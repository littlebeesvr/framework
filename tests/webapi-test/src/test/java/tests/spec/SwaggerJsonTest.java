/*
 *
 *  * Copyright 2016 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package tests.spec;

import leap.core.annotation.Inject;
import leap.lang.json.JSON;
import leap.lang.meta.MComplexType;
import leap.lang.meta.MProperty;
import leap.lang.meta.MType;
import leap.lang.resource.Resources;
import leap.web.api.meta.ApiMetadata;
import leap.web.api.meta.ApiMetadataBuilder;
import leap.web.api.meta.model.MApiModelBuilder;
import leap.web.api.meta.model.MApiPropertyBuilder;
import leap.web.api.meta.model.MApiSecurityDef;
import leap.web.api.spec.swagger.SwaggerConstants;
import leap.web.api.spec.swagger.SwaggerJsonWriter;
import leap.web.api.spec.swagger.SwaggerSpecReader;
import leap.webunit.WebTestBase;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

public class SwaggerJsonTest extends WebTestBase {

    private @Inject SwaggerSpecReader specReader;
    private @Inject SwaggerJsonWriter specWriter;

    @Test
    public void testOpenFormat() throws Exception {

        try(Reader reader = Resources.getResource("classpath:/swagger/format.json").getInputStreamReader()) {
            ApiMetadata m = specReader.read(reader).build();

            StringBuilder out = new StringBuilder();
            specWriter.write(m, out);
            String json = out.toString();

            assertContains(json, "uuid");
            assertContains(json, "email");
        }
    }

    @Test
    public void testCompatibleFormat() throws Exception {

        try(Reader reader = Resources.getResource("classpath:/swagger/compatible.json").getInputStreamReader()) {
            ApiMetadata m = specReader.read(reader).build();

            StringBuilder out = new StringBuilder();
            specWriter.write(m, out);
            String json = out.toString();

            assertNotContains(json, "vendorExtensions");
        }
    }

    @Test
    public void testXSecuritySwaggerJson(){
        String swagger = get("/api/swagger.json").getContent();
        Map<String, Object> map = JSON.decodeMap(swagger);
        Map<String, Object> path = getAsMap(getAsMap(map,"paths"),"/xs/anonymous");
        Map<String, Object> op = getAsMap(path,"get");
        Map<String, Object> xs = getAsMap(op,"x-security");
        assertNull(xs);

        path = getAsMap(getAsMap(map,"paths"),"/xs/client_only");
        op = getAsMap(path,"get");
        xs = getAsMap(op,"x-security");
        Object userRequired = xs.get("userRequired");
        assertNull(userRequired);

        Object allowClientOnly = xs.get("allowClientOnly");
        assertEquals(Boolean.TRUE, allowClientOnly);
    }

    @Test
    public void testSecurityDef() throws IOException {
        String swagger = get("/basepackage/swagger.json").getContent();
        Map<String, Object> map = JSON.decodeMap(swagger);
        Map<String, Object> secDef = getAsMap(map,SwaggerConstants.SECURITY_DEFINITIONS);
        Map<String, Object> oauth = getAsMap(secDef,SwaggerConstants.OAUTH2);
        Object flow = oauth.get(SwaggerConstants.FLOW);
        assertEquals(SwaggerConstants.ACCESS_CODE,flow);
        ApiMetadata m = specReader.read(new StringReader(swagger)).build();
        boolean assertFlow = false;
        for(MApiSecurityDef def : m.getSecurityDefs()){
            if(def.isOAuth2()){
                assertEquals(SwaggerConstants.ACCESS_CODE,def.getFlow());
                assertFlow = true;
            }
        }
        assertTrue(assertFlow);
    }

    @Test
    public void testProfile() throws IOException {
        String swagger = get("/api/swagger.json").getContent();
        assertContains(swagger, "/profile/common_api");
        assertContains(swagger, "/profile/mobile_api");
        assertContains(swagger, "/profile/web_api");

        swagger = get("/api/swagger.json?profile=mobile").getContent();
        assertContains(swagger, "/profile/common_api");
        assertContains(swagger, "/profile/mobile_api");
        assertContains(swagger, "\"/profile/web_api\":{}");

        swagger = get("/api/swagger.json?profile=web").getContent();
        assertContains(swagger, "/profile/common_api");
        assertContains(swagger, "\"/profile/mobile_api\":{}");
        assertContains(swagger, "/profile/web_api");
    }

    @Test
    public void testReadNestedModel() throws IOException {
        String json = Resources.getContent("classpath:swagger/nested_model.json");

        Map<String, Object> map = (Map)JSON.decodeMap(json).get("MT_BREAKDOWN_DETAIL_REQ");

        MApiModelBuilder model = specReader.readModel("test", map);

        MApiPropertyBuilder p = model.getProperty("I_REQUEST");
        MType type = p.getType();
        assertTrue(type.isComplexType());

        MComplexType ct = type.asComplexType();
        MProperty mp = ct.getProperty("REQ_BASEINFO");
        assertTrue(mp.getType().isComplexType());

        json = toJson(model);
        map = JSON.parse(json).asJsonObject().getObject("definitions").getObject("test").asMap();
        model = specReader.readModel("test", map);

        String json1 = toJson(model);
        assertEquals(json1, json);
    }

    protected String toJson(MApiModelBuilder model) throws IOException{
        ApiMetadataBuilder mdb = new ApiMetadataBuilder();
        mdb.setName("Test");
        mdb.addModel(model);

        StringBuilder out = new StringBuilder();
        specWriter.write(mdb.build(), out);
        return out.toString();
    }

    protected Map<String, Object> getAsMap(Map<String, Object> map, String key){
        return (Map<String, Object>)map.get(key);
    }
}
