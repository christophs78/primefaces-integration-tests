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
package org.primefaces.extensions.integrationtests.toggleswitch;

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
import org.primefaces.extensions.selenium.component.ToggleSwitch;
import org.primefaces.extensions.selenium.component.model.Msg;

public class ToggleSwitch001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("ToggleSwitch: Basic test clicking on switch and using Submit button")
    public void testSubmit(Page page) {
        // Arrange
        ToggleSwitch toggleSwitch = page.toggleSwitch;
        Assertions.assertFalse(toggleSwitch.isSelected());

        // Act
        toggleSwitch.click();
        page.submit.click();

        // Assert
        assertChecked(page, true);
    }

    @Test
    @Order(2)
    @DisplayName("ToggleSwitch: Use toggle() widget API to select switch")
    public void testToggleTrue(Page page) {
        // Arrange
        ToggleSwitch toggleSwitch = page.toggleSwitch;
        Assertions.assertFalse(toggleSwitch.isSelected());

        // Act
        toggleSwitch.toggle();

        // Assert
        assertChecked(page, true);
    }

    @Test
    @Order(3)
    @DisplayName("ToggleSwitch: Use toggle() widget API to de-select switch")
    public void testToggleFalse(Page page) {
        // Arrange
        ToggleSwitch toggleSwitch = page.toggleSwitch;
        toggleSwitch.setValue(true);
        Assertions.assertTrue(toggleSwitch.isSelected());

        // Act
        toggleSwitch.toggle();

        // Assert
        assertChecked(page, false);
    }

    @Test
    @Order(4)
    @DisplayName("ToggleSwitch: Use uncheck() widget API to de-select switch")
    public void testUncheck(Page page) {
        // Arrange
        ToggleSwitch toggleSwitch = page.toggleSwitch;
        toggleSwitch.setValue(true);
        Assertions.assertTrue(toggleSwitch.isSelected());

        // Act
        toggleSwitch.uncheck();

        // Assert
        assertChecked(page, false);
    }

    @Test
    @Order(5)
    @DisplayName("ToggleSwitch: Use check() widget API to select switch")
    public void testCheck(Page page) {
        // Arrange
        ToggleSwitch toggleSwitch = page.toggleSwitch;
        toggleSwitch.setValue(false);
        Assertions.assertFalse(toggleSwitch.isSelected());

        // Act
        toggleSwitch.check();

        // Assert
        assertChecked(page, true);
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("ToggleSwitch Config = " + cfg);
    }

    private void assertChecked(Page page, boolean checked) {
        Assertions.assertEquals(checked, page.toggleSwitch.isSelected());
        Msg message = page.messages.getMessage(0);
        Assertions.assertEquals(checked ? "Checked" : "Unchecked", message.getDetail());
        assertConfiguration(page.toggleSwitch.getWidgetConfiguration());
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:toggleSwitch")
        ToggleSwitch toggleSwitch;

        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:submit")
        CommandButton submit;

        @Override
        public String getLocation() {
            return "toggleswitch/toggleSwitch001.xhtml";
        }
    }
}
