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
package org.primefaces.extensions.integrationtests.selectoneradio;

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
import org.primefaces.extensions.selenium.component.SelectOneRadio;

public class SelectOneRadio001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("SelectOneRadio: basic usecase")
    public void testBasic(Page page) {
        // Arrange
        SelectOneRadio selectOneRadio = page.selectOneRadio;
        Assertions.assertEquals(4, selectOneRadio.getItemsSize());
        Assertions.assertEquals("Lewis", selectOneRadio.getSelectedLabel());

        // Act
        selectOneRadio.select("Max");
        page.submit.click();

        // Assert - part 1
        Assertions.assertEquals("Max", selectOneRadio.getSelectedLabel());
        assertConfiguration(selectOneRadio.getWidgetConfiguration());

        // Act
        selectOneRadio.select(3);
        page.submit.click();

        // Assert - part 2
        Assertions.assertEquals("Lando", selectOneRadio.getSelectedLabel());
        assertConfiguration(selectOneRadio.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("SelectOneRadio: Selecting again remains selected with unselectable='false'")
    public void testNotUnselectable(Page page) {
        // Arrange
        SelectOneRadio selectOneRadio = page.selectOneRadio;
        Assertions.assertEquals(4, selectOneRadio.getItemsSize());
        Assertions.assertEquals("Lewis", selectOneRadio.getSelectedLabel());

        // Act
        selectOneRadio.select("Lewis");
        page.submit.click();

        // Assert
        Assertions.assertEquals("Lewis", selectOneRadio.getSelectedLabel());
        assertConfiguration(selectOneRadio.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("SelectOneRadio: Disable component using widget API")
    public void testDisable(Page page) {
        // Arrange
        SelectOneRadio selectOneRadio = page.selectOneRadio;
        Assertions.assertEquals("Lewis", selectOneRadio.getSelectedLabel());

        // Act
        selectOneRadio.disable();

        // Assert
        for (WebElement radioButton : selectOneRadio.getRadioButtons()) {
            Assertions.assertFalse(PrimeSelenium.isElementClickable(radioButton.findElement(By.className("ui-radiobutton-box"))));
        }
        assertConfiguration(selectOneRadio.getWidgetConfiguration());
    }

    @Test
    @Order(4)
    @DisplayName("SelectOneRadio: Enable component using widget API")
    public void testEnable(Page page) {
        // Arrange
        SelectOneRadio selectOneRadio = page.selectOneRadio;
        Assertions.assertEquals("Lewis", selectOneRadio.getSelectedLabel());

        // Act
        selectOneRadio.disable();
        selectOneRadio.enable();

        // Assert
        for (WebElement radioButton : selectOneRadio.getRadioButtons()) {
            Assertions.assertTrue(PrimeSelenium.isElementClickable(radioButton.findElement(By.className("ui-radiobutton-box"))));
        }
        assertConfiguration(selectOneRadio.getWidgetConfiguration());
    }

    @Test
    @Order(5)
    @DisplayName("SelectOneRadio: Disable option using widget API")
    public void testDisableOption(Page page) {
        // Arrange
        SelectOneRadio selectOneRadio = page.selectOneRadio;
        Assertions.assertEquals("Lewis", selectOneRadio.getSelectedLabel());
        WebElement radioButton = selectOneRadio.getRadioButtonBox(2);
        Assertions.assertTrue(PrimeSelenium.isElementClickable(radioButton));

        // Act
        selectOneRadio.disableOption(2);

        // Assert
        Assertions.assertFalse(PrimeSelenium.isElementClickable(radioButton));
        assertConfiguration(selectOneRadio.getWidgetConfiguration());
    }

    @Test
    @Order(6)
    @DisplayName("SelectOneRadio: Enable option using widget API")
    public void testEnableOption(Page page) {
        // Arrange
        SelectOneRadio selectOneRadio = page.selectOneRadio;
        Assertions.assertEquals("Lewis", selectOneRadio.getSelectedLabel());
        WebElement radioButton = selectOneRadio.getRadioButtonBox(2);
        Assertions.assertTrue(PrimeSelenium.isElementClickable(radioButton));

        // Act - disable option
        selectOneRadio.disableOption(2);

        // Assert
        Assertions.assertFalse(PrimeSelenium.isElementClickable(radioButton));

        //Act - enable option
        selectOneRadio.enableOption(2);

        // Assert
        Assertions.assertTrue(PrimeSelenium.isElementClickable(radioButton));
        assertConfiguration(selectOneRadio.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("SelectOneRadio Config = " + cfg);
        Assertions.assertFalse(cfg.getBoolean("unselectable"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:selectoneradio")
        SelectOneRadio selectOneRadio;

        @FindBy(id = "form:submit")
        CommandButton submit;

        @Override
        public String getLocation() {
            return "selectoneradio/selectOneRadio001.xhtml";
        }
    }
}
