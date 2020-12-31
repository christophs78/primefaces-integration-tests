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
package org.primefaces.extensions.integrationtests.password;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.Password;
import org.primefaces.extensions.selenium.component.model.Msg;

public class Password004Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Password: Passwords are required for matching")
    public void testRequired(Page page) {
        // Arrange
        Password pwd1 = page.pwd1;
        Password pwd2 = page.pwd2;
        Assertions.assertEquals("", pwd1.getValue());
        Assertions.assertEquals("", pwd2.getValue());

        // Act
        page.button.click();

        // Assert
        Msg message1 = page.messages.getMessage(0);
        Assertions.assertNotNull(message1);
        Assertions.assertEquals("Password 1: Validation Error: Value is required.", message1.getSummary());
        Msg message2 = page.messages.getMessage(1);
        Assertions.assertNotNull(message2);
        Assertions.assertEquals("Password 2: Validation Error: Value is required.", message2.getSummary());
        Assertions.assertEquals("", pwd1.getValue());
        Assertions.assertEquals("", pwd2.getValue());
        assertConfiguration(pwd1.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Password: Passwords do not match and should be rejected")
    public void testDoNotMatch(Page page) {
        // Arrange
        Password pwd1 = page.pwd1;
        Password pwd2 = page.pwd2;
        Assertions.assertEquals("", pwd1.getValue());
        Assertions.assertEquals("", pwd2.getValue());

        // Act
        pwd1.setValue("apple");
        pwd2.setValue("orange");
        page.button.click();

        // Assert
        Msg message1 = page.messages.getMessage(0);
        Assertions.assertNotNull(message1);
        Assertions.assertEquals("Password 1: Validation Error.", message1.getSummary());
        Assertions.assertEquals("Password 1 should match with Password 2.", message1.getDetail());
        Assertions.assertEquals("", pwd1.getValue());
        Assertions.assertEquals("", pwd2.getValue());
        assertConfiguration(pwd1.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("Password: Passwords match and should be accepted")
    public void testMatch(Page page) {
        // Arrange
        Password pwd1 = page.pwd1;
        Password pwd2 = page.pwd2;
        Assertions.assertEquals("", pwd1.getValue());
        Assertions.assertEquals("", pwd2.getValue());

        // Act
        pwd1.setValue("banana");
        pwd2.setValue("banana");
        page.button.click();

        // Assert
        Msg message1 = page.messages.getMessage(0);
        Assertions.assertNull(message1);
        Assertions.assertEquals("", pwd1.getValue());
        Assertions.assertEquals("", pwd2.getValue());
        assertConfiguration(pwd1.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Password Config = " + cfg);
        Assertions.assertTrue(cfg.has("widgetVar"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:pwd1")
        Password pwd1;

        @FindBy(id = "form:pwd2")
        Password pwd2;

        @FindBy(id = "form:messages")
        Messages messages;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "password/password004.xhtml";
        }
    }
}
