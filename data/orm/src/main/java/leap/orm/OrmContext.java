/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package leap.orm;

import javax.sql.DataSource;

import leap.lang.Named;
import leap.orm.command.CommandFactory;
import leap.orm.linq.ConditionParser;
import leap.orm.metadata.MetadataContext;
import leap.orm.parameter.ParameterStrategy;
import leap.orm.query.QueryFactory;
import leap.orm.reader.EntityReader;
import leap.orm.reader.RowReader;
import leap.orm.sql.SqlFactory;

public interface OrmContext extends MetadataContext, Named {
	
	OrmConfig getConfig();
	
	DataSource getDataSource();
	
	ParameterStrategy getParameterStrategy();
	
	CommandFactory getCommandFactory();
	
	SqlFactory getSqlFactory();
	
	ConditionParser getConditionParser();
	
	QueryFactory getQueryFactory();
	
	EntityReader getEntityReader();
	
	RowReader getRowReader();

}