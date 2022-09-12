package com.aldo.samples.testing;

import org.springframework.core.env.Environment;


public class EnvironmentWrapper implements IEnvironmentWrapper {

    private Environment environment;

    public EnvironmentWrapper(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String getVariable(String variableName) {
        return this.environment.getProperty("bar");
    }
}
