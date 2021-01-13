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
package org.primefaces.extensions.integrationtests.selectonemenu;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeExpectedConditions;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputText;
import org.primefaces.extensions.selenium.component.SelectOneMenu;

public class SelectOneMenu001Test extends AbstractPrimePageTest {

    @Test
    @DisplayName("SelectOneMenu: basic usecase")
    public void testBasic(Page page) {
        // Arrange
        SelectOneMenu selectOneMenu = page.selectOneMenu;
        Assertions.assertEquals("Lewis", selectOneMenu.getSelectedLabel());

        // Act
        selectOneMenu.select("Max");
        page.button.click();

        // Assert - part 1
        Assertions.assertEquals("Max", selectOneMenu.getSelectedLabel());
        assertConfiguration(selectOneMenu.getWidgetConfiguration());

        // Act
        selectOneMenu.select(3);
        page.button.click();

        // Assert - part 2
        Assertions.assertEquals("Charles", selectOneMenu.getSelectedLabel());
        assertConfiguration(selectOneMenu.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("SelectOneMenu Config = " + cfg);
        Assertions.assertTrue(cfg.has("appendTo"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:selectonemenu")
        SelectOneMenu selectOneMenu;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "selectonemenu/selectOneMenu001.xhtml";
        }
    }
}
