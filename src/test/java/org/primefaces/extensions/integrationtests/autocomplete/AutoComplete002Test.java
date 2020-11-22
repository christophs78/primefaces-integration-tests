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
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeExpectedConditions;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.AutoComplete;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.model.Msg;

import java.util.List;

public class AutoComplete002Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("AutoComplete: completeEndpoint (REST) & POJO")
    public void testSuggestions(Page page) {
        // Arrange
        AutoComplete autoComplete = page.autoComplete;

        // Assert - initial
        Assertions.assertEquals("Driver No. 4", autoComplete.getValue());

        // Act
        autoComplete.setValueWithoutTab("1");
        autoComplete.wait4Panel();

        // Assert - Part 1
        Assertions.assertTrue(autoComplete.getPanel().isDisplayed());
        Assertions.assertNotNull(autoComplete.getItems());
        List<String> itemValues = autoComplete.getItemValues();
        Assertions.assertEquals(14, itemValues.size());
        Assertions.assertEquals("Driver No. 1", itemValues.get(0));
        Assertions.assertEquals("Driver No. 10", itemValues.get(1));
        Assertions.assertEquals("Driver No. 19", itemValues.get(10));
        Assertions.assertEquals("Driver No. 41", itemValues.get(13));

        // Act
        autoComplete.setValue("15");
        page.button.click();

        // Assert - Part 2
        Assertions.assertEquals("Driver No. 15", autoComplete.getValue());
        assertConfiguration(autoComplete.getWidgetConfiguration());

        Msg message = page.messages.getMessage(0);
        Assertions.assertNotNull(message);
        Assertions.assertEquals("Driver", message.getSummary());
        Assertions.assertEquals("id: 15, name: Driver No. 15", message.getDetail());

        assertConfiguration(autoComplete.getWidgetConfiguration());
    }


    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("AutoComplete Config = " + cfg);
        Assertions.assertTrue(cfg.has("appendTo"));
        Assertions.assertTrue(cfg.has("completeEndpoint"));
        Assertions.assertTrue(cfg.has("moreText"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:autocomplete")
        AutoComplete autoComplete;

        @FindBy(id = "form:button")
        CommandButton button;

        @FindBy(id = "form:msgs")
        Messages messages;

        @Override
        public String getLocation() {
            return "autocomplete/autoComplete002.xhtml";
        }
    }
}
