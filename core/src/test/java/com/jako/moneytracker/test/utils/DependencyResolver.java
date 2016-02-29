package com.jako.moneytracker.test.utils;

import org.mockito.Mock;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DependencyResolver {

    public <T> T resolveDependencies(final T sut, final Object dependencyContainer) throws IllegalAccessException {
        if (sut == null) {
            throw new NullPointerException("System under test must not be null");
        }
        if (dependencyContainer == null) {
            throw new NullPointerException("Dependency container must not be null");
        }
        final Collection<Field> requiredDependencies = getRequiredDependencies(sut);
        final Collection<Field> availableMocks = getAnnotatedFields(dependencyContainer.getClass(), Mock.class);

        injectMocksForDependencies(sut, requiredDependencies, dependencyContainer, availableMocks);

        return sut;
    }

    private <T> Collection<Field> getRequiredDependencies(T sut) throws IllegalAccessException {
        final Collection<Field> dependencies = getAnnotatedFields(sut.getClass(), Inject.class);
        final Collection<Field> result = new ArrayList<>();
        for (Field field: dependencies) {
            field.setAccessible(true);
            if (hasToBeInjected(field, sut)) {
                result.add(field);
            }
        }
        return result;
    }

    private Collection<Field> getAnnotatedFields(Class<? extends Object> clazz, Class<? extends Annotation> annotation) {
        final Collection<Field> allFields = getFields(clazz);
        Collection<Field> annotatedFields = new ArrayList<>();
        for (Field field: allFields) {
            if (field.isAnnotationPresent(annotation)) {
                annotatedFields.add(field);
            }
        }
        return annotatedFields;
    }

    private Collection<Field> getFields(Class<?> clazz) {
        Collection<Field> fields = new ArrayList<>();
        while (clazz != null && clazz != Object.class) {
            for (Field field: clazz.getDeclaredFields()) {
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    private <T> boolean hasToBeInjected(Field field, T sut) throws IllegalAccessException {
        return field.get(sut) == null;
    }

    private void injectMocksForDependencies(final Object sut, final Collection<Field> requiredDependencies, final Object dependencyContainer, final Collection<Field> availableMocks) throws IllegalAccessException {
        for (Field dependency: requiredDependencies) {
            Field mock = findMockFor(dependency, availableMocks);
            if (mock != null) {
                mock.setAccessible(true);
                inject(mock.get(dependencyContainer), dependency, sut);
            }
        }
    }

    private static Field findMockFor(Field dependency, final Collection<Field> availableMocks) {
        final Class<?> dependencyType = dependency.getType();
        List<Field> matchingMocks = new ArrayList<>();
        for (Field mock: availableMocks) {
            if (dependencyType.isAssignableFrom(mock.getType())) {
                matchingMocks.add(mock);
            }
        }
        if (matchingMocks.size() == 1) {
            return matchingMocks.get(0);
        }
        for (Field mock: matchingMocks) {
            if (mock.getName().equals(dependency.getName())) {
                return mock;
            }
        }
        return null;
    }

    private static void inject(final Object toInject, final Field target, final Object targetOwner) throws IllegalAccessException {
        if (toInject == null) {
            return;
        }
        target.set(targetOwner, toInject);
    }
}
