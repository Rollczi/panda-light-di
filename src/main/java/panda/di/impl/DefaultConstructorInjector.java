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

package panda.di.impl;

import panda.di.injector.ConstructorInjector;

import java.lang.reflect.Constructor;

final class DefaultConstructorInjector<T> implements ConstructorInjector<T> {

    private final InjectorProcessor processor;
    private final Constructor<?> constructor;
    private final InjectorCache cache;

    DefaultConstructorInjector(InjectorProcessor processor, Constructor<T> constructor) {
        this.processor = processor;
        this.constructor = constructor;
        constructor.setAccessible(true);
        this.cache = InjectorCache.of(processor, constructor);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T newInstance(Object... injectorArgs) throws Throwable {
        return (T) constructor.newInstance(processor.fetchValues(cache, injectorArgs));
    }

    @Override
    public Constructor<?> getConstructor() {
        return constructor;
    }

}
