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

public class Spinner004Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Spinner: Test decimal increment by 0.33 using euro notation")
    public void testSpinnerForEuroNotation(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        spinner.increment();
        page.button.click();

        // Assert
        Assertions.assertEquals("€0.33", spinner.getValue());
        assertOutputLabel(page, "0.33");
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    @Test
    @Order(1)
    @DisplayName("Spinner: Test setting large value for euro notation")
    public void testEuroNotationWithThousandsAndDecimal(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("", spinner.getValue());

        // Act
        sendKeys(spinner, "1.456,78");
        page.button.click();

        // Assert
        Assertions.assertEquals("€1456.78", spinner.getValue()); // TODO: this expected result seems odd to me
        assertOutputLabel(page, "1456.78");
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Spinner Config = " + cfg);
        Assertions.assertEquals(0.33, cfg.getDouble("step"));
        if (cfg.has("decimalPlaces")) {
            Assertions.assertEquals("2", cfg.get("decimalPlaces"));
        }
        if (cfg.has("precision")) {
            Assertions.assertEquals(2, cfg.getInt("precision"));
        }
        Assertions.assertEquals(",", cfg.get("decimalSeparator"));
        Assertions.assertEquals(".", cfg.get("thousandSeparator"));
    }

    private void assertOutputLabel(Page page, String value) {
        Assertions.assertEquals(value, page.output.getText());
    }

    public void sendKeys(Spinner spinner, CharSequence value) {
        WebElement input = spinner.getInput();
        ComponentUtils.sendKeys(input, value);
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:decimal")
        Spinner spinner;

        @FindBy(id = "form:output")
        WebElement output;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "spinner/spinner004.xhtml";
        }
    }
}
