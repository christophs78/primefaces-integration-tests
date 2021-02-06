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

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.InputText;

public class DataTable019Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: Global Filter Function finds results by name")
    public void testGlobalFilterFunctionByName(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        InputText globalFilter = page.globalFilter;
        dataTable.selectPage(1);
        dataTable.sort("Name");

        // Act
        globalFilter.setValue("Java");

        // Assert
        List<ProgrammingLanguage> langsFiltered = langs.stream()
                    .sorted(Comparator.comparing(ProgrammingLanguage::getName))
                    .filter(l -> l.getName().startsWith("Java"))
                    .limit(3)
                    .collect(Collectors.toList());
        Assertions.assertEquals(2, langsFiltered.size());
        assertRows(dataTable, langsFiltered);
        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("DataTable: Global Filter Function finds results by type")
    public void testGlobalFilterFunctionByType(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        InputText globalFilter = page.globalFilter;
        dataTable.selectPage(1);
        dataTable.sort("Type");

        // Act
        globalFilter.setValue("COMPILED");

        // Assert
        List<ProgrammingLanguage> langsFiltered = langs.stream()
                    .sorted(Comparator.comparing(ProgrammingLanguage::getType))
                    .filter(l -> l.getType().name().startsWith("COMPILED"))
                    .limit(3)
                    .collect(Collectors.toList());
        Assertions.assertEquals(2, langsFiltered.size());
        assertRows(dataTable, langsFiltered);
        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("DataTable: Global Filter Function finds NO results")
    public void testGlobalFilterFunctionNoResults(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        InputText globalFilter = page.globalFilter;
        dataTable.selectPage(1);
        dataTable.sort("Name");

        // Act
        globalFilter.setValue("Clojure");

        // Assert
        List<ProgrammingLanguage> langsFiltered = langs.stream()
                    .sorted(Comparator.comparing(ProgrammingLanguage::getName))
                    .filter(l -> l.getName().startsWith("Clojure"))
                    .limit(3)
                    .collect(Collectors.toList());
        Assertions.assertEquals(0, langsFiltered.size());
        assertRows(dataTable, langsFiltered);
        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("DataTable: Global Filter Function clearing field resets all rows")
    public void testGlobalFilterFunctionClear(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        InputText globalFilter = page.globalFilter;
        dataTable.selectPage(1);
        dataTable.sort("Name");

        // Act
        globalFilter.setValue("Java");
        globalFilter.setValue("");

        // Assert
        Assertions.assertEquals(5, langs.size());
        assertConfiguration(dataTable.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("paginator"));
        Assertions.assertEquals("wgtTable", cfg.getString("widgetVar"));
        Assertions.assertEquals(0, cfg.getInt("tabindex"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @FindBy(id = "form:datatable:globalFilter")
        InputText globalFilter;

        @Override
        public String getLocation() {
            return "datatable/dataTable019.xhtml";
        }
    }
}
