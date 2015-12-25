/*
 * Copyright TrigerSoft <kostat@trigersoft.com> 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package leap.orm.linq.jaque;

import java.util.ArrayList;

import leap.lang.asm.Label;

/**
 * @author <a href="mailto://kostat@trigersoft.com">Konstantin Triger</a>
 */

@SuppressWarnings("serial")
final class ExpressionStack extends ArrayList<Expression> {

	private BranchExpression _parent;
	private boolean _reduced;

	public ExpressionStack() {
		this(null);
	}

	public ExpressionStack(BranchExpression parent) {
		_parent = parent;
	}

	public BranchExpression getParent() {
		return _parent;
	}

	private void setParent(BranchExpression value) {
		_parent = value;
	}

	public boolean isReduced() {
		return _reduced;
	}

	public void reduce() {
		_reduced = true;
	}

	public void push(Expression item) {
		add(item);
	}

	public int getDepth() {
		return getParent() != null ? getParent().getDepth() : 0;
	}

	public Expression pop() {
		Expression obj = peek();
		remove(size() - 1);

		return obj;
	}

	public Expression peek() {
		Expression obj = get(size() - 1);

		return obj;
	}

	public static final class BranchExpression extends Expression {

		private final Expression _test;
		private final ExpressionStack _true;
		private final ExpressionStack _false;
		private final ExpressionStack _parent;

		public BranchExpression(ExpressionStack parent, Expression test, Label label) {
			this(parent, test, null, null);
		}

		public BranchExpression(ExpressionStack parent, Expression test,
				ExpressionStack trueE, ExpressionStack falseE) {
			super(ExpressionType.Conditional, Void.TYPE);
			_parent = parent;
			_test = test;

			if (trueE != null) {
				_true = trueE;
				_true.setParent(this);
			} else
				_true = new ExpressionStack(this);

			if (falseE != null) {
				_false = falseE;
				_false.setParent(this);
			} else
				_false = new ExpressionStack(this);
		}

		public ExpressionStack getTrue() {
			return _true;
		}

		public ExpressionStack getFalse() {
			return _false;
		}

		public ExpressionStack get(boolean side) {
			return side ? getTrue() : getFalse();
		}

		public Expression getTest() {
			return _test;
		}

		public ExpressionStack getParent() {
			return _parent;
		}

		int getDepth() {
			return _parent.getDepth() + 1;
		}

		@Override
		protected <T> T visit(ExpressionVisitor<T> v) {
			throw new IllegalStateException();
		}

		@Override
		public String toString() {
			return "(" + getTest().toString() + " ? " + getTrue().toString()
					+ " : " + getFalse().toString() + ")";
		}
	}
}
