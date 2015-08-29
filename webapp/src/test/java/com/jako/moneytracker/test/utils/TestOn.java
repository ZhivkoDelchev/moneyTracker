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
@ParametersSuppliedBy(TestOn.TestOnSupplier.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface TestOn {
    String NULL = null;

    String[] strings();

    class TestOnSupplier extends ParameterSupplier {
        @Override
        public List<PotentialAssignment> getValueSources(ParameterSignature sig) {

            List<PotentialAssignment> result = new ArrayList<>();

            TestOn testOn = sig.getAnnotation(TestOn.class);
            if (testOn != null) {
                for (String param : testOn.strings()) {
                    result.add(PotentialAssignment.forValue(param, param));
                }
            }
            return result;
        }
    }
}