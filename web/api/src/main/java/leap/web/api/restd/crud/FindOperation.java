/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package leap.web.api.restd.crud;

import leap.lang.Strings;
import leap.orm.dao.Dao;
import leap.web.Request;
import leap.web.action.ActionParams;
import leap.web.action.FuncActionBuilder;
import leap.web.api.Api;
import leap.web.api.config.ApiConfigurator;
import leap.web.api.meta.model.MApiModel;
import leap.web.api.mvc.ApiResponse;
import leap.web.api.mvc.params.QueryOptionsBase;
import leap.web.api.orm.*;
import leap.web.api.restd.CrudOperation;
import leap.web.api.restd.CrudOperationBase;
import leap.web.api.restd.RestdContext;
import leap.web.api.restd.RestdModel;
import leap.web.exception.NotFoundException;
import leap.web.route.RouteBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Find by id operation.
 */
public class FindOperation extends CrudOperationBase implements CrudOperation {

    protected static final String NAME = "find";

    @Override
    public void createCrudOperation(ApiConfigurator c, RestdContext context, RestdModel model) {
        if (!context.getConfig().allowFindModel(model.getName())) {
            return;
        }

        String path = fullModelPath(c, model) + getIdPath(model);
        String name = Strings.lowerCamel(NAME, model.getName());

        createCrudOperation(c, context, model, path, name, null);
    }

    public void createCrudOperation(ApiConfigurator c, RestdContext context, RestdModel model,
                                    String path, String name, Callback callback) {
        FuncActionBuilder action = new FuncActionBuilder(name);
        RouteBuilder      route  = rm.createRoute("GET", path);

        if (null != callback) {
            callback.preAddArguments(action);
        }

        action.setFunction(createFunction(context, model, action.getArguments().size()));

        addIdArguments(context, action, model);
        addArgument(context, action, QueryOptionsBase.class, "options");
        if (null != callback) {
            callback.postAddArguments(action);
        }
        addModelResponse(action, model);

        preConfigure(context, model, action);
        route.setAction(action.build());
        setCrudOperation(route, NAME);
        postConfigure(context, model, route);

        if (isOperationExists(context, route)) {
            return;
        }

        c.addDynamicRoute(rm.loadRoute(context.getRoutes(), route));
    }

    protected Function<ActionParams, Object> createFunction(RestdContext context, RestdModel model, int start) {
        return new FindFunction(context.getApi(), context.getDao(), model, start);
    }

    protected class FindFunction extends CrudFunction {

        public FindFunction(Api api, Dao dao, RestdModel model, int start) {
            super(api, dao, model, start);
        }

        @Override
        public Object apply(ActionParams params) {
            MApiModel am = am();

            final Request request = params.getContext().getRequest();

            ModelExecutorContext context  = new SimpleModelExecutorContext(api, dao, am, em, params);
            ModelQueryExecutor   executor = newQueryExecutor(context);

            Object           id      = id(params);
            QueryOptionsBase options = getWithId(params, 0);

            QueryOneResult result = executor.queryOne(id, options);

            boolean hasExpandErrors = null != result.getExpandErrors() && !result.getExpandErrors().isEmpty();
            if (hasExpandErrors && !allowExpandErrors(request)) {
                return ApiResponse.err(request.getMessageSource().getMessage(QueryOperation.QUERY_EXPAND_ERROR));
            }

            ApiResponse response;
            if (null != result.getEntity()) {
                response = ApiResponse.of(result.getEntity());
            } else if (result.getRecord() == null) {
                throw new NotFoundException(am.getName() + " '" + id.toString() + "' not found");
            } else {
                response = ApiResponse.of(result.getRecord());
            }

            if (hasExpandErrors) {
                List<String> expands = new ArrayList<>();
                for (ExpandError ee : result.getExpandErrors()) {
                    expands.add(ee.getExpand());
                }
                response.withHeader(QueryOperation.ERROR_EXPANDS, Strings.join(expands, ','));
            }

            return response;
        }

        protected ModelQueryExecutor newQueryExecutor(ModelExecutorContext context) {
            return mef.newQueryExecutor(context);
        }

        protected boolean allowExpandErrors(Request request) {
            return "1".equals(request.getHeader(QueryOperation.ALLOW_EXPAND_ERROR));
        }

        @Override
        public String toString() {
            return "Function:" + "Find " + model.getName() + "";
        }
    }

}
