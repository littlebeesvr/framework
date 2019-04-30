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
package leap.core.ds;

import javax.sql.DataSource;

import leap.lang.exception.NestedClassNotFoundException;

public interface DataSourceFactory {

	/**
	 * Returns a {@link DataSource} instance connecte to the database specified in the given config or <code>null</code>
	 * 
	 * if this factory not supports the given config.
	 * 
	 * @throws NestedClassNotFoundException if the given 'driverClassName' property not found.
	 */
	DataSource tryCreateDataSource(DataSourceProps props) throws NestedClassNotFoundException;
	
	/**
	 * Destroy the given {@link DataSource}.
	 * 
	 * <p>
	 * Returns <code>true</code> if this factory success destroy the given {@link DataSource}, returns <code>false</code> otherwise. 
	 */
	boolean tryDestroyDataSource(DataSource ds);
}