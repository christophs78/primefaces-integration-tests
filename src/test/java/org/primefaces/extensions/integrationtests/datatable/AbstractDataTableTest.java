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

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebElement;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.model.datatable.Row;

public abstract class AbstractDataTableTest extends AbstractPrimePageTest {
    protected void assertRows(DataTable dataTable, List<ProgrammingLanguage> langs) {
        List<Row> rows = dataTable.getRows();
        assertRows(rows, langs);
    }

    protected void assertRows(List<Row> rows, List<ProgrammingLanguage> langs) {
        Assertions.assertNotNull(rows);
        Assertions.assertEquals(langs.size(), rows.size());

        int row = 0;
        for (ProgrammingLanguage programmingLanguage : langs) {
            String rowText = rows.get(row).getCell(0).getText();
            if (!rowText.equalsIgnoreCase("No records found.")) {
                Assertions.assertEquals(programmingLanguage.getId(), Integer.parseInt(rowText));
                row++;
            }
        }
    }

    protected void assertHeaderSorted(WebElement header, String sortDirection, int sortPriority) {
        String directionClass = null;
        switch (sortDirection) {
            case "ASC":
                directionClass = "ui-icon-triangle-1-n";
                break;
            case "DESC":
                directionClass = "ui-icon-triangle-1-s";
                break;
            default:
                break;
        }
        Assertions.assertTrue(PrimeSelenium.hasCssClass(header.findElement(By.className("ui-sortable-column-icon")), directionClass));
        WebElement badge = header.findElement(By.className("ui-sortable-column-badge"));
        if (sortPriority > 0) {
            Assertions.assertEquals(sortPriority, Integer.parseInt(badge.getText()));
        }
        else {
            Assertions.assertEquals("", badge.getText());
        }
    }

    protected void logWebDriverCapabilites(DataTable003Test.Page page) {
        if (page.getWebDriver() instanceof HasCapabilities) {
            HasCapabilities hasCaps = (HasCapabilities) page.getWebDriver();
            System.out.println("BrowserName: " + hasCaps.getCapabilities().getBrowserName());
            System.out.println("Version: " + hasCaps.getCapabilities().getVersion());
            System.out.println("Platform: " + hasCaps.getCapabilities().getPlatform());
        }
        else {
            System.out.println("WebDriver does not implement HasCapabilities --> can´t show version etc");
        }
    }
}
