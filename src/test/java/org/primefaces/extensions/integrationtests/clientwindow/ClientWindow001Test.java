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
package org.primefaces.extensions.integrationtests.clientwindow;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.SessionStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeExpectedConditions;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputText;
import org.primefaces.extensions.selenium.component.Messages;

public class ClientWindow001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("ClientWindow: Basic check - compare URL-Param, SessionStorage and Postback")
    public void testBasic(Page page) {
        // Arrange

        // Act
        page.button.click();

        // Assert
        String url = page.getWebDriver().getCurrentUrl();
        Assertions.assertTrue(url.contains("jfwid="));

        String pfWindowId = page.getWebStorage().getSessionStorage().getItem("pf.windowId");
        Assertions.assertNotNull(pfWindowId);
        Assertions.assertTrue(pfWindowId.length()>0);
        Assertions.assertTrue(url.endsWith(pfWindowId));

        Assertions.assertEquals("jfwid", page.messages.getMessage(0).getSummary());
        Assertions.assertEquals(pfWindowId, page.messages.getMessage(0).getDetail());
    }

    @Test
    @Order(2)
    @DisplayName("ClientWindow: check rendered URLs")
    public void testRenderedUrls(Page page) {
        // Arrange

        // Act

        // Assert
        String pfWindowId = page.getWebStorage().getSessionStorage().getItem("pf.windowId");
        WebElement eltLink2Anotherpage = page.getWebDriver().findElement(By.className("link2Anotherpage"));
        String href = eltLink2Anotherpage.getAttribute("href");
        Assertions.assertTrue(href.endsWith(pfWindowId));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:inputtext")
        InputText inputtext;

        @FindBy(id = "form:button")
        CommandButton button;

        @FindBy(id = "form:msgs")
        Messages messages;

        @Override
        public String getLocation() {
            return "clientwindow/clientWindow001.xhtml";
        }
    }
}
