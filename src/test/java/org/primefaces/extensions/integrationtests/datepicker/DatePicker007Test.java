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
package org.primefaces.extensions.integrationtests.datepicker;

import java.time.LocalTime;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DatePicker;

public class DatePicker007Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("DatePicker: GitHub #6636 TimeOnly at 12AM issue")
    public void testBasic(Page page) {
        // Arrange
        DatePicker datePicker = page.datePicker;
        Assertions.assertEquals(LocalTime.of(12, 4), datePicker.getValue().toLocalTime());

        // Act
        datePicker.click(); // focus to bring up panel

        // Assert Panel (12:04 AM)
        WebElement panel = datePicker.getPanel();
        Assertions.assertNotNull(panel);
        String text = panel.getText();
        Assertions.assertTrue(text.contains("12"));
        Assertions.assertTrue(text.contains("04"));
        Assertions.assertTrue(text.contains("PM")); //TODO: When #6636 is fixed this should be AM

        // Act (go down by 1 hour)
        WebElement hourPicker = panel.findElement(By.className("ui-hour-picker"));
        hourPicker.findElement(By.className("ui-picker-down")).click();

        // Assert (new time should be 11:04 PM)
        text = panel.getText();
        Assertions.assertTrue(text.contains("11"));
        Assertions.assertTrue(text.contains("04"));
        Assertions.assertTrue(text.contains("AM")); //TODO: When #6636 is fixed this should be PM
        assertConfiguration(datePicker.getWidgetConfiguration(), "12:04 AM");
    }

    private void assertConfiguration(JSONObject cfg, String defaultDate) {
        assertNoJavascriptErrors();
        System.out.println("DatePicker Config = " + cfg);
        Assertions.assertEquals(defaultDate, cfg.getString("defaultDate"));
        Assertions.assertEquals("single", cfg.getString("selectionMode"));
        Assertions.assertFalse(cfg.getBoolean("inline"));
        Assertions.assertTrue(cfg.getBoolean("timeOnly"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datepicker")
        DatePicker datePicker;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "datepicker/datePicker007.xhtml";
        }
    }
}
