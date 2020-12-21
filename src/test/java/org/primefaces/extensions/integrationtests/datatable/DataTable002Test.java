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

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeExpectedConditions;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.base.ComponentUtils;
import org.primefaces.extensions.selenium.component.model.datatable.Row;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DataTable002Test extends AbstractPrimePageTest {

    private ProgrammingLanguageLazyDataModel langs = new ProgrammingLanguageLazyDataModel();

    @Test
    @Order(1)
    @DisplayName("DataTable: Lazy: Basic & Paginator")
    public void testLazyAndPaginator(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        //page.button.click();

        // Assert
        List<Row> rows = dataTable.getRows();
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(10, rows.size());

        Row firstRow = dataTable.getRow(0);
        Assertions.assertEquals("1", firstRow.getCell(0).getText());
        Assertions.assertEquals("Language 1", firstRow.getCell(1).getText());

        assertConfiguration(dataTable.getWidgetConfiguration());

        // Act - second page
        dataTable.selectPage(2);

        // Assert
        rows = dataTable.getRows();
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(10, rows.size());

        firstRow = dataTable.getRow(0);
        Assertions.assertEquals("11", firstRow.getCell(0).getText());
        Assertions.assertEquals("Language 11", firstRow.getCell(1).getText());

        assertConfiguration(dataTable.getWidgetConfiguration());

        // Act - last page
        dataTable.selectPage(8);

        // Assert
        rows = dataTable.getRows();
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(5, rows.size());

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("DataTable: Lazy: single sort")
    public void testLazySortSingle(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);
        List<ProgrammingLanguage> langsAsc = langs.getLangs().stream().sorted(Comparator.comparing(ProgrammingLanguage::getName)).collect(Collectors.toList());
        List<ProgrammingLanguage> langsDesc = langs.getLangs().stream().sorted(Comparator.comparing(ProgrammingLanguage::getName).reversed()).collect(Collectors.toList());

        // Act - ascending
        dataTable.selectPage(1);
        dataTable.sort("Name");

        // Assert
        List<Row> rows = dataTable.getRows();
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(10, rows.size());

        Assertions.assertEquals(langsAsc.get(0).getName(), rows.get(0).getCell(1).getText());
        Assertions.assertEquals(langsAsc.get(1).getName(), rows.get(1).getCell(1).getText());
        Assertions.assertEquals(langsAsc.get(9).getName(), rows.get(9).getCell(1).getText());

        // Act - descending
        dataTable.sort("Name");

        // Assert
        rows = dataTable.getRows();
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(10, rows.size());

        Assertions.assertEquals(langsDesc.get(0).getName(), rows.get(0).getCell(1).getText());
        Assertions.assertEquals(langsDesc.get(1).getName(), rows.get(1).getCell(1).getText());
        Assertions.assertEquals(langsDesc.get(9).getName(), rows.get(9).getCell(1).getText());

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("DataTable: Lazy: filter")
    public void testLazyFilter(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);
        List<ProgrammingLanguage> langsFiltered = langs.getLangs().stream()
                .filter(l -> l.getFirstAppeared() >= 1998)
                .sorted(Comparator.comparingInt(ProgrammingLanguage::getFirstAppeared))
                .collect(Collectors.toList());

        // Act
        dataTable.selectPage(1);
        dataTable.sort("First appeared");
        dataTable.filter("First appeared", "1998");

        // Assert
        List<Row> rows = dataTable.getRows();
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(10, rows.size()); //one page
        //TODO: implement dataTable.getTotalRowCount()
        //Assertions.assertEquals(langsFiltered.size(), dataTable.getTotalRowCount());

        Assertions.assertEquals(langsFiltered.get(0).getName(), rows.get(0).getCell(1).getText());
        Assertions.assertEquals(langsFiltered.get(1).getName(), rows.get(1).getCell(1).getText());

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(4)
    @DisplayName("DataTable: Lazy: rowSelect")
    public void testLazyRowSelect(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        PrimeSelenium.guardAjax(dataTable.getRow(3).getCell(0).getWebElement()).click();

        // Assert
        Assertions.assertEquals(1, page.messages.getAllMessages().size());
        Assertions.assertEquals("ProgrammingLanguage Selected", page.messages.getMessage(0).getSummary());
        String row3ProgLang = dataTable.getRow(3).getCell(0).getText() + " - " + dataTable.getRow(3).getCell(1).getText();
        Assertions.assertEquals(row3ProgLang, page.messages.getMessage(0).getDetail());

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("paginator"));
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
            return "datatable/dataTable002.xhtml";
        }
    }
}
