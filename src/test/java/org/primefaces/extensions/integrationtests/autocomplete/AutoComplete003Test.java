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
package org.primefaces.extensions.integrationtests.autocomplete;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.AutoComplete;
import org.primefaces.extensions.selenium.component.CommandButton;

public class AutoComplete003Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("AutoComplete: multiple mode with unique values")
    public void testMultiple(Page page) {
        // Arrange
        AutoComplete autoComplete = page.autoComplete;

        // Assert initial state
        List<String> values = autoComplete.getValues();
        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals("Ringo", values.get(0));

        // Act
        autoComplete.addItem("Ringo"); // duplicate should be ignored
        autoComplete.addItem("John");
        autoComplete.addItem("George");
        autoComplete.addItem("Paul"); // over the limit of 3 should not be added
        page.button.click();

        // Assert
        values = autoComplete.getValues();
        Assertions.assertEquals(3, values.size());
        Assertions.assertEquals("George", values.get(0));
        Assertions.assertEquals("John", values.get(1));
        Assertions.assertEquals("Ringo", values.get(2));
        assertConfiguration(autoComplete.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("AutoComplete: multiple mode add item and remove item")
    public void testAddAndRemoveItem(Page page) {
        // Arrange
        AutoComplete autoComplete = page.autoComplete;

        // Assert initial state
        List<String> values = autoComplete.getValues();
        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals("Ringo", values.get(0));

        // Act
        autoComplete.addItem("Paul");
        autoComplete.removeItem("Ringo");
        page.button.click();

        // Assert
        values = autoComplete.getValues();
        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals("Paul", values.get(0));
        assertConfiguration(autoComplete.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("AutoComplete: adding/removing an item while disabled should have no effect")
    public void testDisabled(Page page) {
        // Arrange
        AutoComplete autoComplete = page.autoComplete;

        // Assert initial state
        List<String> values = autoComplete.getValues();
        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals("Ringo", values.get(0));

        // Act
        autoComplete.disable();
        autoComplete.removeItem("Ringo");
        autoComplete.addItem("Paul");
        page.button.click();

        // Assert
        values = autoComplete.getValues();
        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals("Ringo", values.get(0));
        assertConfiguration(autoComplete.getWidgetConfiguration());
    }

    @Test
    @Order(4)
    @DisplayName("AutoComplete: test enabling and disabling")
    public void testEnabled(Page page) {
        // Arrange
        AutoComplete autoComplete = page.autoComplete;

        // Assert initial state
        List<String> values = autoComplete.getValues();
        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals("Ringo", values.get(0));

        // Act
        autoComplete.disable();
        autoComplete.removeItem("Ringo");
        autoComplete.addItem("Paul");
        autoComplete.enable();
        autoComplete.removeItem("Ringo");
        autoComplete.addItem("George");
        page.button.click();

        // Assert
        values = autoComplete.getValues();
        Assertions.assertEquals(1, values.size());
        Assertions.assertEquals("George", values.get(0));
        assertConfiguration(autoComplete.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("AutoComplete Config = " + cfg);
        Assertions.assertTrue(cfg.getBoolean("multiple"));
        Assertions.assertTrue(cfg.getBoolean("unique"));
        Assertions.assertEquals(3, cfg.getInt("selectLimit"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:autocomplete")
        AutoComplete autoComplete;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "autocomplete/autoComplete003.xhtml";
        }
    }
}
