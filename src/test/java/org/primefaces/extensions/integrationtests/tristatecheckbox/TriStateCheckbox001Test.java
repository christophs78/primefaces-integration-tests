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
package org.primefaces.extensions.integrationtests.tristatecheckbox;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.TriStateCheckbox;

public class TriStateCheckbox001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("TriStateCheckbox: click through all states")
    public void testClickThrough(Page page) {
        // Arrange / Assert
        TriStateCheckbox triStateCheckbox = page.triStateCheckbox;
        Assertions.assertEquals("0", triStateCheckbox.getValue());

        // Act
        triStateCheckbox.click();

        // Assert
        Assertions.assertEquals("1", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());

        // Act
        triStateCheckbox.click();

        // Assert
        Assertions.assertEquals("2", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());

        // Act
        page.button.click();

        // Assert
        Assertions.assertEquals("2", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());
    }

    @Test
    @Order(1)
    @DisplayName("TriStateCheckbox: widget toggle through all states")
    public void testWidgetToggle(Page page) {
        // Arrange / Assert
        TriStateCheckbox triStateCheckbox = page.triStateCheckbox;
        Assertions.assertEquals("0", triStateCheckbox.getValue());

        // Act
        triStateCheckbox.toggle();

        // Assert
        Assertions.assertEquals("1", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());

        // Act
        triStateCheckbox.toggle();

        // Assert
        Assertions.assertEquals("2", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());

        // Act
        page.button.click();

        // Assert
        Assertions.assertEquals("2", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("TriStateCheckbox: setValue")
    public void testSetValue(Page page) {
        // Arrange
        TriStateCheckbox triStateCheckbox = page.triStateCheckbox;
        triStateCheckbox.setValue("1");
        Assertions.assertEquals("1", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());

        // Act
        page.button.click();

        // Assert
        Assertions.assertEquals("1", triStateCheckbox.getValue());
        assertConfiguration(triStateCheckbox.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("TriStateCheckbox Config = " + cfg);
        Assertions.assertTrue(cfg.has("id"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:triStateCheckbox")
        TriStateCheckbox triStateCheckbox;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "tristatecheckbox/triStateCheckbox001.xhtml";
        }
    }
}
