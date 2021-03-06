/*
 * Copyright (c) 2020 Dzikoysk
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

package panda.di.bind;

import org.jetbrains.annotations.NotNull;
import panda.di.injector.Property;
import panda.std.function.TriFunction;
import java.lang.annotation.Annotation;
import java.util.function.Supplier;

public interface Bind<A extends Annotation> extends Comparable<Bind<A>> {

    /**
     * Assign object to the bind
     *
     * @param value the instance to assign
     */
    void assignInstance(Object value);

    /**
     * Assign value supplier to the bind
     *
     * @param valueSupplier the supplier to assign
     */
    void assignInstance(Supplier<?> valueSupplier);

    /**
     * Assign custom handler to the bind
     *
     * @param handler the handler which accepts type of parameter and bind type as arguments
     */
    void assignHandler(TriFunction<Property, A, Object[], ?> handler);

    /**
     * Get value of bind for the required (parameter) type and instance of bind type
     *
     * @param required the required return type
     * @param annotation instance of bind generic type
     * @param injectorArgs custom arguments for injector
     * @return the result value
     * @throws Exception if anything wrong will happen, whole process should be stopped
     */
    Object getValue(Property required, A annotation, Object... injectorArgs) throws Exception;

    /**
     * Get associated type with the bind
     *
     * @return the associated type
     */
    Class<?> getAssociatedType();

    /**
     * Get data type
     *
     * @return the data type
     */
    Class<?> getDataType();

    @Override
    default int compareTo(@NotNull Bind bind) {
        return Integer.compare(ResourceBindType.of(getAssociatedType()).getPriority(), ResourceBindType.of(bind.getAssociatedType()).getPriority());
    }

}
