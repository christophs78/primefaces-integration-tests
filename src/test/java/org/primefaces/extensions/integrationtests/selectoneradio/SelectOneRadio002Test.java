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
package org.primefaces.extensions.integrationtests.selectoneradio;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.SelectOneRadio;

public class SelectOneRadio002Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("SelectOneRadio: Selecting again unselects with unselectable='true'")
    public void testUnselectable(Page page) {
        // Arrange
        SelectOneRadio selectOneRadio = page.selectOneRadio;
        Assertions.assertEquals(4, selectOneRadio.getItemsSize());
        Assertions.assertEquals("Charles", selectOneRadio.getSelectedLabel());

        // Act
        selectOneRadio.select("Charles");
        page.submit.click();

        // Assert -- should be empty
        Assertions.assertEquals("", selectOneRadio.getSelectedLabel());

        // Act
        selectOneRadio.select("Charles");
        page.submit.click();

        // Assert
        Assertions.assertEquals("Charles", selectOneRadio.getSelectedLabel());
        assertConfiguration(selectOneRadio.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("SelectOneRadio Config = " + cfg);
        Assertions.assertTrue(cfg.getBoolean("unselectable"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:selectoneradio")
        SelectOneRadio selectOneRadio;

        @FindBy(id = "form:submit")
        CommandButton submit;

        @Override
        public String getLocation() {
            return "selectoneradio/selectOneRadio002.xhtml";
        }
    }
}
