package io.yukkuric.botania_overpowered.helpers;

import java.lang.reflect.Constructor;

public class ReflectionHelpers {
    public static class WrappedCtor<T> {
        Class<T> caster;
        Constructor<?> loader;

        public WrappedCtor(Class<T> caster, String className, Class<?>... args) {
            this.caster = caster;
            try {
                loader = Class.forName(className).getConstructor(args);
                loader.setAccessible(true);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public T get(Object... args) {
            try {
                return caster.cast(loader.newInstance(args));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
