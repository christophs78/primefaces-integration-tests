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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.integrationtests.utilities.TestUtils;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DatePicker;

public class DatePicker001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("DatePicker: set date and basic panel validation")
    public void testBasic(Page page) {
        // Arrange
        DatePicker datePicker = page.datePicker;
        Assertions.assertEquals(LocalDate.now(), datePicker.getValue().toLocalDate());
        LocalDate value = LocalDate.of(1978, 2, 19);

        // Act
        datePicker.setValue(value);
        datePicker.showPanel(); // focus to bring up panel

        // Assert Panel
        WebElement panel = datePicker.getPanel();
        Assertions.assertNotNull(panel);
        String text = panel.getText();
        System.out.println(text);
        Assertions.assertTrue(text.contains("1978"));
        Assertions.assertTrue(text.contains("February"));

        // Assert Submit Value
        page.button.click();
        LocalDate newValue = datePicker.getValueAsLocalDate();
        Assertions.assertEquals(value, newValue);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        assertConfiguration(datePicker.getWidgetConfiguration(), newValue.format(dateTimeFormatter));
    }

    @Test
    @Order(2)
    @DisplayName("DatePicker: select date via click on day")
    public void testSelectDate(Page page) {
        // Arrange
        TestUtils.pause(1000);
        DatePicker datePicker = page.datePicker;
        LocalDate value = LocalDate.of(1978, 2, 19);

        // Act
        datePicker.setValue(value);
        datePicker.showPanel(); // focus to bring up panel
        WebElement panel = datePicker.getPanel();
        panel.findElement(By.className("ui-datepicker-next")).click();
        panel.findElement(By.linkText("25")).click();

        // Assert selected value
        LocalDate expectedDate = LocalDate.of(1978, 3, 25);
        Assertions.assertEquals(expectedDate, datePicker.getValueAsLocalDate());

        // Assert Submit Value
        page.button.click();
        LocalDate newValue = datePicker.getValueAsLocalDate();
        Assertions.assertEquals(expectedDate, newValue);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        assertConfiguration(datePicker.getWidgetConfiguration(), newValue.format(dateTimeFormatter));
    }

    @Test
    @Order(3)
    @DisplayName("DatePicker: highlight today and selected")
    public void testHighlight(Page page) {
        // Arrange
        TestUtils.pause(1000);
        DatePicker datePicker = page.datePicker;

        // Act
        LocalDate selectedDate = LocalDate.now();
        if (selectedDate.getDayOfMonth() == 1) {
            selectedDate = selectedDate.plusMonths(1).minusDays(1);
        }
        else {
            selectedDate = selectedDate.minusDays(1);
        }
        datePicker.setValue(selectedDate);
        datePicker.click(); // focus to bring up panel

        //Assert panel
        String currentDayOfMonth = ((Integer)LocalDate.now().getDayOfMonth()).toString();
        String selectedDayOfMonth = ((Integer)selectedDate.getDayOfMonth()).toString();
        Assertions.assertTrue(PrimeSelenium.hasCssClass(datePicker.getPanel().findElement(By.linkText(selectedDayOfMonth)), "ui-state-active"));
        Assertions.assertTrue(PrimeSelenium.hasCssClass(datePicker.getPanel().findElement(By.linkText(currentDayOfMonth)), "ui-state-highlight"));
    }

    private void assertConfiguration(JSONObject cfg, String defaultDate) {
        assertNoJavascriptErrors();
        System.out.println("DatePicker Config = " + cfg);
        Assertions.assertEquals("mm/dd/yy", cfg.getString("dateFormat"));
        Assertions.assertEquals(defaultDate, cfg.getString("defaultDate"));
        Assertions.assertEquals("single", cfg.getString("selectionMode"));
        Assertions.assertFalse(cfg.getBoolean("inline"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datepicker")
        DatePicker datePicker;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "datepicker/datePicker001.xhtml";
        }
    }
}
