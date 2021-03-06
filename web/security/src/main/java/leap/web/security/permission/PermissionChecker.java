/*
 * Copyright 2016 the original author or authors.
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
package leap.web.security.permission;

import leap.lang.Arrays2;

import java.security.Permission;

public interface PermissionChecker {

    /**
     * Checks if the specified permission are "implied by" the "impliedBy" permission.
     *
     * see {@link java.security.Permission#implies(Permission)}.
     *
     * @deprecated use {@link #checkPermissionImplies(String, String[])} instead.
     */
    @Deprecated
    boolean checkPermissionImplies(String[] checkingPermission,String impliedByPermission);

    /**
     * Checks if the expected permission are "implied by" the actual permissions.
     */
    default boolean checkPermissionImplies(String expected, String[] actual) {
        if(null == expected) {
            return true;
        }
        if(null == actual) {
            return false;
        }
        return Arrays2.contains(actual, expected);
    }
}