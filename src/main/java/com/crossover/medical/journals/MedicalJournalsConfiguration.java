package com.crossover.medical.journals;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class MedicalJournalsConfiguration extends Configuration {

    private String applicationName;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    public String getApplicationName() {
        return applicationName;
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        database = dataSourceFactory;
    }
}
