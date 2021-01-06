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
package org.primefaces.extensions.integrationtests.tristatecheckbox;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.TriStateCheckbox;

public class TriStateCheckbox001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("TriStateCheckbox: click through all states")
    public void testClickThrough(Page page) {
        // Arrange / Assert
        TriStateCheckbox triStateCheckbox = page.triStateCheckbox;
        Assertions.assertEquals("0", triStateCheckbox.getValue());

        // Act
        triStateCheckbox.click();

        // Assert
        Assertions.assertEquals("1", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());

        // Act
        triStateCheckbox.click();

        // Assert
        Assertions.assertEquals("2", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());

        // Act
        page.button.click();

        // Assert
        Assertions.assertEquals("2", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("TriStateCheckbox: setValue")
    public void testSetValue(Page page) {
        // Arrange
        TriStateCheckbox triStateCheckbox = page.triStateCheckbox;
        triStateCheckbox.setValue("1");

        // Act

        // Assert
        Assertions.assertEquals("1", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());

        // Act
        page.button.click();

        // Assert
        Assertions.assertEquals("1", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("TriStateCheckbox Config = " + cfg);
        Assertions.assertTrue(cfg.has("id"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:triStateCheckbox")
        TriStateCheckbox triStateCheckbox;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "tristatecheckbox/triStateCheckbox001.xhtml";
        }
    }
}
