package com.crossover.medical.journals;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crossover.medical.journals.auth.AuthenticationManager;
import com.crossover.medical.journals.auth.TokenAuthenticator;
import com.crossover.medical.journals.auth.TokenAuthenticatorProvider;
import com.crossover.medical.journals.core.User;
import com.crossover.medical.journals.dao.UserDAO;
import com.crossover.medical.journals.exception.WebExceptionMapper;
import com.crossover.medical.journals.resource.ApplicationResource;
import com.crossover.medical.journals.resource.UserResource;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MedicalJournalsApplication extends Application<MedicalJournalsConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalJournalsApplication.class);

    private final HibernateBundle<MedicalJournalsConfiguration> hibernateBundle = new HibernateBundle<MedicalJournalsConfiguration>(
            User.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(MedicalJournalsConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    private final MigrationsBundle<MedicalJournalsConfiguration> migrationBundle = new MigrationsBundle<MedicalJournalsConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(MedicalJournalsConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(final Bootstrap<MedicalJournalsConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(migrationBundle);
    }

    @Override
    public void run(MedicalJournalsConfiguration configuration, Environment environment) throws Exception {
        LOGGER.info("MedicalJournalsApplication - Method run() called");

        // Exception Mapper
        environment.jersey().register(new WebExceptionMapper());

        // DAO
        final UserDAO userDAO = new UserDAO(hibernateBundle.getSessionFactory());

        // Manager
        final AuthenticationManager authenticatorManager = new AuthenticationManager(userDAO);

        // Authentication
        environment.jersey()
                .register(new TokenAuthenticatorProvider<User>(new TokenAuthenticator(authenticatorManager)));

        // Resources
        environment.jersey().register(new ApplicationResource());
        environment.jersey().register(new UserResource(userDAO, authenticatorManager));

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
