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
package org.primefaces.extensions.integrationtests.inputtextarea;

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
import org.primefaces.extensions.selenium.component.InputTextarea;

public class InputTextArea003Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("InputTextarea: MaxLength using less than max # of characters")
    public void testMaxLengthLessThan(Page page) {
        // Arrange
        InputTextarea inputText = page.inputtext;
        Assertions.assertEquals("", inputText.getValue());

        // Act
        inputText.setValue("four");
        page.button.click();

        // Assert
        Assertions.assertEquals("MaxLength Counter", inputText.getAssignedLabelText());
        Assertions.assertEquals("four", inputText.getValue());
        Assertions.assertEquals("6 characters remaining.", page.display.getText());
        assertConfiguration(inputText.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("InputTextarea: MaxLength using more than max # of characters")
    public void testMaxLengthMoreThan(Page page) {
        // Arrange
        InputTextarea inputText = page.inputtext;
        Assertions.assertEquals("", inputText.getValue());

        // Act
        inputText.setValue("12345678901234");
        page.button.click();

        // Assert
        Assertions.assertEquals("MaxLength Counter", inputText.getAssignedLabelText());
        Assertions.assertEquals("1234567890", inputText.getValue());
        Assertions.assertEquals("0 characters remaining.", page.display.getText());
        assertConfiguration(inputText.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("InputTextarea Config = " + cfg);
        Assertions.assertTrue(cfg.getBoolean("autoResize"));
        Assertions.assertEquals(10, cfg.getInt("maxlength"));
        Assertions.assertEquals("{0} characters remaining.", cfg.getString("counterTemplate"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:inputtext")
        InputTextarea inputtext;

        @FindBy(id = "form:display")
        WebElement display;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "inputtextarea/inputTextArea003.xhtml";
        }
    }
}
