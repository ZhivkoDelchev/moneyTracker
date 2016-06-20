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

@ParametersSuppliedBy(TestOnLongs.TestOnSupplier.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface TestOnLongs {

    long[] value();

    class TestOnSupplier extends ParameterSupplier {
        @Override
        public List<PotentialAssignment> getValueSources(final ParameterSignature parameterSignature) throws Throwable {
            final List<PotentialAssignment> result = new ArrayList<>();

            final TestOnLongs testOnLongs = parameterSignature.getAnnotation(TestOnLongs.class);
            if (testOnLongs != null) {
                for (long value : testOnLongs.value()) {
                    result.add(PotentialAssignment.forValue("" + value, value));
                }
            }
            return result;
        }
    }
}
