package panda.di.injector;

public interface FieldsInjector<T> {

    T newInstance(Object... injectorArgs) throws Throwable;

}
