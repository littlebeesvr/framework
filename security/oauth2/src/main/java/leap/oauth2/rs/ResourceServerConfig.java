/*
 * Copyright 2015 the original author or authors.
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
package leap.oauth2.rs;

public interface ResourceServerConfig {

	/**
	 * Returns <code>true</code> if oauth2.0 resource server is enabled.
	 */
	boolean isEnabled();
	
	/**
	 * Returns <code>true</code> if any requests will be intercepted as protected resource.  
	 */
	boolean isInterceptAnyRequests();
	
	/**
	 * Returns the url of token info endpoing.
	 */
	String getRemoteTokenInfoUrl();
	
	/**
	 * Returns the {@link ResourceScope} for the given path or <code>null</code>.
	 */
	ResourceScope getScope(String path);
	
	/**
	 * Returns {@link ResourcePath} for the given path or <code>null</code>.
	 */
	ResourcePath getResourcePath(String path);

}