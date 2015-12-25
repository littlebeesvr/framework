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
package leap.orm.tested.model;

import java.sql.Timestamp;

import leap.core.metamodel.ReservedMetaFieldName;
import leap.orm.annotation.Domain;
import leap.orm.annotation.domain.CreatedAt;
import leap.orm.annotation.meta.MetaName;
import leap.orm.model.Model;

public class Model1 extends Model {

	@CreatedAt
	protected Timestamp creationTime;
	
	@MetaName(reserved=ReservedMetaFieldName.UPDATED_AT)
	@Domain("updatedAt")
	protected Timestamp lastModified;

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}

	public Timestamp getLastModified() {
		return lastModified;
	}

	public void setLastModified(Timestamp lastModified) {
		this.lastModified = lastModified;
	}

}