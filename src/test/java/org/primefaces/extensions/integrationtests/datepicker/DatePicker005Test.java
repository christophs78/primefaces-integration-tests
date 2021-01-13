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

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.integrationtests.general.utilities.TestUtils;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DatePicker;
import org.primefaces.extensions.selenium.component.Messages;

public class DatePicker005Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("DatePicker: preselected range")
    public void testPreselectedRange(Page page) {
        // Arrange
        DatePicker datePicker = page.datePicker;

        // Act
        datePicker.showPanel(); // focus to bring up panel

        // Assert Panel
        WebElement panel = datePicker.getPanel();
        Assertions.assertNotNull(panel);
        Assertions.assertTrue(PrimeSelenium.hasCssClass(panel.findElement(By.linkText("20")), "ui-state-active"));
        Assertions.assertTrue(PrimeSelenium.hasCssClass(panel.findElement(By.linkText("25")), "ui-state-active"));
        Assertions.assertFalse(PrimeSelenium.hasCssClass(panel.findElement(By.linkText("19")), "ui-state-active"));
        Assertions.assertFalse(PrimeSelenium.hasCssClass(panel.findElement(By.linkText("26")), "ui-state-active"));
        Assertions.assertEquals(6, panel.findElements(By.className("ui-state-active")).size());

        // Assert Submit Value
        page.button.click();
        Assertions.assertEquals("08/20/2020 - 08/25/2020", page.messages.getMessage(0).getDetail());
        assertConfiguration(datePicker.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("DatePicker: select range")
    public void testSelectRange(Page page) {
        // Arrange
        TestUtils.pause(1000);
        DatePicker datePicker = page.datePicker;

        // Act
        datePicker.showPanel(); // focus to bring up panel
        WebElement panel = datePicker.getPanel();
        Assertions.assertNotNull(panel);
        panel.findElement(By.linkText("3")).click();
        panel.findElement(By.linkText("5")).click();

        // Assert Panel
        Assertions.assertTrue(PrimeSelenium.hasCssClass(panel.findElement(By.linkText("3")), "ui-state-active"));
        Assertions.assertTrue(PrimeSelenium.hasCssClass(panel.findElement(By.linkText("5")), "ui-state-active"));
        Assertions.assertFalse(PrimeSelenium.hasCssClass(panel.findElement(By.linkText("2")), "ui-state-active"));
        Assertions.assertFalse(PrimeSelenium.hasCssClass(panel.findElement(By.linkText("6")), "ui-state-active"));
        Assertions.assertEquals(3, panel.findElements(By.className("ui-state-active")).size());

        // Assert Submit Value
        page.button.click();
        Assertions.assertEquals("08/03/2020 - 08/05/2020", page.messages.getMessage(0).getDetail());
        assertConfiguration(datePicker.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("DatePicker Config = " + cfg);
        Assertions.assertEquals("mm/dd/yy", cfg.getString("dateFormat"));
        Assertions.assertEquals("range", cfg.getString("selectionMode"));
        Assertions.assertFalse(cfg.getBoolean("inline"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:datepicker")
        DatePicker datePicker;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "datepicker/datePicker005.xhtml";
        }
    }
}
