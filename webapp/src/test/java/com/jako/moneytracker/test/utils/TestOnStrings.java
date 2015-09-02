package com.jako.moneytracker.test.utils;

import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jako on 28.8.2015 ;)
 */
@ParametersSuppliedBy(TestOnStrings.TestOnSupplier.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface TestOnStrings {
    public static final String NULL = "\u0000";

    String[] strings();

    class TestOnSupplier extends ParameterSupplier {
        @Override
        public List<PotentialAssignment> getValueSources(ParameterSignature sig) {

            List<PotentialAssignment> result = new ArrayList<>();

            TestOnStrings testOnStrings = sig.getAnnotation(TestOnStrings.class);
            if (testOnStrings != null) {
                for (String param : testOnStrings.strings()) {
                    result.add(PotentialAssignment.forValue(param, param));
                }
            }
            return result;
        }
    }
}