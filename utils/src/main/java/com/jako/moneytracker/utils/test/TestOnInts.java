package com.jako.moneytracker.utils.test;

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

@ParametersSuppliedBy(TestOnInts.TestOnSupplier.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface TestOnInts {
    String NULL = "\u0000";

    int[] value();

    class TestOnSupplier extends ParameterSupplier {
        @Override
        public List<PotentialAssignment> getValueSources(ParameterSignature sig) {
            List<PotentialAssignment> result = new ArrayList<>();

            TestOnInts TestOnInts = sig.getAnnotation(TestOnInts.class);
            if (TestOnInts != null) {
                for (int value : TestOnInts.value()) {
                    result.add(PotentialAssignment.forValue("" + value, value));
                }
            }
            return result;
        }
    }
}