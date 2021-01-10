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

import org.apache.openejb.jee.Web;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.primefaces.extensions.integrationtests.utilities.TestUtils;
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

public class DataTable014Test extends AbstractDataTableTest {

    @Test
    @Order(1)
    @DisplayName("DataTable: stickyHeader")
    public void testStickyHeader(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);
        int dataTableWidth = dataTable.getSize().getWidth();
        int dataTableLocationX = dataTable.getLocation().getX();

        WebElement dataTableSticky = dataTable.findElement(By.className("ui-datatable-sticky"));
        WebElement dataTableWrapper = dataTable.findElement(By.className("ui-datatable-tablewrapper"));
        Assertions.assertNotNull(dataTableSticky);
        Assertions.assertNotNull(dataTableWrapper);

        // Act
        JavascriptExecutor js = (JavascriptExecutor) getWebDriver();
        int scrollDown = 500;
        js.executeScript("window.scrollBy(0," + scrollDown + ")");
        TestUtils.wait(1000); //compensate weird Firefox (81) timing issue with assigning ui-sticky

        // Assert
        Assertions.assertTrue(PrimeSelenium.hasCssClass(dataTableSticky, "ui-sticky"));
        Assertions.assertEquals(dataTableWidth, dataTableSticky.getSize().getWidth());
        Assertions.assertEquals(dataTableWidth, dataTableWrapper.getSize().getWidth());
        Assertions.assertEquals(dataTableLocationX, dataTableSticky.getLocation().getX());
        Assertions.assertEquals(dataTableLocationX, dataTableWrapper.getLocation().getX());
        Assertions.assertEquals(scrollDown, dataTableSticky.getLocation().getY());
        Assertions.assertEquals(dataTable.getLocation().getY(), dataTableWrapper.getLocation().getY());

        // Act
        js.executeScript("window.scrollTo(0,0)");
        TestUtils.wait(1000); //compensate weird Firefox (81) timing issue with removing ui-sticky

        // Assert
        Assertions.assertFalse(PrimeSelenium.hasCssClass(dataTableSticky, "ui-sticky"));

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("stickyHeader"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @Override
        public String getLocation() {
            return "datatable/dataTable014.xhtml";
        }
    }
}
