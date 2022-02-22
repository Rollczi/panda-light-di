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
import panda.std.Option;
import panda.std.Pair;
import panda.std.function.ThrowingConsumer;

import java.lang.reflect.Array;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class ArrayUtils {

    private ArrayUtils() { }

    /**
     * Find value in array using the predicate condition
     *
     * @param array the array to search in
     * @param condition the condition
     * @param <T> type of array
     * @return the found element or null
     */
    public static <T> Option<T> findIn(T[] array, Predicate<T> condition) {
        for (T element : array) {
            if (condition.test(element)) {
                return Option.of(element);
            }
        }

        return Option.none();
    }


    /**
     * Get index of the given element
     *
     * @param array the array to search in
     * @param element element to search for
     * @param <T> type of array
     * @return index of element or -1 if element was not found
     */
    public static <T> int indexOf(T[] array, @Nullable T element) {
        for (int index = 0; index < array.length; index++) {
            if (Objects.equals(array[index], element)) {
                return index;
            }
        }

        return -1;
    }

    /**
     * Return array of the specified elements using varargs parameter
     *
     * @param elements elements in array
     * @param <T>      type of the array
     * @return the array of the specified elements
     */
    @SafeVarargs
    public static <T> T[] of(T... elements) {
        return elements;
    }

}
