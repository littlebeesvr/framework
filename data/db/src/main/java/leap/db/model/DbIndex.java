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
package leap.db.model;

import leap.lang.Args;
import leap.lang.Named;
import leap.lang.json.JsonStringable;
import leap.lang.json.JsonWriter;

public class DbIndex extends DbNamedObject implements Named, JsonStringable {
	
	protected final boolean  unique;
	protected final String[] columnNames;

	public DbIndex(String name,boolean unique,String[] columnNames) {
	    super(name);
	    
	    Args.notEmpty(columnNames,"the column names");
	    
	    this.unique      = unique;
	    this.columnNames = columnNames;
    }

	public boolean isUnique() {
		return unique;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	@Override
    public void toJson(JsonWriter writer) {
		writer.startObject();
		
		writer.property("name", name);

		if(unique){
			writer.property("unique", unique);
		}
		
		writer.key("columnNames").array(columnNames);
		
		writer.endObject();
    }
}