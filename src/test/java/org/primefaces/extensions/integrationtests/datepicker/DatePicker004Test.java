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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.integrationtests.utilities.TestUtils;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DatePicker;

public class DatePicker004Test extends AbstractPrimePageTest {

    @Test
    @DisplayName("DatePicker: date with time HH:mm:ss. See GitHub #6458 and #6459")
    public void testDateAndTimeWithSeconds(Page page) {
        // Arrange
        TestUtils.pause(1000);
        DatePicker datePicker = page.datePickerSeconds;
        Assertions.assertEquals(LocalDateTime.of(2020, 8, 20, 22, 20, 19), datePicker.getValue());
        LocalDateTime value = LocalDateTime.of(1978, 2, 19, 11, 55, 19);

        // Act
        datePicker.setValue(value);
        datePicker.showPanel(); // focus to bring up panel

        // Assert Panel
        WebElement panel = datePicker.getPanel();
        Assertions.assertNotNull(panel);
        String text = panel.getText();
        Assertions.assertTrue(text.contains("1978"));
        Assertions.assertTrue(text.contains("February"));

        WebElement timePicker = panel.findElement(By.className("ui-timepicker"));
        Assertions.assertEquals("11", timePicker.findElement(By.cssSelector("div.ui-hour-picker > span")).getText());
        Assertions.assertEquals("55", timePicker.findElement(By.cssSelector("div.ui-minute-picker > span")).getText());
        // #6458 showSeconds="true" automatically detected because of pattern contains 's'
        Assertions.assertEquals("19", timePicker.findElement(By.cssSelector("div.ui-second-picker > span")).getText());

        // Assert Submit Value
        page.button.click();
        LocalDateTime newValue = datePicker.getValue();
        Assertions.assertEquals(value, newValue);
        // #6459 showTime="true" automatically detected because of LocalDateTime
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        assertConfiguration(datePicker.getWidgetConfiguration(), newValue.format(dateTimeFormatter));
    }

    @Test
    @DisplayName("DatePicker: date with time HH:mm")
    public void testDateAndTimeWithHours(Page page) {
        // Arrange
        TestUtils.pause(1000);
        DatePicker datePicker = page.datePickerHours;
        Assertions.assertEquals(LocalDateTime.of(2020, 10, 31, 13, 13), datePicker.getValue());
        LocalDateTime value = LocalDateTime.of(1978, 2, 19, 11, 55);

        // Act
        datePicker.setValue(value);
        datePicker.showPanel(); // focus to bring up panel

        // Assert Panel
        WebElement panel = datePicker.getPanel();
        Assertions.assertNotNull(panel);
        String text = panel.getText();
        Assertions.assertTrue(text.contains("1978"));
        Assertions.assertTrue(text.contains("February"));

        WebElement timePicker = panel.findElement(By.className("ui-timepicker"));
        Assertions.assertEquals("11", timePicker.findElement(By.cssSelector("div.ui-hour-picker > span")).getText());
        Assertions.assertEquals("55", timePicker.findElement(By.cssSelector("div.ui-minute-picker > span")).getText());

        // Assert Submit Value
        page.button.click();
        LocalDateTime newValue = datePicker.getValue();
        Assertions.assertEquals(value, newValue);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        assertConfiguration(datePicker.getWidgetConfiguration(), newValue.format(dateTimeFormatter));
    }

    private void assertConfiguration(JSONObject cfg, String defaultDate) {
        assertNoJavascriptErrors();
        System.out.println("DatePicker Config = " + cfg);
        Assertions.assertEquals("mm/dd/yy", cfg.getString("dateFormat"));
        Assertions.assertEquals(defaultDate, cfg.getString("defaultDate"));
        Assertions.assertEquals("single", cfg.getString("selectionMode"));
        Assertions.assertEquals(true, cfg.getBoolean("showTime"));
        Assertions.assertFalse(cfg.getBoolean("inline"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:dpSeconds")
        DatePicker datePickerSeconds;

        @FindBy(id = "form:dpHours")
        DatePicker datePickerHours;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "datepicker/datePicker004.xhtml";
        }
    }
}
