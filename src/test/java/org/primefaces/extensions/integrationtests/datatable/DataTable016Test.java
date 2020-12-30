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

import java.util.List;
import java.util.stream.Collectors;

public class DataTable016Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: RowGroup")
    public void testRowGroup(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        //page.button.click();

        // Assert
        Assertions.assertNotNull(dataTable.getHeaderWebElement());

        List<WebElement> rowElts = dataTable.getRowsWebElement();
        Assertions.assertNotNull(rowElts);
        Assertions.assertEquals(langs.size() + 2, rowElts.size()); // plus 2 header-rows

        List<Row> rows = dataTable.getRows();
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(langs.size() + 2, rows.size());

        //check header-rows
        Assertions.assertEquals("4", dataTable.getRow(0).getCell(0).getWebElement().getAttribute("colspan"));
        Assertions.assertEquals("COMPILED", dataTable.getRow(0).getCell(0).getText());
        Assertions.assertEquals(1, dataTable.getRow(0).getCell(0).getWebElement().findElements(By.className("ui-rowgroup-toggler")).size());
        Assertions.assertEquals("4", dataTable.getRow(3).getCell(0).getWebElement().getAttribute("colspan"));
        Assertions.assertEquals("INTERPRETED", dataTable.getRow(3).getCell(0).getText());
        Assertions.assertEquals(1, dataTable.getRow(3).getCell(0).getWebElement().findElements(By.className("ui-rowgroup-toggler")).size());

        //remove header-rows
        rows.remove(3);
        rows.remove(0);

        assertRows(rows, langs);

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("groupColumnIndexes"));
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
            return "datatable/dataTable016.xhtml";
        }
    }
}
