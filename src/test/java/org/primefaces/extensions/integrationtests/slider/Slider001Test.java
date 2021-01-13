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
package org.primefaces.extensions.integrationtests.slider;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.Chips;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.Slider;

import java.util.List;

public class Slider001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Slider: int-value")
    public void testIntValue(Page page) {
        // Arrange
        Slider slider = page.sliderInt;

        // Assert initial state
        Assertions.assertEquals(5, slider.getValue().intValue());

        // Act - add value
        slider.setValue(8);

        // Act - submit
        page.button.click();

        // Assert
        //Assertions.assertEquals("Defect, Feature, Question", page.messages.getMessage(0).getSummary());
        Assertions.assertEquals(8, slider.getValue().intValue());

        assertConfiguration(slider.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Slider: float-value")
    public void testFloatValue(Page page) {
        // Arrange
        Slider slider = page.sliderfloat;

        // Assert initial state
        Assertions.assertEquals(3.14f, slider.getValue().floatValue());

        // Act - add value
        slider.setValue(9.9f);

        // Act - submit
        page.button.click();

        // Assert
        //Assertions.assertEquals("Defect, Feature, Question", page.messages.getMessage(0).getSummary());
        Assertions.assertEquals(9.9f, slider.getValue().floatValue());

        assertConfiguration(slider.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Slider Config = " + cfg);
        Assertions.assertTrue(cfg.has("id"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:sliderInt")
        Slider sliderInt;

        @FindBy(id = "form:sliderFloat")
        Slider sliderfloat;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "slider/slider001.xhtml";
        }
    }
}
