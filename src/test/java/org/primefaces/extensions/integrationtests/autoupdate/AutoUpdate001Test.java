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
package org.primefaces.extensions.integrationtests.autoupdate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;

public class AutoUpdate001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("AutoUpdate: Globally update all AutoUpdate components")
    public void testGlobal(Page page) {
        // Arrange
        assertInitialValues(page);

        // Act
        page.buttonGlobal.click();

        // Assert
        assertNoJavascriptErrors();
        Assertions.assertEquals("global", page.displayGlobal.getText());
        Assertions.assertEquals("", page.displayEvent1.getText());
        Assertions.assertEquals("", page.displayEvent2.getText());
    }

    @Test
    @Order(2)
    @DisplayName("AutoUpdate: Pub/Sub only update the events subscribed")
    public void testPubSubEvent1(Page page) {
        // Arrange
        assertInitialValues(page);

        // Act
        page.buttonEvent1.click();

        // Assert
        assertNoJavascriptErrors();
        Assertions.assertEquals("one", page.displayGlobal.getText());
        Assertions.assertEquals("one", page.displayEvent1.getText());
        Assertions.assertEquals("", page.displayEvent2.getText());
    }

    @Test
    @Order(3)
    @DisplayName("AutoUpdate: Pub/Sub only update the events subscribed")
    public void testPubSubEvent2(Page page) {
        // Arrange
        assertInitialValues(page);

        // Act
        page.buttonEvent2.click();

        // Assert
        assertNoJavascriptErrors();
        Assertions.assertEquals("two", page.displayGlobal.getText());
        Assertions.assertEquals("", page.displayEvent1.getText());
        Assertions.assertEquals("two", page.displayEvent2.getText());
    }

    private void assertInitialValues(Page page) {
        Assertions.assertEquals("", page.displayGlobal.getText());
        Assertions.assertEquals("", page.displayEvent1.getText());
        Assertions.assertEquals("", page.displayEvent2.getText());
    }

    public static class Page extends AbstractPrimePage {

        @FindBy(id = "form:displayGlobal")
        WebElement displayGlobal;

        @FindBy(id = "form:displayEvent1")
        WebElement displayEvent1;

        @FindBy(id = "form:displayEvent2")
        WebElement displayEvent2;

        @FindBy(id = "form:btnGlobal")
        CommandButton buttonGlobal;

        @FindBy(id = "form:btnEvent1")
        CommandButton buttonEvent1;

        @FindBy(id = "form:btnEvent2")
        CommandButton buttonEvent2;

        @Override
        public String getLocation() {
            return "autoupdate/autoUpdate001.xhtml";
        }
    }
}
