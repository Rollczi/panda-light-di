package panda.di.injector;

import java.lang.reflect.Method;

public interface MethodInjector {

    public <T> T invoke(Object instance, Object... injectorArgs) throws Throwable;

    public Method getMethod();

}
