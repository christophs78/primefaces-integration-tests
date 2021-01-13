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
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputTextarea;

public class InputTextArea001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    public void testInputTextAreaWithAjax(Page page) {
        // Arrange
        InputTextarea inputText = page.inputtext1;
        Assertions.assertEquals("byebye!", inputText.getValue());

        // Act
        inputText.setValue("hello!");
        page.button.click();

        // Assert
        Assertions.assertEquals("hello!", inputText.getValue());
        assertConfiguration(inputText.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    public void testInputTextAreaWithoutAjax(Page page) {
        // Arrange
        InputTextarea inputText = page.inputtext2;
        Assertions.assertEquals("safari", inputText.getValue());

        // Act
        inputText.setValue("hello safari!");
        page.button.click();

        // Assert
        Assertions.assertEquals("hello safari!", inputText.getValue());
        assertConfiguration(inputText.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("InputTextarea Config = " + cfg);
        Assertions.assertFalse(cfg.has("maxlength"));
        Assertions.assertTrue(cfg.getBoolean("autoResize"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:inputtext1")
        InputTextarea inputtext1;

        @FindBy(id = "form:inputtext2")
        InputTextarea inputtext2;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "inputtextarea/inputTextArea001.xhtml";
        }
    }
}
