package com.crossover.medical.journals;

import io.dropwizard.Configuration;

public class MedicalJournalsConfiguration extends Configuration {

    private String applicationName;

    public String getApplicationName() {
        return applicationName;
    }
}
