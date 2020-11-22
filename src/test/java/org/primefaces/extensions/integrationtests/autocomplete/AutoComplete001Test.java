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

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeExpectedConditions;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.AutoComplete;
import org.primefaces.extensions.selenium.component.CommandButton;

import java.util.List;

public class AutoComplete001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("AutoComplete: usage like normal input; without suggestions")
    public void testBasic(Page page) {
        // Arrange
        AutoComplete autoComplete = page.autoComplete;
        Assertions.assertEquals("test", autoComplete.getValue());
        Assertions.assertNotNull(autoComplete.getPanel());
        Assertions.assertFalse(autoComplete.getPanel().isDisplayed());

        // Act
        autoComplete.deactivate();
        autoComplete.setValue("hello");
        page.button.click();

        // Assert
        Assertions.assertEquals("hello", autoComplete.getValue());
        assertConfiguration(autoComplete.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("AutoComplete: usage like normal input, with suggestions")
    public void testBasic2(Page page) {
        // Arrange
        AutoComplete autoComplete = page.autoComplete;

        // Act
        autoComplete.activate();
        autoComplete.setValueWithoutTab("bye");
        autoComplete.wait4Panel();
        autoComplete.getInput().sendKeys(new CharSequence[]{Keys.TAB});
        page.button.click();

        // Assert
        Assertions.assertEquals("bye0", autoComplete.getValue());
        assertConfiguration(autoComplete.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("AutoComplete: check suggested values")
    public void testSuggestions(Page page) {
        // Arrange
        AutoComplete autoComplete = page.autoComplete;

        // Act
        autoComplete.activate();
        autoComplete.setValueWithoutTab("Prime");
        autoComplete.wait4Panel();

        // Assert - Part 1
        Assertions.assertTrue(autoComplete.getPanel().isDisplayed());
        Assertions.assertNotNull(autoComplete.getItems());
        List<String> itemValues = autoComplete.getItemValues();
        Assertions.assertEquals(10, itemValues.size());
        Assertions.assertEquals("Prime0", itemValues.get(0));
        Assertions.assertEquals("Prime9", itemValues.get(9));

        // Act
        page.button.click();

        // Assert - Part 2
        Assertions.assertEquals("Prime", autoComplete.getValue());
        assertConfiguration(autoComplete.getWidgetConfiguration());
    }

    @Test
    @Order(4)
    @DisplayName("AutoComplete: client-side search-method")
    public void testSearch(Page page) {
        // Arrange
        AutoComplete autoComplete = page.autoComplete;

        // Act
        autoComplete.search("abc");

        // Assert
        Assertions.assertTrue(autoComplete.getPanel().isDisplayed());
        Assertions.assertNotNull(autoComplete.getItems());
        List<String> itemValues = autoComplete.getItemValues();
        Assertions.assertEquals(10, itemValues.size());
        Assertions.assertEquals("abc0", itemValues.get(0));
        Assertions.assertEquals("abc9", itemValues.get(9));
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("AutoComplete Config = " + cfg);
        Assertions.assertTrue(cfg.has("appendTo"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:autocomplete")
        AutoComplete autoComplete;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "autocomplete/autoComplete001.xhtml";
        }
    }
}
