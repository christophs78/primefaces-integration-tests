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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeExpectedConditions;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DataTable;
import org.primefaces.extensions.selenium.component.base.ComponentUtils;
import org.primefaces.extensions.selenium.component.model.data.Paginator;
import org.primefaces.extensions.selenium.component.model.datatable.Header;
import org.primefaces.extensions.selenium.component.model.datatable.Row;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DataTable003Test extends AbstractDataTableTest {

    private final List<ProgrammingLanguage> langs = new ProgrammingLanguageService().getLangs();

    @Test
    @Order(1)
    @DisplayName("DataTable: multisort - firstAppeared desc, name asc")
    public void testMultiSort(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        Actions actions = new Actions(page.getWebDriver());
        WebElement eltSortFirstAppeared = dataTable.getHeader().getCell(2).getWebElement();
        WebElement eltSortName = dataTable.getHeader().getCell(1).getWebElement();
        // 1) sort by firstAppeared desc
        actions.click(eltSortFirstAppeared).click(eltSortFirstAppeared).perform();
        PrimeSelenium.waitGui().until(PrimeExpectedConditions.jQueryNotActive());
        // 2) additional sort by name asc
        actions.keyDown(Keys.CONTROL).click(eltSortName).keyUp(Keys.CONTROL).perform();
        PrimeSelenium.waitGui().until(PrimeExpectedConditions.jQueryNotActive());

        // Assert
        Assertions.assertTrue(PrimeSelenium.hasCssClass(eltSortFirstAppeared.findElement(By.className("ui-sortable-column-icon")), "ui-icon-triangle-1-s"));
        Assertions.assertTrue(PrimeSelenium.hasCssClass(eltSortName.findElement(By.className("ui-sortable-column-icon")), "ui-icon-triangle-1-n"));

        List<ProgrammingLanguage> langsSorted = langs.stream().sorted(new ProgrammingLanguageSorterFirstAppearedDescNameAsc()).collect(Collectors.toList());
        assertRows(dataTable, langsSorted);
    }

    @Test
    @Order(2)
    @DisplayName("DataTable: multisort - firstAppeared desc, name desc")
    public void testMultiSort2(Page page) {
        // Arrange
        DataTable dataTable = page.dataTable;
        Assertions.assertNotNull(dataTable);

        // Act
        Actions actions = new Actions(page.getWebDriver());
        WebElement eltSortFirstAppeared = dataTable.getHeader().getCell(2).getWebElement();
        WebElement eltSortName = dataTable.getHeader().getCell(1).getWebElement();
        // 1) sort by firstAppeared desc
        actions.click(eltSortFirstAppeared).click(eltSortFirstAppeared).perform();
        PrimeSelenium.waitGui().until(PrimeExpectedConditions.jQueryNotActive());
        // 2) additional sort by name asc
        actions.keyDown(Keys.CONTROL).click(eltSortName).click(eltSortName).keyUp(Keys.CONTROL).perform();
        PrimeSelenium.waitGui().until(PrimeExpectedConditions.jQueryNotActive());

        // Assert
        Assertions.assertTrue(PrimeSelenium.hasCssClass(eltSortFirstAppeared.findElement(By.className("ui-sortable-column-icon")), "ui-icon-triangle-1-s"));
        Assertions.assertTrue(PrimeSelenium.hasCssClass(eltSortName.findElement(By.className("ui-sortable-column-icon")), "ui-icon-triangle-1-s"));

        List<ProgrammingLanguage> langsSorted = langs.stream().sorted(new ProgrammingLanguageSorterFirstAppearedDescNameDesc()).collect(Collectors.toList());
        assertRows(dataTable, langsSorted);
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DataTable Config = " + cfg);
        Assertions.assertTrue(cfg.has("sortMode"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datatable")
        DataTable dataTable;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "datatable/dataTable003.xhtml";
        }
    }
}
