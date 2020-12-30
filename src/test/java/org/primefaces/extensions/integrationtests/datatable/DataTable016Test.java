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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.model.datatable.Row;

public class DataTable016Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: RowGroup - header row and summary row")
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
        Assertions.assertEquals(langs.size() + 2 + 2, rowElts.size()); //plus 2 header-rows plus 2 summary-rows

        List<Row> rows = dataTable.getRows();
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(langs.size() + 2 + 2, rows.size()); //plus 2 header-rows plus 2 summary-rows

        //check header-rows
        Assertions.assertEquals("4", dataTable.getCell(0, 0).getWebElement().getAttribute("colspan"));
        Assertions.assertEquals("COMPILED", dataTable.getCell(0, 0).getText());
        Assertions.assertEquals(1, dataTable.getCell(0, 0).getWebElement().findElements(By.className("ui-rowgroup-toggler")).size());
        Assertions.assertEquals("4", dataTable.getCell(4, 0).getWebElement().getAttribute("colspan"));
        Assertions.assertEquals("INTERPRETED", dataTable.getCell(4, 0).getText());
        Assertions.assertEquals(1, dataTable.getCell(4, 0).getWebElement().findElements(By.className("ui-rowgroup-toggler")).size());

        //check summary-rows
        Assertions.assertEquals("3", dataTable.getCell(3, 0).getWebElement().getAttribute("colspan"));
        Assertions.assertEquals("Total programming languages:", dataTable.getCell(3, 0).getText());
        Assertions.assertEquals("2", dataTable.getCell(3, 1).getText());
        Assertions.assertEquals("3", dataTable.getCell(8, 0).getWebElement().getAttribute("colspan"));
        Assertions.assertEquals("Total programming languages:", dataTable.getCell(8, 0).getText());
        Assertions.assertEquals("3", dataTable.getCell(8, 1).getText());

        //remove header- and summary-rows
        rows.remove(8); //second summary-row
        rows.remove(4); //second header-row
        rows.remove(3); //first summary-row
        rows.remove(0); //first header-row

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

        @Override
        public String getLocation() {
            return "datatable/dataTable016.xhtml";
        }
    }
}
