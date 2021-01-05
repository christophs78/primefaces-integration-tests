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
package org.primefaces.extensions.integrationtests.rating;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.Rating;

public class Rating001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Rating: set value and cancel value using AJAX")
    public void testAjax(Page page) {
        // Arrange
        Rating rating = page.ratingAjax;
        Messages messages = page.messages;
        Assertions.assertNull(rating.getValue());

        // Act - add value
        PrimeSelenium.guardAjax(rating).setValue(4);

        // Assert - rate-event
        Assertions.assertEquals(4L, rating.getValue());
        Assertions.assertEquals("Rate Event", messages.getMessage(0).getSummary());
        Assertions.assertEquals("You rated:4", messages.getMessage(0).getDetail());

        // Act - cancel value
        rating.cancel();

        // Assert
        Assertions.assertNull(rating.getValue());
        Assertions.assertEquals("Cancel Event", messages.getMessage(0).getSummary());
        Assertions.assertEquals("Rate Reset", messages.getMessage(0).getDetail());
        assertConfiguration(rating.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Rating: widget reset() method")
    public void testAjaxReset(Page page) {
        // Arrange
        Rating rating = page.ratingAjax;
        Messages messages = page.messages;
        Assertions.assertNull(rating.getValue());

        // Act - add value
        rating.setValue(4);

        // Assert - rate-event
        Assertions.assertEquals(4L, rating.getValue());
        Assertions.assertEquals("Rate Event", messages.getMessage(0).getSummary());
        Assertions.assertEquals("You rated:4", messages.getMessage(0).getDetail());

        // Act - cancel value
        rating.reset();

        // Assert
        Assertions.assertNull(rating.getValue());
        Assertions.assertEquals("Cancel Event", messages.getMessage(0).getSummary());
        Assertions.assertEquals("Rate Reset", messages.getMessage(0).getDetail());
        assertConfiguration(rating.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("Rating: read only")
    public void testReadonly(Page page) {
        // Arrange
        Rating rating = page.ratingReadOnly;
        Assertions.assertEquals(3L, rating.getValue());
        Assertions.assertTrue(rating.isReadOnly());

        // Act
        rating.setValue(5);

        // Assert
        Assertions.assertEquals(3L, rating.getValue());
        JSONObject cfg = assertConfiguration(rating.getWidgetConfiguration());
        Assertions.assertTrue(cfg.getBoolean("readonly"));
    }

    @Test
    @Order(4)
    @DisplayName("Rating: disabled")
    public void testDisabled(Page page) {
        // Arrange
        Rating rating = page.ratingDisabled;
        Assertions.assertEquals(3L, rating.getValue());
        Assertions.assertTrue(rating.isDisabled());

        // Act
        rating.setValue(5);

        // Assert
        Assertions.assertEquals(3L, rating.getValue());
        JSONObject cfg = assertConfiguration(rating.getWidgetConfiguration());
        Assertions.assertTrue(cfg.getBoolean("disabled"));
    }

    @Test
    @Order(5)
    @DisplayName("Rating: enable and disable")
    public void testEnableAndDisable(Page page) {
        // Arrange
        Rating rating = page.ratingDisabled;
        Assertions.assertEquals(3L, rating.getValue());
        Assertions.assertTrue(rating.isDisabled());

        // Act - enable
        rating.enable();
        rating.setValue(5);

        // Assert
        Assertions.assertTrue(rating.isEnabled());
        Assertions.assertEquals(5L, rating.getValue());

        // Act - disable
        rating.disable();
        rating.setValue(1);

        // Assert
        Assertions.assertTrue(rating.isDisabled());
        Assertions.assertEquals(5L, rating.getValue());
        assertConfiguration(rating.getWidgetConfiguration());
    }

    @Test
    @Order(6)
    @DisplayName("Rating: set value to a string should default it to no stars")
    public void testInvalidNumberClientSide(Page page) {
        // Arrange
        Rating rating = page.ratingMinMax;
        Assertions.assertEquals(2L, rating.getValue());

        // Act
        PrimeSelenium.executeScript(rating.getWidgetByIdScript() + ".setValue('abc');");

        // Assert
        Assertions.assertNull(rating.getValue());
        assertConfiguration(rating.getWidgetConfiguration());
    }

    @Test
    @Order(7)
    @DisplayName("Rating: set value below minimum should set to no stars")
    public void testMinimumClientSide(Page page) {
        // Arrange
        Rating rating = page.ratingMinMax;
        Assertions.assertEquals(2L, rating.getValue());

        // Act
        rating.setValue(-1);

        // Assert
        Assertions.assertNull(rating.getValue());
        assertConfiguration(rating.getWidgetConfiguration());
    }

    @Test
    @Order(8)
    @DisplayName("Rating: set value above maximum should set to max stars")
    public void testMaximumClientSide(Page page) {
        // Arrange
        Rating rating = page.ratingMinMax;
        Assertions.assertEquals(2L, rating.getValue());

        // Act
        rating.setValue(14);

        // Assert
        Assertions.assertEquals(8L, rating.getValue());
        assertConfiguration(rating.getWidgetConfiguration());
    }

    @Test
    @Order(9)
    @DisplayName("Rating: Submit value below minimum should return original value")
    public void testMinimumServerSide(Page page) {
        // Arrange
        Rating rating = page.ratingMinMax;
        Assertions.assertEquals(2L, rating.getValue());

        // Act
        PrimeSelenium.setHiddenInput(rating.getInput(), "-1");
        Assertions.assertEquals("-1", rating.getInput().getAttribute("value"));
        page.submit.click();

        // Assert
        Assertions.assertEquals(2L, rating.getValue());
        assertConfiguration(rating.getWidgetConfiguration());
    }

    @Test
    @Order(10)
    @DisplayName("Rating: Submit value above maximum should return original value")
    public void testMaximumServerSide(Page page) {
        // Arrange
        Rating rating = page.ratingMinMax;
        Assertions.assertEquals(2L, rating.getValue());

        // Act
        PrimeSelenium.setHiddenInput(rating.getInput(), "14");
        Assertions.assertEquals("14", rating.getInput().getAttribute("value"));
        page.submit.click();

        // Assert
        Assertions.assertEquals(2L, rating.getValue());
        assertConfiguration(rating.getWidgetConfiguration());
    }

    @Test
    @Order(11)
    @DisplayName("Rating: set value to a string should return original value")
    public void testInvalidNumberServerSide(Page page) {
        // Arrange
        Rating rating = page.ratingMinMax;
        Assertions.assertEquals(2L, rating.getValue());

        // Act
        PrimeSelenium.setHiddenInput(rating.getInput(), "def");
        Assertions.assertEquals("def", rating.getInput().getAttribute("value"));
        page.submit.click();

        // Assert
        Assertions.assertEquals(2L, rating.getValue());
        assertConfiguration(rating.getWidgetConfiguration());
    }

    private JSONObject assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Rating Config = " + cfg);
        Assertions.assertTrue(cfg.has("id"));
        return cfg;
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:ajax")
        Rating ratingAjax;

        @FindBy(id = "form:readonly")
        Rating ratingReadOnly;

        @FindBy(id = "form:disabled")
        Rating ratingDisabled;

        @FindBy(id = "form:minmax")
        Rating ratingMinMax;

        @FindBy(id = "form:button")
        CommandButton submit;

        @Override
        public String getLocation() {
            return "rating/rating001.xhtml";
        }
    }
}
