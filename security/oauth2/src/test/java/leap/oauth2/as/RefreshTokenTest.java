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
package leap.oauth2.as;

import leap.oauth2.TokenResponse;
import leap.oauth2.OAuth2TestBase;

import org.junit.Test;

public class RefreshTokenTest extends OAuth2TestBase {
    
    @Test
    public void testInvalidClientCredentials() {
        TokenResponse token = obtainAccessTokenByRefreshToken("bad refresh token");
        assertTrue(token.isError());
        assertNotEmpty(token.error);
    }
    
	@Test
	public void testSuccessRequest() {
	    logout();
	    
	    TokenResponse token = obtainAccessTokenByPassword(USERNAME1, PASSWORD1);
	    assertFalse(token.isError());
	    
	    TokenResponse newToken = obtainAccessTokenByRefreshToken(token.refreshToken);
	    testAccessTokenInfo(newToken);
	}
	
}