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

import org.json.JSONArray;
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
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.Timeline;
import org.primefaces.extensions.selenium.component.model.Msg;

public class Timeline002Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Timeline: GitHub #6721 B.C. Dates show and check for JS-errors")
    public void testBCDates_6721(Page page) {
        // Arrange
        Timeline timeline = page.timeline;

        // Act
        timeline.select("vis-item-content");
        PrimeSelenium.waitGui().until(PrimeExpectedConditions.visibleAndAnimationComplete(page.messages));

        // Assert
        Msg message = page.messages.getMessage(0);
        Assertions.assertEquals("Selected event:", message.getSummary());
        Assertions.assertEquals("164 B.C.", message.getDetail());
        assertConfiguration(timeline.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Timeline Config = " + cfg);
        Assertions.assertTrue(cfg.has("data"));
        JSONArray data = cfg.getJSONArray("data");
        JSONObject event = data.getJSONObject(0);
        Assertions.assertEquals("-000164-06", event.getString("start").substring(0, 10));
        Assertions.assertEquals("164 B.C.", event.get("content"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:timeline")
        Timeline timeline;

        @Override
        public String getLocation() {
            return "timeline/timeline002.xhtml";
        }
    }
}