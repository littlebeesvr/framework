/*
 * Copyright 2013 the original author or authors.
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
package leap.core;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import leap.core.i18n.MessageSource;

class StandaloneRequestContext extends RequestContext {
	private final Map<String, Object> attributes = new HashMap<String, Object>();
	
	private Session 	  session;
	private MessageSource messageSource;
	private Locale  	  locale;
	private Boolean 	  debug;
	
	@Override
    public AppContext getAppContext() {
	    return AppContext.current();
    }

	@Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

	@Override
    public void removeAttribute(String name) {
        attributes.remove(name);
    }

	@Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

	@Override
    public Session getSession() {
		return getSession(true);
    }
	
	@Override
    public Session getSession(boolean create) {
		if(null == session){
			if(create){
				session = new StandaloneSession();
			}
		}
	    return session;
    }
	
	public MessageSource getMessageSource() {
		if(null == messageSource){
			messageSource = getAppContext().getMessageSource();
		}
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
    public Locale getLocale() {
		if(null == locale){
			locale = getAppContext().getConfig().getDefaultLocale();
		}
	    return locale;
    }

	@Override
    public void setLocale(Locale locale) {
		this.locale = locale;
    }
	
	@Override
    public boolean isDebug() {
		if(null == debug){
			debug = getAppContext().getConfig().isDebug();
		}
	    return debug;
    }

	@Override
    public void setDebug(boolean debug) {
		this.debug = debug;
    }

	protected void invalidateSessionContext(){
		this.session = null;
	}
	
	class StandaloneSession implements Session {
		private final Map<String, Object> attributes = new HashMap<String, Object>();
		
		private boolean valid = true;
		
		@Override
	    public void setAttribute(String name, Object value) {
	        attributes.put(name, value);
	    }

		@Override
	    public void removeAttribute(String name) {
	        attributes.remove(name);
	    }

		@Override
	    public Object getAttribute(String name) {
	        return attributes.get(name);
	    }

		@Override
	    public void invalidate() {
			valid = false;
			invalidateSessionContext();
		}
		
		@Override
        public boolean valid() {
	        return valid;
        }

		@Override
        public HttpSession getServletSession() throws IllegalStateException {
			throw new IllegalStateException("Not servlet environment");
		}
	}
}