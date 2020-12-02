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
package org.primefaces.extensions.integrationtests.inputnumber;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputNumber;

public class InputNumber004Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("InputNumber: GitHub #6590 Bean validation constraints @Positive still ensure decimalPlaces='0'")
    public void testNoDecimalPlaces(final Page page) {
        // Arrange
        InputNumber inputNumber = page.inputnumber;
        Assertions.assertEquals("66", inputNumber.getValue());

        // Act
        inputNumber.setValue("87.31");

        // Assert
        Assertions.assertEquals("87", inputNumber.getValue());
        assertConfiguration(inputNumber.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("InputNumber: GitHub #6590 Bean validation constraints @Positive doesn't accept negative number")
    public void testPositiveConstraint(final Page page) {
        // Arrange
        InputNumber inputNumber = page.inputnumber;
        Assertions.assertEquals("66", inputNumber.getValue());

        // Act
        try {
            inputNumber.setValue("-5");
            Assertions.fail("Should be blocked by AutoNumeric javascript.");
        }
        catch (JavascriptException ex) {
            // Assert
            Assertions.assertEquals(
                        "The value [-5] being set falls outside of the minimumValue [0.0000001] and maximumValue [999999] range set for this element",
                        StringUtils.substringBetween(ex.getMessage(), ":", "\n"));
        }
    }

    @Test
    @Order(3)
    @DisplayName("InputNumber: GitHub #6590 Bean validation constraints @Max doesn't accept higher than max value")
    public void testMaxConstraint(final Page page) {
        // Arrange
        InputNumber inputNumber = page.inputnumber;
        Assertions.assertEquals("66", inputNumber.getValue());

        // Act
        try {
            inputNumber.setValue("23999999");
            Assertions.fail("Should be blocked by AutoNumeric javascript.");
        }
        catch (JavascriptException ex) {
            // Assert
            Assertions.assertEquals(
                        "The value [23999999] being set falls outside of the minimumValue [0.0000001] and maximumValue [999999] range set for this element",
                        StringUtils.substringBetween(ex.getMessage(), ":", "\n"));
        }
    }

    @Test
    @Order(4)
    @DisplayName("InputNumber: GitHub #6590 Bean validation constraints @Positive removing value")
    public void testRemovingValue(final Page page) {
        // Arrange
        InputNumber inputNumber = page.inputnumber;
        Assertions.assertEquals("66", inputNumber.getValue());

        // Act
        inputNumber.setValue("3");
        inputNumber.getInput().sendKeys(Keys.BACK_SPACE);
        inputNumber.getInput().sendKeys(Keys.DELETE);

        // Assert
        Assertions.assertEquals("", inputNumber.getValue());
        assertConfiguration(inputNumber.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("InputNumber Config = " + cfg);
        Assertions.assertEquals("0", cfg.get("decimalPlaces"));
        Assertions.assertEquals("0.0000001", cfg.get("minimumValue"));
        Assertions.assertEquals("999999", cfg.get("maximumValue"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:inputnumber")
        InputNumber inputnumber;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "inputnumber/inputNumber004.xhtml";
        }
    }
}
