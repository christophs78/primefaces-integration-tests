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
