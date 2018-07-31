package ru.otus.l41;

import ru.otus.l41.annotations.After;
import ru.otus.l41.annotations.Before;
import ru.otus.l41.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public class TestFramework {
    public void run(Class<?> klazz) {
        List<Method> beforeList = ReflectionHelper.getMethodsAnnotatedWith(klazz, Before.class);
        List<Method> afterList = ReflectionHelper.getMethodsAnnotatedWith(klazz, After.class);
        List<Method> testList = ReflectionHelper.getMethodsAnnotatedWith(klazz, Test.class);

        for (Method m : testList) {

            AnnotationsTest test = (AnnotationsTest) ReflectionHelper.instantiate(klazz);

            for (Method b : beforeList)
                ReflectionHelper.callMethod(test, b.getName());

            ReflectionHelper.callMethod(test, m.getName());

            for (Method a : afterList)
                ReflectionHelper.callMethod(test, a.getName());
        }

    }

}
