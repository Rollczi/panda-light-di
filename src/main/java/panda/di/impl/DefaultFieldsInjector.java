package panda.di.impl;

import panda.di.injector.FieldsInjector;
import panda.di.annotations.Inject;

import java.lang.reflect.Field;

final class DefaultFieldsInjector<T> implements FieldsInjector<T> {

    private final InjectorProcessor processor;
    private final DefaultConstructorInjector<T> constructorInjector;

    DefaultFieldsInjector(InjectorProcessor processor, DefaultConstructorInjector<T> constructorInjector) {
        this.processor = processor;
        this.constructorInjector = constructorInjector;
    }

    @Override
    public T newInstance(Object... injectorArgs) throws Throwable {
        T instance = constructorInjector.newInstance(injectorArgs);

        for (Field field : instance.getClass().getFields()) {
            if (!field.isAnnotationPresent(Inject.class)) {
                continue;
            }

            field.set(instance, processor.tryFetchValue(processor, new PropertyField(field), injectorArgs));
        }

        return instance;
    }

}
