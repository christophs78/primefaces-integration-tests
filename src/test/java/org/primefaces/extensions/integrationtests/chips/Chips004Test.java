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
package org.primefaces.extensions.integrationtests.chips;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.Chips;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputText;
import org.primefaces.extensions.selenium.component.Messages;

public class Chips004Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Chips: GitHub #1895/#6691: Chips allow pasting of delimited list")
    public void testClipboardPaste(Page page) {
        // Arrange
        Chips chips = page.chips;
        InputText clipboard = page.clipboard;

        // Assert initial state
        Assertions.assertEquals("apple;orange;banana", clipboard.getValue());
        Assertions.assertEquals("", chips.getText());
        List<String> values = chips.getValues();
        Assertions.assertEquals(0, values.size());

        // Act - add values and submit
        clipboard.copyToClipboard();
        chips.pasteFromClipboard();
        page.button.click();

        // Assert
        Assertions.assertEquals("apple, orange, banana", page.messages.getMessage(0).getSummary());
        values = chips.getValues();
        Assertions.assertEquals(3, values.size());
        Assertions.assertEquals("orange", values.get(0));
        Assertions.assertEquals("banana", values.get(1));
        Assertions.assertEquals("apple", values.get(2));
        assertConfiguration(chips.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Chips: GitHub #6691: Chips toggle editor between list and tokens")
    public void testToggleEditor(Page page) {
        // Arrange
        Chips chips = page.chips;

        // Assert initial state
        Assertions.assertEquals("", chips.getText());
        List<String> values = chips.getValues();
        Assertions.assertEquals(0, values.size());

        // Act - add values and toggle editor
        chips.addValue("Excel");
        chips.addValue("Outlook");
        chips.addValue("Word");
        assertText(chips, "Excel\nOutlook\nWord");
        chips.toggleEditor();

        // Assert (editing mode)
        Assertions.assertEquals("Excel;Outlook;Word", chips.getInput().getAttribute("value"));

        // Act - close editor and submit
        chips.toggleEditor();
        page.button.click();

        // Assert (final values)
        values = chips.getValues();
        Assertions.assertEquals(3, values.size());
        Assertions.assertEquals("Excel", values.get(0));
        Assertions.assertEquals("Word", values.get(1));
        Assertions.assertEquals("Outlook", values.get(2));
        assertConfiguration(chips.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Chips Config = " + cfg);
        Assertions.assertTrue(cfg.has("id"));
        Assertions.assertTrue(cfg.getBoolean("addOnPaste"));
        Assertions.assertEquals(";", cfg.get("separator"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:clipboard")
        InputText clipboard;

        @FindBy(id = "form:chips")
        Chips chips;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "chips/chips004.xhtml";
        }
    }
}
