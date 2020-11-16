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

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputNumber;

public class InputNumber003Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("InputNumber: GitHub #6387, #6518 test decimal places with padding zeroes")
    public void testPaddingZeroes(Page page) {
        // Arrange
        InputNumber inputNumber = page.inputnumber;
        Assertions.assertEquals("", inputNumber.getValue());

        // Act
        inputNumber.setValue("1.23");
        page.buttonSubmit.click();

        // Assert
        Assertions.assertEquals("1.230000", inputNumber.getValue());
        Assertions.assertEquals("1.230000", inputNumber.getWidgetValue());
        assertConfiguration(inputNumber.getWidgetConfiguration(), false);
    }

    @Test
    @Order(2)
    @DisplayName("InputNumber: GitHub #6387, #6518 test decimal places without padding zeroes")
    public void testNoPadding(Page page) {
        // Arrange
        InputNumber inputNumber = page.inputnumber;
        Assertions.assertEquals("", inputNumber.getValue());

        // Act
        page.buttonPadControl.click();
        inputNumber.setValue("4.56");
        page.buttonSubmit.click();

        // Assert
        Assertions.assertEquals("4.56", inputNumber.getValue());
        Assertions.assertEquals("4.56", inputNumber.getWidgetValue());
        assertConfiguration(inputNumber.getWidgetConfiguration(), true);
    }

    private void assertConfiguration(JSONObject cfg, boolean hasPadControl) {
        assertNoJavascriptErrors();
        System.out.println("InputNumber Config = " + cfg);
        Assertions.assertEquals("6", cfg.get("decimalPlaces"));
        Assertions.assertEquals(hasPadControl, cfg.has("allowDecimalPadding"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:inputnumber")
        InputNumber inputnumber;

        @FindBy(id = "form:buttonSubmit")
        CommandButton buttonSubmit;

        @FindBy(id = "form:buttonPadControl")
        CommandButton buttonPadControl;

        @Override
        public String getLocation() {
            return "inputnumber/inputNumber003.xhtml";
        }
    }
}
