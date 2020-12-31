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
package org.primefaces.extensions.integrationtests.schedule;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.Schedule;
import org.primefaces.extensions.selenium.component.model.Msg;

public class Schedule001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Schedule: show and check for JS-errors")
    public void testBasic(Page page) {
        // Arrange
        Schedule schedule = page.schedule;

        // Act
        schedule.update(); // widget method

        // Assert
        assertConfiguration(schedule.getWidgetConfiguration(), "en");
    }

    @Test
    @Order(2)
    @DisplayName("Schedule: dateSelect")
    public void testDateSelect(Page page) {
        // Arrange
        Schedule schedule = page.schedule;

        // Act
        schedule.select("fc-daygrid-day-top");

        // Assert
        assertMessage(page, "Date selected");
        assertConfiguration(schedule.getWidgetConfiguration(), "en");
    }

    @Test
    @Order(3)
    @DisplayName("Schedule: eventSelect")
    public void testEventSelect(Page page) {
        // Arrange
        Schedule schedule = page.schedule;

        // Act
        schedule.select("fc-daygrid-event");

        // Assert
        assertMessage(page, "Event selected");
        assertConfiguration(schedule.getWidgetConfiguration(), "en");
    }

    @Test
    @Order(4)
    @DisplayName("Schedule: GitHub #6496 locale switching")
    public void testLocales(Page page) {
        // Arrange
        Schedule schedule = page.schedule;

        // Act
        page.buttonEnglish.click();

        // Assert
        assertButton(schedule.getTodayButton(), "Current Date");
        assertButton(schedule.getMonthButton(), "Month");
        assertButton(schedule.getWeekButton(), "Week");
        assertButton(schedule.getDayButton(), "Day");

        // Act
        page.buttonFrench.click();

        // Assert
        assertButton(schedule.getTodayButton(), "Aujourd'hui");
        assertButton(schedule.getMonthButton(), "Mois");
        assertButton(schedule.getWeekButton(), "Semaine");
        assertButton(schedule.getDayButton(), "Jour");
        assertConfiguration(schedule.getWidgetConfiguration(), "fr");
    }

    private void assertButton(WebElement button, String text) {
        Assertions.assertEquals(text, button.getText());
    }

    private void assertMessage(Page page, String message) {
        Msg msg = page.messages.getMessage(0);
        Assertions.assertNotNull(msg, "No messages found!");
        System.out.println("Message = " + msg);
        Assertions.assertTrue(msg.getSummary().contains(message));
    }

    private void assertConfiguration(JSONObject cfg, String locale) {
        assertNoJavascriptErrors();
        Assertions.assertEquals("form", cfg.getString("formId"));
        Assertions.assertEquals(locale, cfg.getString("locale"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:schedule")
        Schedule schedule;

        @FindBy(id = "form:btnEnglish")
        CommandButton buttonEnglish;

        @FindBy(id = "form:btnFrench")
        CommandButton buttonFrench;

        @Override
        public String getLocation() {
            return "schedule/schedule001.xhtml";
        }
    }
}
