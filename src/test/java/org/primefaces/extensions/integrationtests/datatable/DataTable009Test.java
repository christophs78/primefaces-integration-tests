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

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.PrimeExpectedConditions;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.SelectOneMenu;
import org.primefaces.extensions.selenium.component.model.datatable.Row;

public class DataTable009Test extends AbstractDataTableTest {

    @Test
    @Order(1)
    @DisplayName("DataTable: filter - issue 1390 - https://github.com/primefaces/primefaces/issues/1390")
    public void testFilterIssue1390(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act - do some filtering
        dataTable.sort("Name");
        dataTable.filter("Name", "Java");
        PrimeExpectedConditions.visibleAndAnimationComplete(page.messages);

        // Assert
        Assertions.assertEquals("FilterValue for name", page.messages.getMessage(0).getSummary());
        Assertions.assertEquals("Java", page.messages.getMessage(0).getDetail());
        Assertions.assertEquals("FilteredValue(s)", page.messages.getMessage(1).getSummary());
        Assertions.assertEquals("Java,JavaScript", page.messages.getMessage(1).getDetail());

        // Act - do some other filtering
        dataTable.filter("Name", "JavaScript");

        // Assert
        Assertions.assertEquals("FilterValue for name", page.messages.getMessage(0).getSummary());
        Assertions.assertEquals("JavaScript", page.messages.getMessage(0).getDetail());
        Assertions.assertEquals("FilteredValue(s)", page.messages.getMessage(1).getSummary());
        Assertions.assertEquals("JavaScript", page.messages.getMessage(1).getDetail());

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("DataTable: filter - issue 7026 - https://github.com/primefaces/primefaces/issues/7026")
    public void testFilterIssue7026(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act - do some filtering
        dataTable.sort("Name");
        page.firstAppearedFilter.select("2000");
        PrimeExpectedConditions.visibleAndAnimationComplete(page.messages);

        // Assert
        validateFirstAppeared4EachRow(dataTable, "2000");
        Assertions.assertEquals("FilterValue for firstAppeared", page.messages.getMessage(0).getSummary());
        Assertions.assertEquals("2000", page.messages.getMessage(0).getDetail());
        Assertions.assertEquals("FilteredValue(s)", page.messages.getMessage(1).getSummary());
        Assertions.assertEquals("C#", page.messages.getMessage(1).getDetail());

        // Act - do some other filtering
        page.firstAppearedFilter.select("1995");

        // Assert
        validateFirstAppeared4EachRow(dataTable, "1995");
        Assertions.assertEquals("FilterValue for firstAppeared", page.messages.getMessage(0).getSummary());
        Assertions.assertEquals("1995", page.messages.getMessage(0).getDetail());
        Assertions.assertEquals("FilteredValue(s)", page.messages.getMessage(1).getSummary());
        Assertions.assertEquals("Java,JavaScript", page.messages.getMessage(1).getDetail());

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    private void validateFirstAppeared4EachRow(DataTable dataTable, String firstAppearedExpected) {
        int rowNumber = 0;
        for (Row row : dataTable.getRows()) {
            SelectOneMenu firstAppeared = PrimeSelenium.createFragment(SelectOneMenu.class, By.id("form:datatable:" + rowNumber + ":firstAppeared"));
            Assertions.assertEquals(firstAppearedExpected, firstAppeared.getSelectedLabel());
            rowNumber++;
        }
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("filter"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @FindBy(id = "form:datatable:firstAppearedFilter")
        SelectOneMenu firstAppearedFilter;

        @Override
        public String getLocation() {
            return "datatable/dataTable009.xhtml";
        }
    }
}
