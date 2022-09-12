package com.aldo.samples.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;


public class ConfigurationTest {
    private IEnvironmentWrapper mockIEnvironmentWrapper;

    @BeforeEach
    public void beforeEach() {
        this.mockIEnvironmentWrapper = Mockito.mock(IEnvironmentWrapper.class);
    }

    @Test
    @DisplayName("getConnectionString should return the expected connection string")
    public void  testGetConnectionStringShouldReturnTheExpectedValue() {
        // arrange
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_DIALECT"))).thenReturn("");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_DRIVER"))).thenReturn("");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_USERNAME"))).thenReturn("foo");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_PASSWORD"))).thenReturn("bar");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_HOST"))).thenReturn("");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_PORT"))).thenReturn("");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_NAME"))).thenReturn("mydb");

        // act
        var sut = new Configuration(this.mockIEnvironmentWrapper);

        // assert
        Mockito.verify(this.mockIEnvironmentWrapper, Mockito.times(7)).getVariable(ArgumentMatchers.any(String.class));
        Assertions.assertEquals("mysql://foo:bar@localhost:3306/mydb", sut.getConnectionString());
    }

    @Test
    @DisplayName("getConnectionString should return the expected connection string when all the parameters are set up")
    public void  testGetConnectionStringWithFullConfigShouldReturnTheExpectedValue() {
        // arrange
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_DIALECT"))).thenReturn("mysql");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_DRIVER"))).thenReturn("");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_USERNAME"))).thenReturn("foo");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_PASSWORD"))).thenReturn("bar");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_HOST"))).thenReturn("myhost");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_PORT"))).thenReturn("3306");
        Mockito.when(this.mockIEnvironmentWrapper.getVariable(ArgumentMatchers.eq("DB_NAME"))).thenReturn("mydb");

        // act
        var sut = new Configuration(this.mockIEnvironmentWrapper);

        // assert
        Mockito.verify(this.mockIEnvironmentWrapper, Mockito.times(7)).getVariable(ArgumentMatchers.any(String.class));
        Assertions.assertEquals("mysql://foo:bar@myhost:3306/mydb", sut.getConnectionString());
    }
}
