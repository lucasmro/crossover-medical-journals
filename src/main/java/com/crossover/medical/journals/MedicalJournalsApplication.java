package com.crossover.medical.journals;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crossover.medical.journals.exception.WebExceptionMapper;
import com.crossover.medical.journals.resource.ApplicationResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MedicalJournalsApplication extends Application<MedicalJournalsConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalJournalsApplication.class);

    @Override
    public void initialize(final Bootstrap<MedicalJournalsConfiguration> bootstrap) {

    }

    @Override
    public void run(MedicalJournalsConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("MedicalJournalsApplication - Method run() called");

        // Exception Mapper
        environment.jersey().register(new WebExceptionMapper());

        // Resources
        environment.jersey().register(new ApplicationResource());

        // HealthCheck
        environment.healthChecks().register(configuration.getApplicationName(), new MedicalJournalsHealthCheck());

        // CORS Filter
        configureCors(environment);
    }

    public static void main(final String[] args) throws Exception {
        new MedicalJournalsApplication().run(args);
    }

    private void configureCors(Environment environment) {
        final Dynamic filter = environment.servlets().addFilter("CORS", CrossOriginFilter.class);
        filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
        filter.setInitParameter("allowedHeaders",
                "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin");
        filter.setInitParameter("allowCredentials", "true");
    }
}
