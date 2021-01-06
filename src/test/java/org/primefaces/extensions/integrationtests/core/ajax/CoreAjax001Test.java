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
package org.primefaces.extensions.integrationtests.core.ajax;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputText;
import org.primefaces.extensions.selenium.component.SelectOneMenu;

public class CoreAjax001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Core-AJAX: keep vertical scroll-position after ajax-update - https://github.com/primefaces/primefaces/issues/6700")
    public void testAjaxScrollPosition(Page page) {
        // Arrange
        Long scrollTop = PrimeSelenium.executeScript("return $(document).scrollTop();");
        Assertions.assertEquals(0, scrollTop);
        PrimeSelenium.executeScript("$(document).scrollTop(200);");

        // Act
        page.selectOneMenu.select(2);

        // Assert
        scrollTop = PrimeSelenium.executeScript("return $(document).scrollTop();");
        Assertions.assertEquals(200, scrollTop);
        assertNoJavascriptErrors();

        // Act
        page.button1.click();

        // Assert
        scrollTop = PrimeSelenium.executeScript("return $(document).scrollTop();");
        Assertions.assertEquals(200, scrollTop);
        assertNoJavascriptErrors();

        // Act
        page.button2.click(); //does PrimeFaces.current().focus("form:inputtext"); --> vertical scroll position changes

        // Assert
        try {
            Thread.sleep(300); //PrimeFaces.focus() has a 50ms setTimeout() so we need to delay here
        }
        catch (Exception ex) {
        }
        Assertions.assertEquals(page.inputtext.getInput().getAttribute("id"), page.getWebDriver().switchTo().activeElement().getAttribute("id"));
        scrollTop = PrimeSelenium.executeScript("return $(document).scrollTop();");
        Assertions.assertTrue(scrollTop > 200);
        assertNoJavascriptErrors();
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:inputtext")
        InputText inputtext;

        @FindBy(id = "form:selectonemenu")
        SelectOneMenu selectOneMenu;

        @FindBy(id = "form:button1")
        CommandButton button1;

        @FindBy(id = "form:button2")
        CommandButton button2;

        @Override
        public String getLocation() {
            return "core/ajax/coreAjax001.xhtml";
        }
    }
}
