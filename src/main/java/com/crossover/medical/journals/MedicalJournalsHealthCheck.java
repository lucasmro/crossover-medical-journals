package com.crossover.medical.journals;

import com.codahale.metrics.health.HealthCheck;

public class MedicalJournalsHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
