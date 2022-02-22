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

import org.jetbrains.annotations.Nullable;
import panda.di.injector.FieldsInjector;
import panda.di.injector.Injector;
import panda.di.injector.InjectorController;
import panda.di.injector.MethodInjector;
import panda.di.injector.Resources;
import panda.di.utils.ObjectUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.security.InvalidParameterException;

final class DefaultInjector implements Injector {

    private final Resources resources;
    private final InjectorProcessor processor;

    DefaultInjector(Resources resources) {
        this.resources = resources;
        this.processor = new InjectorProcessor(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> DefaultConstructorInjector<T> forConstructor(Class<T> type) {
        if (type.getDeclaredConstructors().length != 1) {
            throw new InvalidParameterException("Class has to contain one and only constructor");
        }

        return new DefaultConstructorInjector<T>(processor, (Constructor<T>) type.getDeclaredConstructors()[0]);
    }

    @Override
    public <T> DefaultConstructorInjector<T> forConstructor(Constructor<T> constructor) {
        if (!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }

        return new DefaultConstructorInjector<>(processor, constructor);
    }

    @Override
    public <T> T newInstance(Class<T> type, Object... injectorArgs) throws Throwable {
        return forConstructor(type).newInstance(injectorArgs);
    }

    @Override
    public <T> FieldsInjector<T> forFields(Class<T> type) {
        if (type.getDeclaredConstructors().length != 1) {
            throw new InvalidParameterException("Class has to contain one and only constructor");
        }

        return new DefaultFieldsInjector<T>(processor, forConstructor(type));
    }

    @Override
    public <T> T newInstanceWithFields(Class<T> type, Object... injectorArgs) throws Throwable {
        return forFields(type).newInstance(injectorArgs);
    }

    @Override
    public <T> T invokeMethod(Method method, Object instance, Object... injectorArgs) throws Throwable {
        return forMethod(method).invoke(instance, injectorArgs);
    }

    @Override
    public MethodInjector forMethod(Method method) {
        return new DefaultMethodInjector(processor, method);
    }

    @Override
    public <T> @Nullable T invokeParameter(Parameter parameter, Object... injectorArgs) throws Exception {
        return ObjectUtils.cast(processor.tryFetchValue(processor, new PropertyParameter(parameter), injectorArgs));
    }

    @Override
    public Injector fork(InjectorController controller) {
        return DefaultInjectorFactory.createInjector(controller, resources.fork());
    }

    @Override
    public Injector duplicate(InjectorController controller) {
        return DefaultInjectorFactory.createInjector(controller, resources.duplicate());
    }

    public Resources getResources() {
        return resources;
    }

}
