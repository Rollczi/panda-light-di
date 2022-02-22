/*
 * Copyright (c) 2021 dzikoysk
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package panda.di.utils;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;

public final class ObjectUtils {

    private ObjectUtils() { }

    /**
     * Try to cast or get null
     *
     * @param object the object to cast
     * @param clazz  the type to cast
     * @param <T>    the result type
     * @return casted object or null if object is not a clazz type
     */
    public static @Nullable <T> T cast(Class<T> clazz, @Nullable Object object) {
        if (!clazz.isInstance(object)) {
            return null;
        }

        return clazz.cast(object);
    }

    @SuppressWarnings("unchecked")
    public static @Nullable <T> T cast(Object object) {
        return (T) object;
    }

    /**
     * Check if object is not null
     *
     * @param object object to check
     * @return true if object is not null
     */
    public static boolean isNotNull(@Nullable Object object) {
        return object != null;
    }

    /**
     * Check if all values are null
     *
     * @param objects array to check
     * @return true if all values are null and false for non null element/empty array
     */
    public static boolean areNull(Object... objects) {
        for (Object object : objects) {
            if (object != null) {
                return false;
            }
        }

        return objects.length > 0;
    }

    /**
     * Check if the value is one of the expected
     *
     * @param value    value to check
     * @param expected expected values
     * @return true if expected values contains the specified value
     */
    public static boolean equalsOneOf(Object value, Object... expected) {
        for (Object expectedValue : expected) {
            if (Objects.equals(value, expectedValue)) {
                return true;
            }
        }

        return false;
    }

}
