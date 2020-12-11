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
import org.primefaces.extensions.selenium.component.Messages;

public class Chips002Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Chips: Test max attribute only allowing max items")
    public void testBasic(Page page) {
        // Arrange
        Chips chips = page.chips;

        // Assert initial state
        Assertions.assertEquals("Defect\nFeature", chips.getText());
        List<String> values = chips.getValues();
        Assertions.assertEquals(2, values.size());
        Assertions.assertEquals("Defect", values.get(0));
        Assertions.assertEquals("Feature", values.get(1));

        // Act - add values
        chips.addValue("Enhancement");
        chips.addValue("Question"); //should not be added as its over max=3

        // Act - submit
        page.button.click();

        // Assert
        Assertions.assertEquals("Defect, Feature, Enhancement", page.messages.getMessage(0).getSummary());
        values = chips.getValues();
        Assertions.assertEquals(3, values.size());
        Assertions.assertEquals("Defect", values.get(0));
        Assertions.assertEquals("Feature", values.get(1));
        Assertions.assertEquals("Enhancement", values.get(2));
        assertConfiguration(chips.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Chips Config = " + cfg);
        Assertions.assertTrue(cfg.has("id"));
        Assertions.assertEquals(3, cfg.getInt("max"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:chips")
        Chips chips;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "chips/chips002.xhtml";
        }
    }
}
