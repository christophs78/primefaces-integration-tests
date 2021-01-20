/*
 * Copyright (c) 2011-2021 PrimeFaces Extensions
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.primefaces.extensions.integrationtests.timeline;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.integrationtests.general.utilities.TestUtils;
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
        TestUtils.wait(2000); // let timeline load

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