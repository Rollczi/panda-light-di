package panda.di.injector;

import java.lang.reflect.Constructor;

public interface ConstructorInjector<T> {

    T newInstance(Object... injectorArgs) throws Throwable;

    Constructor<?> getConstructor();

}
