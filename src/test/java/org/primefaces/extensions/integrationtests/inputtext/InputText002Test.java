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
package org.primefaces.extensions.integrationtests.inputtext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputText;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.model.Msg;
import org.primefaces.extensions.selenium.component.model.Severity;

public class InputText002Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("InputText: Test floating label empty field does not have class 'ui-state-filled' but has 'ui-state-hover' ")
    public void testEmptyField(Page page) {
        // Arrange
        InputText inputText = page.inputtext;
        Messages messages = page.messages;

        // Act
        inputText.click();

        // Assert
        assertNoJavascriptErrors();
        Assertions.assertEquals("FillMe*", inputText.getAssignedLabelText());
        Assertions.assertEquals("", inputText.getValue());
        assertCss(inputText, "ui-inputfield", "ui-inputtext", "ui-state-hover", "ui-state-focus");

        Assertions.assertEquals(0, messages.getAllMessages().size());
    }

    @Test
    @Order(2)
    @DisplayName("InputText: Test input with data has class 'ui-state-filled'")
    public void testFilledField(Page page) {
        // Arrange
        InputText inputText = page.inputtext;
        Messages messages = page.messages;

        // Act
        inputText.setValue("filled");
        page.button.click();

        // Assert
        assertNoJavascriptErrors();
        Assertions.assertEquals("FillMe*", inputText.getAssignedLabelText());
        Assertions.assertEquals("filled", inputText.getValue());
        assertCss(inputText, "ui-inputfield", "ui-inputtext", "ui-state-filled");
        Assertions.assertEquals(0, messages.getAllMessages().size());
    }

    @Test
    @Order(3)
    @DisplayName("InputText: Test empty input submission causes required error message")
    public void testRequiredFieldError(Page page) {
        // Arrange
        InputText inputText = page.inputtext;
        Messages messages = page.messages;

        // Act
        inputText.setValue("");
        page.button.click();

        // Assert
        assertNoJavascriptErrors();
        Assertions.assertEquals("", inputText.getValue());
        Assertions.assertEquals(1, messages.getAllMessages().size());
        Msg msg = messages.getAllMessages().get(0);
        Assertions.assertEquals(Severity.ERROR, msg.getSeverity());
        Assertions.assertEquals("InputText is required!", msg.getSummary());
        Assertions.assertEquals("InputText is required!", msg.getDetail());
    }

    @Test
    @Order(3)
    @DisplayName("InputText: Test valid input submission does not cause an error.")
    public void testRequiredFieldPass(Page page) {
        // Arrange
        InputText inputText = page.inputtext;
        Messages messages = page.messages;

        // Act
        inputText.setValue("test");
        page.button.click();

        // Assert
        assertNoJavascriptErrors();
        Assertions.assertEquals("test", inputText.getValue());
        Assertions.assertEquals(0, messages.getAllMessages().size());
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:inputtext")
        InputText inputtext;

        @FindBy(id = "form:messages")
        Messages messages;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "inputtext/inputText002.xhtml";
        }
    }
}
