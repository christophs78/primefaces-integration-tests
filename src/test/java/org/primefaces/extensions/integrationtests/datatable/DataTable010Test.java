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
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.Messages;

public class DataTable010Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: selection - multiple with paging")
    public void testSelectionMultipleWithPaging(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);
        Actions actions = new Actions(page.getWebDriver());

        // Act
        dataTable.getCell(0, 0).getWebElement().click();
        page.buttonSubmit.click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Selected ProgrammingLanguage(s)"));
        Assertions.assertEquals("1", page.messages.getMessage(0).getDetail());

        // Act
        dataTable.selectPage(2);
        actions.keyDown(Keys.META).click(dataTable.getCell(1, 0).getWebElement()).keyUp(Keys.META).perform();
        page.buttonSubmit.click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Selected ProgrammingLanguage(s)"));
        Assertions.assertEquals("1,5", page.messages.getMessage(0).getDetail());

        // Act
        dataTable.selectPage(1);
        actions.keyDown(Keys.META).click(dataTable.getCell(1, 0).getWebElement()).keyUp(Keys.META).perform();
        page.buttonSubmit.click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Selected ProgrammingLanguage(s)"));
        Assertions.assertEquals("1,2,5", page.messages.getMessage(0).getDetail());
        assertConfiguration(page.dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("DataTable: selection - multiple with paging and filtering")
    public void testSelectionMultipleWithPagingAndFiltering(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);
        Actions actions = new Actions(page.getWebDriver());

        // Act
        dataTable.getCell(0, 0).getWebElement().click();
        page.buttonSubmit.click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Selected ProgrammingLanguage(s)"));
        Assertions.assertEquals("1", page.messages.getMessage(0).getDetail());

        // Act
        dataTable.selectPage(2);
        actions.keyDown(Keys.META).click(dataTable.getCell(1, 0).getWebElement()).keyUp(Keys.META).perform();
        page.buttonSubmit.click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Selected ProgrammingLanguage(s)"));
        Assertions.assertEquals("1,5", page.messages.getMessage(0).getDetail());

        // Act
        dataTable.filter(1, "Java");
        page.buttonSubmit.click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Selected ProgrammingLanguage(s)"));
        Assertions.assertEquals("1,5", page.messages.getMessage(0).getDetail());

        // Act
        actions.keyDown(Keys.META).click(dataTable.getCell(1, 0).getWebElement()).keyUp(Keys.META).perform();
        page.buttonSubmit.click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Selected ProgrammingLanguage(s)"));
        Assertions.assertEquals("1,3,5", page.messages.getMessage(0).getDetail());
        assertConfiguration(page.dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("DataTable: selection - multiple with paging, filtering and delete row")
    public void testSelectionMultipleWithPagingAndFilteringAndDelete(Page page) {
        testSelectionMultipleWithPagingAndFiltering(page);

        // Act
        page.buttonDelete.click();

        // Assert
        /*
            https://code.google.com/archive/p/primefaces/issues/7132 still seems to be there as of 09/2020
         */
        Assertions.assertEquals("Java", page.dataTable.getCell(0, 1).getText());
        Assertions.assertEquals("JavaScript", page.dataTable.getCell(1, 1).getText());

        // Act
        page.buttonSubmit.click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Selected ProgrammingLanguage(s)"));
        Assertions.assertEquals("3,5", page.messages.getMessage(0).getDetail());
        assertConfiguration(page.dataTable.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("selectionMode"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @FindBy(id = "form:buttonSubmit")
        CommandButton buttonSubmit;

        @FindBy(id = "form:buttonDelete")
        CommandButton buttonDelete;

        @Override
        public String getLocation() {
            return "datatable/dataTable010.xhtml";
        }
    }
}
