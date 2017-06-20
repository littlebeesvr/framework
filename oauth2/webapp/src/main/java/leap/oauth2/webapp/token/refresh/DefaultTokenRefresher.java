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

package leap.oauth2.webapp.token.refresh;

import leap.lang.http.HTTP;
import leap.lang.http.client.HttpRequest;
import leap.oauth2.webapp.code.DefaultCodeVerifier;
import leap.oauth2.webapp.token.TokenDetails;

public class DefaultTokenRefresher extends DefaultCodeVerifier implements TokenRefresher {

    @Override
    public TokenDetails refreshAccessToken(TokenDetails old) {
        if(null == config.getTokenUrl()) {
            throw new IllegalStateException("The tokenUrl must be configured");
        }

        HttpRequest request = httpClient.request(config.getTokenUrl())
                .addFormParam("grant_type", "refresh_token")
                .addFormParam("refresh_token", old.getRefreshToken())
                .setMethod(HTTP.Method.POST);

        return fetchAccessToken(request);
    }

}
