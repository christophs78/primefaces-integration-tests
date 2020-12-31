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
import org.primefaces.extensions.selenium.component.Password;

public class Password001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Password: Value should not be redisplayed after submission")
    public void testRedisplayFalse(Page page) {
        // Arrange
        Password password = page.password;
        Assertions.assertEquals("", password.getValue());

        // Act
        password.setValue("encryptme!");
        page.button.click();

        // Assert
        Assertions.assertEquals("", password.getValue());
        assertConfiguration(password.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Password: Value should be redisplayed after submission")
    public void testRedisplayTrue(Page page) {
        // Arrange
        Password password = page.redisplay;
        Assertions.assertEquals("", password.getValue());

        // Act
        password.setValue("encryptme!");
        page.button.click();

        // Assert
        Assertions.assertEquals("encryptme!", password.getValue());
        assertConfiguration(password.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Password Config = " + cfg);
        Assertions.assertTrue(cfg.has("widgetVar"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:password")
        Password password;

        @FindBy(id = "form:redisplay")
        Password redisplay;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "password/password001.xhtml";
        }
    }
}
