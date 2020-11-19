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

public class Spinner005Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Spinner: Test round to the nearest stepFactor")
    public void testRound(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        sendKeys(spinner, "12");
        page.button.click();

        // Assert
        Assertions.assertEquals("15", spinner.getValue());
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    @Test
    @Order(1)
    @DisplayName("Spinner: Test round already on stepFactor does not modify value")
    public void testRoundNoChange(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        sendKeys(spinner, "20");
        page.button.click();

        // Assert
        Assertions.assertEquals("20", spinner.getValue());
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("Spinner: Test entering above max value rounds to max")
    public void testRoundAboveMax(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        sendKeys(spinner, "67");
        page.button.click();

        // Assert
        Assertions.assertEquals("55", spinner.getValue());
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Spinner Config = " + cfg);
        Assertions.assertEquals(5, cfg.getInt("step"));
        Assertions.assertEquals(".", cfg.get("decimalSeparator"));
        Assertions.assertEquals(",", cfg.get("thousandSeparator"));
        Assertions.assertEquals(0, cfg.getInt("precision"));
        Assertions.assertEquals("0", cfg.get("decimalPlaces"));
        Assertions.assertTrue(cfg.getBoolean("round"));
    }

    public void sendKeys(Spinner spinner, CharSequence value) {
        WebElement input = spinner.getInput();
        ComponentUtils.sendKeys(input, value);
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:integer")
        Spinner spinner;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "spinner/spinner005.xhtml";
        }
    }
}
