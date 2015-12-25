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
package leap.web.security.cookie;

import leap.web.config.WebConfig;
import leap.web.cookie.CookieBean;
import leap.web.security.SecurityConfig;

public class SecurityCookieBean extends CookieBean {

    protected SecurityConfig securityConfig;
    
    public SecurityCookieBean(WebConfig webConfig, SecurityConfig securityConfg,  String cookieName) {
        super(webConfig, cookieName);
        
        this.securityConfig = securityConfg;
    }
    
    public String getCookieDomain() {
        String domain = securityConfig.getCookieDomain();
        if(null == domain) {
            return super.getCookieDomain();
        }else{
            return domain;
        }
    }

    public boolean isCookieCrossContext() {
        return securityConfig.isCrossContext();
    }

}