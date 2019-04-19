/*
 *
 *  * Copyright 2019 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package leap.lang.jdbc;

import java.util.function.Consumer;

public interface WhereBuilder {

    interface Expr {
        Expr append(String s);

        Expr append(char c);

        Expr arg(Object arg);
    }

    boolean isEmpty();

    WhereBuilder and(String expr);

    WhereBuilder and(Consumer<Expr> func);

    WhereBuilder and(String expr, Object... args);

    WhereBuilder or(String expr);

    WhereBuilder or(Consumer<Expr> func);

    WhereBuilder or(String expr, Object... args);

}