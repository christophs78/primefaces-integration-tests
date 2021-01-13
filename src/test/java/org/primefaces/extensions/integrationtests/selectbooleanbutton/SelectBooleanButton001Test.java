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
package org.primefaces.extensions.integrationtests.selectbooleanbutton;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.SelectBooleanButton;
import org.primefaces.extensions.selenium.component.model.Msg;

public class SelectBooleanButton001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("SelectBooleanButton: Basic test clicking on switch and using Submit button")
    public void testSubmit(Page page) {
        // Arrange
        SelectBooleanButton selectBooleanButton = page.selectBooleanButton;
        Assertions.assertFalse(selectBooleanButton.isSelected());

        // Act
        selectBooleanButton.click();
        page.submit.click();

        // Assert
        assertChecked(page, true);
    }

    @Test
    @Order(2)
    @DisplayName("SelectBooleanButton: Use toggle() widget API to select switch")
    public void testToggleTrue(Page page) {
        // Arrange
        SelectBooleanButton selectBooleanButton = page.selectBooleanButton;
        Assertions.assertFalse(selectBooleanButton.isSelected());

        // Act
        selectBooleanButton.toggle();

        // Assert
        assertChecked(page, true);
    }

    @Test
    @Order(3)
    @DisplayName("SelectBooleanButton: Use toggle() widget API to de-select switch")
    public void testToggleFalse(Page page) {
        // Arrange
        SelectBooleanButton selectBooleanButton = page.selectBooleanButton;
        selectBooleanButton.setValue(true);
        Assertions.assertTrue(selectBooleanButton.isSelected());

        // Act
        selectBooleanButton.toggle();

        // Assert
        assertChecked(page, false);
    }

    @Test
    @Order(4)
    @DisplayName("SelectBooleanButton: Use uncheck() widget API to de-select switch")
    public void testUncheck(Page page) {
        // Arrange
        SelectBooleanButton selectBooleanButton = page.selectBooleanButton;
        selectBooleanButton.setValue(true);
        Assertions.assertTrue(selectBooleanButton.isSelected());

        // Act
        selectBooleanButton.uncheck();

        // Assert
        assertChecked(page, false);
    }

    @Test
    @Order(5)
    @DisplayName("SelectBooleanButton: Use check() widget API to select switch")
    public void testCheck(Page page) {
        // Arrange
        SelectBooleanButton selectBooleanButton = page.selectBooleanButton;
        selectBooleanButton.setValue(false);
        Assertions.assertFalse(selectBooleanButton.isSelected());

        // Act
        selectBooleanButton.check();

        // Assert
        assertChecked(page, true);
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("SelectBooleanButton Config = " + cfg);
    }

    private void assertChecked(Page page, boolean checked) {
        SelectBooleanButton selectBooleanButton = page.selectBooleanButton;
        Assertions.assertEquals(checked, selectBooleanButton.isSelected());
        Msg message = page.messages.getMessage(0);
        Assertions.assertEquals(checked ? "Checked" : "Unchecked", message.getDetail());
        Assertions.assertEquals(checked ? "Yes" : "No", selectBooleanButton.getLabel());
        assertConfiguration(selectBooleanButton.getWidgetConfiguration());
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:selectBooleanButton")
        SelectBooleanButton selectBooleanButton;

        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:submit")
        CommandButton submit;

        @Override
        public String getLocation() {
            return "selectbooleanbutton/selectBooleanButton001.xhtml";
        }
    }
}
