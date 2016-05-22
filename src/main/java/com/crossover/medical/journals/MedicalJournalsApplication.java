package com.crossover.medical.journals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crossover.medical.journals.resource.ApplicationResource;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MedicalJournalsApplication extends Application<Configuration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalJournalsApplication.class);

    @Override
    public void initialize(final Bootstrap<Configuration> bootstrap) {

    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        LOGGER.info("MedicalJournalsApplication - Method run() called");

        environment.jersey().register(new ApplicationResource());
    }

    public static void main(final String[] args) throws Exception {
        new MedicalJournalsApplication().run(args);
    }
}
