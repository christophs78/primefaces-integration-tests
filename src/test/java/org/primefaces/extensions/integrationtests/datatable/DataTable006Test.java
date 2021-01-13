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
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.model.Msg;

public class DataTable006Test extends AbstractDataTableTest {

    @Test
    @Order(1)
    @DisplayName("DataTable: selection - multiple with checkboxes")
    public void testSelectionMultipleCheckboxes(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;

        // Act
        dataTable.getCell(0, 0).getWebElement().click();
        dataTable.getCell(2, 0).getWebElement().click();
        page.submit.click();

        // Assert
        assertSelectAllCheckbox(dataTable, false);
        assertSelections(page.messages, "1,3");
        assertConfiguration(dataTable.getWidgetConfiguration(), true);
    }

    @Test
    @Order(2)
    @DisplayName("DataTable: selection - lazy multiple with checkboxes")
    public void testLazySelectionMultipleCheckboxes(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        page.toggleLazyMode.click();

        // Act
        dataTable.getCell(0, 0).getWebElement().click();
        dataTable.getCell(2, 0).getWebElement().click();
        page.submit.click();

        // Assert
        assertSelectAllCheckbox(dataTable, false);
        assertSelections(page.messages, "1,3");
        assertConfiguration(dataTable.getWidgetConfiguration(), true);
    }

    @Test
    @Order(3)
    @DisplayName("DataTable: selection - select all for page with selectionPageOnly='true'")
    public void testSelectAllPageOnly(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;

        // Act - select all
        dataTable.toggleSelectAllCheckBox();
        page.submit.click();

        // Assert
        assertSelectAllCheckbox(dataTable, true);
        assertSelections(page.messages, "1,2,3");

        // Act - unselect one row
        dataTable.getCell(1, 0).getWebElement().click();
        page.submit.click();

        // Assert
        assertSelectAllCheckbox(dataTable, false);
        assertConfiguration(dataTable.getWidgetConfiguration(), true);
        assertSelections(page.messages, "1,3");
    }

    @Test
    @Order(4)
    @DisplayName("DataTable: selection - Lazy select all for page with selectionPageOnly='true'")
    public void testLazySelectAllPageOnly(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        page.toggleLazyMode.click();

        // Act - select all
        dataTable.toggleSelectAllCheckBox();
        page.submit.click();

        // Assert
        assertSelectAllCheckbox(dataTable, true);
        assertSelections(page.messages, "1,2,3");

        // Act - unselect one row
        dataTable.getCell(1, 0).getWebElement().click();
        page.submit.click();

        // Assert
        assertSelectAllCheckbox(dataTable, false);
        assertConfiguration(dataTable.getWidgetConfiguration(), true);
        assertSelections(page.messages, "1,3");
    }

    @Test
    @Order(3)
    @DisplayName("DataTable: selection - unselect all for page with selectionPageOnly='true'")
    public void testUnselectAllPageOnly(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;

        // Act - select all
        dataTable.toggleSelectAllCheckBox();
        page.submit.click();

        // Assert
        assertSelectAllCheckbox(dataTable, true);
        assertSelections(page.messages, "1,2,3");

        // Act - unselect all
        dataTable.toggleSelectAllCheckBox();
        page.submit.click();

        // Assert
        assertSelections(page.messages, "");
        assertSelectAllCheckbox(dataTable, false);
        assertConfiguration(dataTable.getWidgetConfiguration(), true);
    }

    @Test
    @Order(5)
    @DisplayName("DataTable: GitHub #6730 selection - select all rows with selectionPageOnly='false'")
    public void testSelectAllRows(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;

        // Act - select all
        page.toggleSelectPageOnly.click();
        dataTable.toggleSelectAllCheckBox();
        page.submit.click();

        // Assert
        assertSelectAllCheckbox(dataTable, false);
        assertSelections(page.messages, "1,2,3,4,5");

        // Act - unselect one row
        dataTable.getCell(1, 0).getWebElement().click();
        page.submit.click();

        // Assert - only 1 record unselected
        assertSelectAllCheckbox(dataTable, false);
        assertSelections(page.messages, "1,3,4,5");

        // Act - reselect all record, unselect one and move to next page
        dataTable.toggleSelectAllCheckBox();
        dataTable.getCell(0, 0).getWebElement().click();
        dataTable.selectPage(2);
        page.submit.click();

        // Assert - only 1 record unselected should leave first page selections only
        assertSelections(page.messages, "2,3");
        assertConfiguration(dataTable.getWidgetConfiguration(), false);
    }

    @Test
    @Order(6)
    @DisplayName("DataTable: GitHub #6730 selection - Lazy select all rows with selectionPageOnly='false'")
    public void testLazySelectAllRows(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        page.toggleLazyMode.click();

        // Act - select all
        page.toggleSelectPageOnly.click();
        dataTable.toggleSelectAllCheckBox();
        page.submit.click();

        // Assert
        assertSelectAllCheckbox(dataTable, false);
        assertSelections(page.messages,
                    "1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3");

        // Act - unselect one row
        dataTable.getCell(1, 0).getWebElement().click();
        page.submit.click();

        // Assert - only 1 record unselected
        assertSelectAllCheckbox(dataTable, false);
        assertSelections(page.messages, "1,3");

        // Act - reselect all record, unselect one and move to next page
        dataTable.toggleSelectAllCheckBox();
        dataTable.getCell(0, 0).getWebElement().click();
        dataTable.selectPage(2);
        page.submit.click();

        // Assert - only 1 record unselected should leave first page selections only
        assertSelections(page.messages, "2,3");
        assertConfiguration(dataTable.getWidgetConfiguration(), false);
    }

    private void assertConfiguration(JSONObject cfg, boolean selectionPageOnly) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertEquals("checkbox", cfg.get("selectionMode"));
        Assertions.assertEquals(selectionPageOnly, cfg.getBoolean("selectionPageOnly"));
    }

    private void assertSelectAllCheckbox(DataTable datatable, boolean checked) {
        WebElement selectAllCheckBox = datatable.getSelectAllCheckBox();
        WebElement icon = selectAllCheckBox.findElement(By.className("ui-chkbox-icon"));
        assertCss(icon, checked ? "ui-icon-check" : "ui-icon-blank");
    }

    private void assertSelections(Messages messages, String selections) {
        Msg message = messages.getMessage(0);
        Assertions.assertTrue(message.getSummary().contains("Selected ProgrammingLanguage(s)"));
        Assertions.assertEquals(selections, message.getDetail());
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @FindBy(id = "form:button")
        CommandButton submit;

        @FindBy(id = "form:toggleSelectPageOnly")
        CommandButton toggleSelectPageOnly;

        @FindBy(id = "form:toggleLazyMode")
        CommandButton toggleLazyMode;

        @Override
        public String getLocation() {
            return "datatable/dataTable006.xhtml";
        }
    }
}
