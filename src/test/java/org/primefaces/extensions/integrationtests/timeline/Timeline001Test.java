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
package org.primefaces.extensions.integrationtests.timeline;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeExpectedConditions;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.Timeline;

public class Timeline001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Timeline: show and check for JS-errors")
    public void testBasic(Page page) {
        // Arrange
        Timeline timeline = page.timeline;

        // Act
        timeline.update();

        // Assert
        long eventCount = timeline.getNumberOfEvents();
        Assertions.assertEquals(15, eventCount);
        assertConfiguration(page.timeline.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Timeline: select-event")
    public void testSelect(Page page) {
        // Arrange
        Timeline timeline = page.timeline;

        // Act
        timeline.select("vis-item-content");
        PrimeSelenium.waitGui().until(PrimeExpectedConditions.visibleAndAnimationComplete(page.messages));

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Selected event:"));
        assertConfiguration(page.timeline.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Timeline Config = " + cfg);
        Assertions.assertTrue(cfg.has("data"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:button")
        CommandButton button;

        @FindBy(id = "form:timeline")
        Timeline timeline;

        @Override
        public String getLocation() {
            return "timeline/timeline001.xhtml";
        }
    }
}