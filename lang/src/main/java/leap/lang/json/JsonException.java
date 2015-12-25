/*
 * Copyright 2010 the original author or authors.
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
package leap.lang.json;


public class JsonException extends RuntimeException {

    private static final long serialVersionUID = 6604866089585580442L;

	public JsonException() {
	    super();
    }

	public JsonException(String message, Throwable cause) {
	    super(message, cause);
    }

	public JsonException(String message) {
	    super(message);
    }

	public JsonException(Throwable cause) {
	    super(cause);
    }
}
