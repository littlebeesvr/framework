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
package leap.lang.el.spel.ast;

import leap.lang.el.ElEvalContext;

public class AstString extends AstLiteral {
	private String value;

	public AstString() {
		this.resultType = String.class;
	}

	public AstString(String value) {
		this.value = value;
		this.resultType = String.class;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
    @Override
    public Object getLiteralValue() {
	    return value;
    }

	@Override
    public Object eval(ElEvalContext context) {
	    return value;
    }

    protected void doAccept(AstVisitor visitor) {
        visitor.startVisit(this);
        visitor.endVisit(this);
    }
    
	@Override
    public void toString(StringBuilder buf) {
        if ((this.value == null) || (this.value.length() == 0)) {
            buf.append("null");
        } else {
            buf.append('"');
            buf.append(this.value);
            buf.append('"');
        }
    }
}
