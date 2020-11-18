/*
 * Copyright 2011-2020 PrimeFaces Extensions
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.extensions.integrationtests.spinner;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.Spinner;
import org.primefaces.extensions.selenium.component.base.ComponentUtils;

public class Spinner002Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Spinner: Test decimal increment by 0.50")
    public void testSpinUp(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        spinner.increment();
        spinner.increment();
        page.button.click();

        // Assert
        Assertions.assertEquals("0.50", spinner.getValue());
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Spinner: Test decimal decrement by 0.50")
    public void testSpinDown(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        spinner.setValue("4.75");
        spinner.decrement();
        spinner.decrement();
        page.button.click();

        // Assert
        Assertions.assertEquals("4.25", spinner.getValue());
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("Spinner: GitHub #5579 Test decimal invalid characters should be ignored")
    public void testInvalidCharacter(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        sendKeys(spinner, "abc");
        page.button.click();

        // Assert
        Assertions.assertEquals("", spinner.getValue());
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    @Test
    @Order(4)
    @DisplayName("Spinner: GitHub #5579 Test decimal should allow decimal separator")
    public void testDecimalSeparator(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        sendKeys(spinner, "3.4");
        page.button.click();

        // Assert
        Assertions.assertEquals("3.40", spinner.getValue());
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    @Test
    @Order(5)
    @DisplayName("Spinner: GitHub #5579 Test decimal should allow thousand separator")
    public void testThousandSeparator(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        sendKeys(spinner, "1,456.89");
        page.button.click();

        // Assert
        Assertions.assertEquals("1,456.89", spinner.getValue());
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    @Test
    @Order(6)
    @DisplayName("Spinner: Negative number not allowed when min = 0")
    public void testNegativeNumber(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        sendKeys(spinner, "-2.8");
        page.button.click();

        // Assert
        Assertions.assertEquals("2.80", spinner.getValue());
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Spinner Config = " + cfg);
        Assertions.assertEquals(0.25, cfg.getDouble("step"));
        if (cfg.has("decimalPlaces")) {
            Assertions.assertEquals("2", cfg.get("decimalPlaces"));
        }
        if (cfg.has("precision")) {
            Assertions.assertEquals(2, cfg.getInt("precision"));
        }
        Assertions.assertEquals(".", cfg.get("decimalSeparator"));
        Assertions.assertEquals(",", cfg.get("thousandSeparator"));
    }

    public void sendKeys(Spinner spinner, CharSequence value) {
        WebElement input = spinner.getInput();
        ComponentUtils.sendKeys(input, value);
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:decimal")
        Spinner spinner;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "spinner/spinner002.xhtml";
        }
    }
}
