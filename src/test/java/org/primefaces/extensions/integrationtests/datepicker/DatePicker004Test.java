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
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DatePicker;

public class DatePicker004Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("DatePicker: date with time HH:mm:ss. See GitHub #6458 and #6459")
    public void testDateAndTimeWithSeconds(Page page) {
        // Arrange
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
        datePicker.hidePanel();

        // Assert Submit Value
        // Assert Submit Value
        if (PrimeSelenium.isSafari()) {
            // TODO: Safari gives unexplained NPE on page.submitHours.click();
            assertNoJavascriptErrors();
        }
        else {
            page.submitSeconds.click();
            LocalDateTime newValue = datePicker.getValue();
            Assertions.assertEquals(value, newValue);
            // #6459 showTime="true" automatically detected because of LocalDateTime
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            assertConfiguration(datePicker.getWidgetConfiguration(), newValue.format(dateTimeFormatter));
        }
    }

    @Test
    @Order(2)
    @DisplayName("DatePicker: date with time HH:mm:ss using widget setDate() API.")
    public void testSetDateWidgetApi(Page page) {
        // Arrange
        DatePicker datePicker = page.datePickerSeconds;
        Assertions.assertEquals(LocalDateTime.of(2020, 8, 20, 22, 20, 19), datePicker.getValue());
        LocalDateTime value = LocalDateTime.of(1978, 2, 19, 11, 55, 19);

        // Act
        datePicker.setDate(value);
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
        datePicker.hidePanel();

        // Assert Submit Value
        if (PrimeSelenium.isSafari()) {
            // TODO: Safari gives unexplained NPE on page.submitHours.click();
            assertNoJavascriptErrors();
        }
        else {
            page.submitSeconds.click();
            LocalDateTime newValue = datePicker.getValue();
            Assertions.assertEquals(value, newValue);
            // #6459 showTime="true" automatically detected because of LocalDateTime
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            assertConfiguration(datePicker.getWidgetConfiguration(), newValue.format(dateTimeFormatter));
        }
    }

    @Test
    @Order(3)
    @DisplayName("DatePicker: date with time HH:mm")
    public void testDateAndTimeWithHours(Page page) {
        // Arrange
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
        datePicker.hidePanel();

        if (PrimeSelenium.isSafari()) {
            // TODO: Safari gives unexplained NPE on page.submitHours.click();
            assertNoJavascriptErrors();
        }
        else {
            // Assert Submit Value
            page.submitHours.click();
            LocalDateTime newValue = datePicker.getValue();
            Assertions.assertEquals(value, newValue);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
            assertConfiguration(datePicker.getWidgetConfiguration(), newValue.format(dateTimeFormatter));
        }
    }

    private void assertConfiguration(JSONObject cfg, String defaultDate) {
        assertNoJavascriptErrors();
        System.out.println("DatePicker Config = " + cfg);
        Assertions.assertEquals("mm/dd/yy", cfg.getString("dateFormat"));
        Assertions.assertEquals(defaultDate, cfg.getString("defaultDate"));
        Assertions.assertEquals("single", cfg.getString("selectionMode"));
        Assertions.assertTrue(cfg.getBoolean("showTime"));
        Assertions.assertFalse(cfg.getBoolean("inline"));
    }

    public static class Page extends AbstractPrimePage {

        @FindBy(id = "form1:dpHours")
        DatePicker datePickerHours;

        @FindBy(id = "form1:btnHours")
        CommandButton submitHours;

        @FindBy(id = "form2:dpSeconds")
        DatePicker datePickerSeconds;

        @FindBy(id = "form2:btnSeconds")
        CommandButton submitSeconds;

        @Override
        public String getLocation() {
            return "datepicker/datePicker004.xhtml";
        }
    }
}
