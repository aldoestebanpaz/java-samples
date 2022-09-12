package com.aldo.samples.testing.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class CustomerTest {
    @Test
    @DisplayName("Customer object should be initialized with expected values")
    public void  testCustomerCreatedWithExpectedValues() {
        // arrange
        var name = "aldo";
        var email = "apaz@noemail.com";

        // act
        var customer = new Customer() {{ setName(name); setEmail(email); }};

        // assert
        Assertions.assertEquals(name, customer.getName());
        Assertions.assertEquals(email, customer.getEmail());
        Assertions.assertEquals(0, customer.getId());
    }

    @ParameterizedTest
    @CsvSource({
            "\"aldo\", \"apaz@noemail.com\"",
            "\"ella\", \"ella@ella.com\"",
            "\"foo\", \"foo@bar.com\"",
    })
    @DisplayName("Customer object should be initialized with expected values")
    public void  testCustomerCreatedWithExpectedValues(String name, String email) {
        // act
        var customer = new Customer() {{ setName(name); setEmail(email); }};

        // assert
        Assertions.assertEquals(name, customer.getName());
        Assertions.assertEquals(email, customer.getEmail());
        Assertions.assertEquals(0, customer.getId());
    }

    @Test
    @DisplayName("Customer object should be initialized with expected values and custom date")
    public void  testCustomerCreatedWithExpectedValuesAndCustomDate() {
        // arrange
        var name = "aldo";
        var email = "apaz@noemail.com";

        // Parse as a LocalDateTime because your input string lacks any indicator of
        // time zone (https://en.wikipedia.org/wiki/Time_zone) or offset-from-UTC (https://en.wikipedia.org/wiki/UTC_offset).
        // Such a value is not a moment, is not a point on the timeline. It is only a set of potential moments
        // along a range of about 26-27 hours.
        var localDateTime = LocalDateTime.parse(
                // This input uses a poor choice of format. Whenever possible, use standard ISO 8601 formats
                // when exchanging date-time values as text. Conveniently, the java.time classes use the standard
                // formats by default when parsing/generating strings.
                "2022/08/30 13:26:15",
                DateTimeFormatter.ofPattern( "yyyy/MM/dd HH:mm:ss" , Locale.US )
        );

        // Apply a zone to that unzoned `LocalDateTime`, giving it meaning, determining a moment, a point on the timeline.
        // If you know for certain that input was intended to represent a moment using the wall-clock time used
        // by the people of the Toronto Canada region, apply a ZoneId to get a ZonedDateTime object.
        // NOTE: Always specify a proper time zone with `Contintent/Region` format, never a 3-4 letter pseudo-zone such as `PST`, `CST`, or `IST`.
        var zonedDateTime = localDateTime.atZone(ZoneId.of( "America/Toronto" ));
        // Extract a `Instant` object, always in UTC by definition. Same moment, different wall-clock time.
        var currDateTime = zonedDateTime.toInstant();

        // act
        var customer = new Customer(name, email, currDateTime);

        // assert
        Assertions.assertEquals(name, customer.getName());
        Assertions.assertEquals(email, customer.getEmail());
        Assertions.assertEquals(currDateTime, customer.getCreatedDate());
    }
}
