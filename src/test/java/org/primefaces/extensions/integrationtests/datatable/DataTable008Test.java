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
import java.util.stream.Collectors;

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

public class DataTable008Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: filter - issue 5481 - https://github.com/primefaces/primefaces/issues/5481")
    public void testFilterIssue_5481(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act - do some filtering
        dataTable.selectPage(1);
        dataTable.sort("Name");
        dataTable.getHeader().getCell("First appeared").get().setFilterValue("2010", true);

        // Assert
        Assertions.assertEquals("2010", getFirstAppearedFilterElt(dataTable).getAttribute("value"));
        List<ProgrammingLanguage> langsFiltered = langs.stream()
                    .sorted((l1, l2) -> l1.getName().compareTo(l2.getName()))
                    .filter(l -> l.getFirstAppeared() >= 2010)
                    .limit(3)
                    .collect(Collectors.toList());
        assertRows(dataTable, langsFiltered);

        assertConfiguration(dataTable.getWidgetConfiguration());

        // Act - reload page and go to page 2 (filter is visually removed but to some degree due to SessionScoped-bean still there)
        page.getWebDriver().navigate().refresh();
        dataTable.selectPage(2);

        // Assert
        Assertions.assertEquals("", getFirstAppearedFilterElt(dataTable).getAttribute("value"));
        List<ProgrammingLanguage> langsUnfilteredPage2 = langs.stream()
                    .sorted((l1, l2) -> l1.getName().compareTo(l2.getName()))
                    .skip(3)
                    .limit(3)
                    .collect(Collectors.toList());
        assertRows(dataTable, langsUnfilteredPage2);

        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    private static WebElement getFirstAppearedFilterElt(DataTable dataTable) {
        return dataTable.getHeader().getCell(2).getWebElement().findElement(By.tagName("input"));
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("paginator"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @Override
        public String getLocation() {
            return "datatable/dataTable008.xhtml";
        }
    }
}
