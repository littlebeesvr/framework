/*
 * Copyright 2014 the original author or authors.
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
package leap.orm.tested.domains;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import leap.lang.enums.Bool;
import leap.orm.annotation.ADomain;
import leap.orm.annotation.ColumnType;

@ADomain(name="test2",length=100,type=ColumnType.VARCHAR,update=Bool.FALSE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestDomain2 {

}
