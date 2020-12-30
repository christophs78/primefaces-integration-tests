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
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DataTable;

public class DataTable011Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: sort and delete (button with fixed id) - https://stackoverflow.com/questions/24754118/after-sorting-it-deletes-the-wrong-line-of-primefaces-datatable")
    public void testSortAndDelete(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        dataTable.sort("Name");
        page.buttonDeleteId1.click();

        // Assert
        page.dataTable.getRows()
                    .forEach(row -> Assertions.assertNotEquals("1", row.getCell(0).getText()));
    }

    @Test
    @Order(2)
    @DisplayName("DataTable: sort and delete (delete-button get´s object as parameter) - https://stackoverflow.com/questions/24754118/after-sorting-it-deletes-the-wrong-line-of-primefaces-datatable")
    public void testSortAndDeleteV2(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        dataTable.sort("Name");
        PrimeSelenium.guardAjax(dataTable.getCell(1, 3).getWebElement().findElement(By.tagName("button"))).click();

        // Assert
        page.dataTable.getRows()
                    .forEach(row -> Assertions.assertNotEquals("1", row.getCell(0).getText()));
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("paginator"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @FindBy(id = "form:buttonDeleteId1")
        CommandButton buttonDeleteId1;

        @Override
        public String getLocation() {
            return "datatable/dataTable011.xhtml";
        }
    }
}
