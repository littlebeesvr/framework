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
package app.controllers;

import app.models.StringableModel;
import leap.lang.New;
import leap.lang.enums.Bool;
import leap.lang.naming.NamingStyles;
import leap.web.action.ControllerBase;
import leap.web.json.JsonSerialize;
import app.models.Model1;
import app.models.products.Product;

import java.util.Map;

public class ResponseBodyController extends ControllerBase {

	public Product getProduct() {
		Product product = new Product();
		
		product.setId(100);
		product.setTitle("Iphone6");
		
		return product;
	}
	
	@JsonSerialize(ignoreNull=Bool.TRUE)
	public Product getProductIgnoreNull() {
		Product product = new Product();
		
		product.setId(100);
		product.setTitle("Iphone6");
		
		return product;
	}
	@JsonSerialize(namingStyle = NamingStyles.NAME_LOWER_UNDERSCORE)
	public Map<String, Object> getMapWithLowerUnderscore(){
		Map<String, Object> map = New.hashMap("userId","userId");
		map.put("userProperty",New.hashMap("userName","userName"));

		return map;
	}

	@JsonSerialize(namingStyle = NamingStyles.NAME_LOWER_UNDERSCORE)
	public StringableModel getJsonStringableWithLowerUnderscore(){
		StringableModel sm = new StringableModel();
		sm.setLowerCamel("lowerUnderScore");
		return sm;
	}

	public Product product(Product product) {
		return product;
	}
	
	public Model1 getModel1() {
		Model1 m = new Model1();
		m.setAttr1("s1");
		m.set("unknow", "s2");
		return m;
	}
}
