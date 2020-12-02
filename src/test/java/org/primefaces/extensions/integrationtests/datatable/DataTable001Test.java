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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.PrimeExpectedConditions;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.model.data.Paginator;
import org.primefaces.extensions.selenium.component.model.datatable.Header;
import org.primefaces.extensions.selenium.component.model.datatable.Row;

public class DataTable001Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: Basic & Paginator")
    public void testBasicAndPaginator(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        //page.button.click();

        // Assert
        Assertions.assertNotNull(dataTable.getPaginatorWebElement());
        Assertions.assertNotNull(dataTable.getHeaderWebElement());

        List<WebElement> rowElts = dataTable.getRowsWebElement();
        Assertions.assertNotNull(rowElts);
        Assertions.assertEquals(3, rowElts.size());

        List<Row> rows = dataTable.getRows();
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(3, rows.size());

        Row firstRow = dataTable.getRow(0);
        Assertions.assertEquals("1", firstRow.getCell(0).getText());
        Assertions.assertEquals("Java", firstRow.getCell(1).getText());

        Header header = dataTable.getHeader();
        Assertions.assertNotNull(header);
        Assertions.assertNotNull(header.getCells());
        Assertions.assertEquals(3, header.getCells().size());
        Assertions.assertEquals("ID", header.getCell(0).getText());
        Assertions.assertEquals("Name", header.getCell(1).getText());

        Paginator paginator = dataTable.getPaginator();
        Assertions.assertNotNull(paginator);
        Assertions.assertNotNull(paginator.getPages());
        Assertions.assertEquals(2, paginator.getPages().size());
        Assertions.assertEquals(1, paginator.getPage(0).getNumber());
        Assertions.assertEquals(2, paginator.getPage(1).getNumber());

        assertConfiguration(dataTable.getWidgetConfiguration());

        // Act
        dataTable.selectPage(2);

        // Assert - Part 2
        rows = dataTable.getRows();
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(2, rows.size());

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("DataTable: single sort")
    public void testSortSingle(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act - ascending
        dataTable.selectPage(1);
        dataTable.sort("Name");

        // Assert
        List<ProgrammingLanguage> langsSorted = langs.stream()
                    .sorted((l1, l2) -> l1.getName().compareTo(l2.getName()))
                    .limit(3)
                    .collect(Collectors.toList());
        assertRows(dataTable, langsSorted);

        // Act - descending
        dataTable.sort("Name");

        // Assert
        langsSorted = langs.stream()
                    .sorted((l1, l2) -> l2.getName().compareTo(l1.getName()))
                    .limit(3)
                    .collect(Collectors.toList());
        assertRows(dataTable, langsSorted);


        // Act
        page.buttonUpdate.click();

        // Assert - filter must not be lost after update
        assertRows(dataTable, langsSorted);

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("DataTable: filter")
    public void testFilter(Page page) {
        // Arrange
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
        page.buttonUpdate.click();

        // Assert - sort must not be lost after update
        assertRows(dataTable, langsFiltered);

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(4)
    @DisplayName("DataTable: rows per page & reset; includes https://github.com/primefaces/primefaces/issues/5465 & https://github.com/primefaces/primefaces/issues/5481")
    public void testRowsPerPageAndReset_5465_5481(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Assert
        Select selectRowsPerPage = new Select(dataTable.getPaginatorWebElement().findElement(By.className("ui-paginator-rpp-options")));
        Assertions.assertEquals("3", selectRowsPerPage.getFirstSelectedOption().getText());
        Assertions.assertEquals(3, dataTable.getRows().size());

        // Act
        dataTable.selectPage(1);
        dataTable.sort("Name");
        selectRowsPerPage.selectByVisibleText("10");
        PrimeSelenium.waitGui().until(PrimeExpectedConditions.jQueryNotActive());

        // Assert
        Assertions.assertEquals(langs.size(), dataTable.getRows().size());

        // Act
        dataTable.filter("Name", "Java");
        PrimeSelenium.waitGui().until(PrimeExpectedConditions.jQueryNotActive());
        PrimeSelenium.guardAjax(page.buttonResetTable).click();

        // Assert
        selectRowsPerPage = new Select(dataTable.getPaginatorWebElement().findElement(By.className("ui-paginator-rpp-options")));
        Assertions.assertEquals("3", selectRowsPerPage.getFirstSelectedOption().getText());
        Assertions.assertEquals(3, dataTable.getRows().size());
        assertRows(dataTable, langs.stream().limit(3).collect(Collectors.toList())); //implicit checks reset sort & filter

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

        @FindBy(id = "form:button")
        CommandButton button;

        @FindBy(id = "form:buttonUpdate")
        CommandButton buttonUpdate;

        @FindBy(id = "form:buttonResetTable")
        CommandButton buttonResetTable;

        @Override
        public String getLocation() {
            return "datatable/dataTable001.xhtml";
        }
    }
}
