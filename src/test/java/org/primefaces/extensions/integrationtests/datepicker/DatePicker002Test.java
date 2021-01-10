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

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.integrationtests.utilities.TestUtils;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.DatePicker;

public class DatePicker002Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    public void testWithoutShowOtherMonths(Page page) {
        // Arrange
        DatePicker datePicker = page.datePicker1;

        // Act
        datePicker.click(); // focus to bring up panel

        // Assert
        assertNoJavascriptErrors();
        WebElement panel = datePicker.getPanel();
        Assertions.assertNotNull(panel);
        List<WebElement> elements = panel.findElements(By.cssSelector("table.ui-datepicker-calendar"));
        Assertions.assertNotNull(elements);
        Assertions.assertEquals(elements.size(), 1);
        WebElement table = elements.get(0);

        List<WebElement> days = table.findElements(By.cssSelector("td a"));
        Assertions.assertNotNull(days);
        Assertions.assertEquals(days.size(), 28);

        List<WebElement> daysOtherMonths = table.findElements(By.cssSelector("td.ui-datepicker-other-month"));
        Assertions.assertNotNull(daysOtherMonths);
        Assertions.assertEquals(daysOtherMonths.size(), 14);
        Assertions.assertEquals(daysOtherMonths.stream().filter(dayOtherMonth -> dayOtherMonth.isDisplayed()).count(), 0);
    }

    @Test
    @Order(2)
    public void testWithShowOtherMonths(Page page) {
        // Arrange
        TestUtils.pause(1000);
        DatePicker datePicker = page.datePicker2;

        // Act
        datePicker.click(); // focus to bring up panel

        // Assert
        assertNoJavascriptErrors();
        WebElement panel = datePicker.getPanel();
        Assertions.assertNotNull(panel);
        List<WebElement> elements = panel.findElements(By.cssSelector("table.ui-datepicker-calendar"));
        Assertions.assertNotNull(elements);
        Assertions.assertEquals(1, elements.size());
        WebElement table = elements.get(0);

        List<WebElement> days = table.findElements(By.cssSelector("td a"));
        Assertions.assertNotNull(days);
        Assertions.assertEquals(28, days.size());

        List<WebElement> daysOtherMonths = table.findElements(By.cssSelector("td.ui-datepicker-other-month"));
        Assertions.assertNotNull(daysOtherMonths);
        Assertions.assertEquals(14, daysOtherMonths.size());
        Assertions.assertEquals(14, daysOtherMonths.stream().filter(dayOtherMonth -> dayOtherMonth.isDisplayed()).count());
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:datepicker1")
        DatePicker datePicker1;

        @FindBy(id = "form:datepicker2")
        DatePicker datePicker2;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "datepicker/datePicker002.xhtml";
        }
    }
}
