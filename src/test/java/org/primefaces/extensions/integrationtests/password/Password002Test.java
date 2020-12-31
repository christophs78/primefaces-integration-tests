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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.Password;

public class Password002Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Password: Show feedback panel with widget method")
    public void testFeedbackShow(Page page) {
        // Arrange
        Password password = page.password;

        // Act
        password.showFeedback();

        // Assert
        WebElement feedback = password.getFeedbackPanel();
        Assertions.assertEquals("block", feedback.getCssValue("display"));
        Assertions.assertEquals("Please enter a password", feedback.getText().trim());
        assertConfiguration(password.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Password: Hide feedback panel with widget method")
    public void testFeedbackHide(Page page) {
        // Arrange
        Password password = page.password;
        password.showFeedback();

        // Act
        password.hideFeedback();

        // Assert
        WebElement feedback = password.getFeedbackPanel();
        Assertions.assertEquals("none", feedback.getCssValue("display"));
        assertConfiguration(password.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("Password: Weak password")
    public void testFeedbackWeak(Page page) {
        // Arrange
        Password password = page.password;

        // Act
        password.setValue("SOweak");
        password.showFeedback();

        // Assert
        WebElement feedback = password.getFeedbackPanel();
        Assertions.assertEquals("Weak", feedback.getText().trim());
        assertConfiguration(password.getWidgetConfiguration());
    }

    @Test
    @Order(4)
    @DisplayName("Password: Good password")
    public void testFeedbackGood(Page page) {
        // Arrange
        Password password = page.password;

        // Act
        password.setValue("PrettyGood");
        password.showFeedback();

        // Assert
        WebElement feedback = password.getFeedbackPanel();
        Assertions.assertEquals("Good", feedback.getText().trim());
        assertConfiguration(password.getWidgetConfiguration());
    }

    @Test
    @Order(5)
    @DisplayName("Password: Strong password")
    public void testFeedbackStrong(Page page) {
        // Arrange
        Password password = page.password;

        // Act
        password.setValue("Very#Strong12345");
        password.showFeedback();

        // Assert
        WebElement feedback = password.getFeedbackPanel();
        Assertions.assertEquals("Strong", feedback.getText().trim());
        assertConfiguration(password.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Password Config = " + cfg);
        Assertions.assertTrue(cfg.getBoolean("feedback"));
        Assertions.assertFalse(cfg.getBoolean("inline"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:password")
        Password password;

        @Override
        public String getLocation() {
            return "password/password002.xhtml";
        }
    }
}
