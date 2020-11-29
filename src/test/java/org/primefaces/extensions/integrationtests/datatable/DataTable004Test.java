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
package org.primefaces.extensions.integrationtests.datatable;

import java.util.List;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.Messages;

public class DataTable004Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: selection - single; click on row & events")
    public void testSelectionSingle(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        PrimeSelenium.guardAjax(dataTable.getRow(2).getCell(0).getWebElement()).click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("ProgrammingLanguage Selected"));
        Assertions.assertTrue(page.messages.getMessage(0).getDetail().contains(langs.get(2).getName()));

        // Act - other row
        PrimeSelenium.guardAjax(dataTable.getRow(4).getCell(0).getWebElement()).click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("ProgrammingLanguage Selected"));
        Assertions.assertTrue(page.messages.getMessage(0).getDetail().contains(langs.get(4).getName()));

        // Act - submit button
        page.button.click();

        // Assert
        assertConfiguration(dataTable.getWidgetConfiguration());
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Selected ProgrammingLanguage"));
        Assertions.assertTrue(page.messages.getMessage(0).getDetail().contains(langs.get(4).getName()));
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("selectionMode"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "datatable/dataTable004.xhtml";
        }
    }
}
