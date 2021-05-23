/*
 * Copyright (c) 2011-2021 PrimeFaces Extensions
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.primefaces.extensions.integrationtests.datatable;

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

import java.util.List;

/**
 * https://github.com/primefaces/primefaces/issues/7336
 */
public class DataTable021Test extends AbstractDataTableTest {

    @Test
    @Order(1)
    @DisplayName("DataTable: filter and add row")
    public void testFilterAndAddRow(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        dataTable.sort("Name");

        // Act
        dataTable.filter("Name", "Java");

        // Assert
        List<ProgrammingLanguage> langsFiltered = filterByName("Java");
        assertRows(dataTable, langsFiltered);
        int rows = dataTable.getRows().size();

        // Act - one row more than before
        page.buttonAdd.click();
        Assertions.assertEquals(rows + 1, dataTable.getRows().size());

        assertNoJavascriptErrors();
    }

    @Test
    @Order(2)
    @DisplayName("DataTable: filter and remove row")
    public void testFilterAndRemoveRow(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        dataTable.sort("Name");

        // Act
        dataTable.filter("Name", "Java");

        // Assert
        List<ProgrammingLanguage> langsFiltered = filterByName("Java");
        assertRows(dataTable, langsFiltered);
        int rows = dataTable.getRows().size();

        // Act - one row more than before
        WebElement removeButton = dataTable.getRow(0).getCell(3).getWebElement().findElement(By.className("ui-button"));
        removeButton.click();
        Assertions.assertEquals(rows - 1, dataTable.getRows().size());

        assertNoJavascriptErrors();
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @FindBy(id = "form:buttonUpdate")
        CommandButton buttonUpdate;

        @FindBy(id = "form:buttonResetTable")
        CommandButton buttonResetTable;

        @FindBy(id = "form:buttonAdd")
        CommandButton buttonAdd;

        @Override
        public String getLocation() {
            return "datatable/dataTable021.xhtml";
        }
    }
}
