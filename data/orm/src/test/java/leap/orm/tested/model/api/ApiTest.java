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

package leap.orm.tested.model.api;

import leap.orm.OrmTestCase;
import leap.orm.mapping.EntityMapping;
import org.junit.Test;

public class ApiTest extends OrmTestCase {

    @Test
    public void testApiOperationQuery() {
        EntityMapping em = ApiOperation.metamodel();
        assertEquals(11, em.getFieldMappings().length);

        ApiOperation.where("pathId = ?", "not_exists").list();
    }

}
