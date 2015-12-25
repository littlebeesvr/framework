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

import leap.lang.codec.Hex;
import leap.lang.json.JSON;
import leap.web.action.ControllerBase;
import leap.web.annotation.RequestBody;
import app.models.products.Product;

public class RequestBodyController extends ControllerBase {
	
	public void index() {
		ok();
	}
	
	public String stringBody(@RequestBody String body) {
		return body;
	}
	
	public String stringBody1(@RequestBody String body,String p1) {
		return body + ":" + p1;
	}
	
	public String bytesBody(@RequestBody byte[] body) {
		return Hex.encode(body);
	}
	
	public String entityBody(Product product) {
		return JSON.encode(product);
	}

}