package com.aldo.samples.testing;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Optional;


@Getter
public class Configuration {

    @Getter(value = AccessLevel.NONE)
    private final IEnvironmentWrapper environmentWrapper;

    private String dbDialect;
    private String dbDriver;
    private String dbUsername;
    private String dbPassword;
    private String dbHost;
    private String dbPort;
    private String dbName;

    public Configuration(IEnvironmentWrapper environmentWrapper)
    {
        this.environmentWrapper = environmentWrapper;

        dbDialect = Optional.ofNullable(environmentWrapper.getVariable("DB_DIALECT")).orElse("");
        dbDriver = Optional.ofNullable(environmentWrapper.getVariable("DB_DRIVER")).orElse("");;
        dbUsername = Optional.ofNullable(environmentWrapper.getVariable("DB_USERNAME")).orElse("");;
        dbPassword = Optional.ofNullable(environmentWrapper.getVariable("DB_PASSWORD")).orElse("");;
        dbHost = Optional.ofNullable(environmentWrapper.getVariable("DB_HOST")).orElse("");;
        dbPort = Optional.ofNullable(environmentWrapper.getVariable("DB_PORT")).orElse("");;
        dbName = Optional.ofNullable(environmentWrapper.getVariable("DB_NAME")).orElse("");;
    }

    public String getConnectionString()
    {
        // dialect+driver://username:password@host:port/database
        return String.format("%s://%s:%s@%s:%s/%s",
                (StringUtils.hasLength(this.dbDialect) ? this.dbDialect : "mysql") + (StringUtils.hasLength(this.dbDriver) ? String.format("+%s", this.dbDriver) : ""),
                this.dbUsername,
                this.dbPassword,
                (StringUtils.hasLength(this.dbHost) ? this.dbHost : "localhost"),
                (StringUtils.hasLength(this.dbPort) ? this.dbPort : "3306"),
                this.dbName
        );
    }
}
