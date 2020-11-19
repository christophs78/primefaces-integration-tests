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
import org.primefaces.extensions.selenium.component.Spinner;

public class Spinner003Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Spinner: Test starting at 0 and decrementing should rotate to max")
    public void testMinRotateAjax(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("0", spinner.getValue());

        // Act
        spinner.getButtonDown().click();

        // Assert
        Assertions.assertEquals("2", spinner.getValue());
        assertOutputLabel(page, "2");
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Spinner: Test starting at 0 and incrementing past max should rotate to min")
    public void testMaxRotateAjax(Page page) {
        // Arrange
        Spinner spinner = page.spinner;
        Assertions.assertEquals("0", spinner.getValue());

        // Act
        spinner.getButtonUp().click();
        assertOutputLabel(page, "1");
        spinner.getButtonUp().click();
        assertOutputLabel(page, "2");
        spinner.getButtonUp().click();

        // Assert
        Assertions.assertEquals("0", spinner.getValue());
        assertOutputLabel(page, "0");
        assertConfiguration(spinner.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Spinner Config = " + cfg);
        Assertions.assertEquals(1, cfg.getInt("step"));
        Assertions.assertEquals(".", cfg.get("decimalSeparator"));
        Assertions.assertEquals(",", cfg.get("thousandSeparator"));
        if (cfg.has("decimalPlaces")) {
            Assertions.assertEquals("0", cfg.get("decimalPlaces"));
        }
        if (cfg.has("precision")) {
            Assertions.assertEquals(0, cfg.getInt("precision"));
        }
    }

    private void assertOutputLabel(Page page, String value) {
        Assertions.assertEquals(value, page.output.getText());
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:integer")
        Spinner spinner;

        @FindBy(id = "form:ajaxSpinnerValue")
        WebElement output;

        @Override
        public String getLocation() {
            return "spinner/spinner003.xhtml";
        }
    }
}
