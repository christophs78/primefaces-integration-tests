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
import java.util.stream.Collectors;

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

public class DataTable015Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: MultiViewState - sort & filter")
    public void multiViewStateSortFilter(Page page, OtherPage otherPage) {
        // Arrange
        PrimeSelenium.goTo(page);
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        dataTable.selectPage(1);
        dataTable.sort("Name");
        dataTable.filter("Name", "Java");

        // Assert
        List<ProgrammingLanguage> langsFiltered = langs.stream()
                    .sorted((l1, l2) -> l1.getName().compareTo(l2.getName()))
                    .filter(l -> l.getName().startsWith("Java"))
                    .limit(3)
                    .collect(Collectors.toList());
        assertRows(dataTable, langsFiltered);

        // Act
        PrimeSelenium.goTo(otherPage);
        PrimeSelenium.goTo(page);

        // Assert - sort and filter must not be lost after navigating to another page and back
        assertRows(dataTable, langsFiltered);

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("DataTable: MultiViewState - paginator")
    public void multiViewStatePage(Page page, OtherPage otherPage) {
        // Arrange
        PrimeSelenium.goTo(page);
        PrimeSelenium.guardAjax(page.buttonClearTableState).click();
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        dataTable.selectPage(2);
        PrimeSelenium.goTo(otherPage);
        PrimeSelenium.goTo(page);

        // Assert - page must not be lost after navigating to another page and back
        Assertions.assertEquals(2, dataTable.getPaginator().getActivePage().getNumber());

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("paginator"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @FindBy(id = "form:buttonClearTableState")
        CommandButton buttonClearTableState;

        @Override
        public String getLocation() {
            return "datatable/dataTable015.xhtml";
        }
    }

    public static class OtherPage extends AbstractPrimePage {
        @Override
        public String getLocation() {
            return "clientwindow/clientWindow001.xhtml";
        }
    }
}
