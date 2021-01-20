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
package org.primefaces.extensions.integrationtests.confirmpopup;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.ConfirmPopup;
import org.primefaces.extensions.selenium.component.Messages;

public class ConfirmPopup001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("ConfirmPopup: Show the popup")
    public void testShowWidget(Page page) {
        // Arrange
        ConfirmPopup popup = page.popup;
        Assertions.assertFalse(popup.isVisible());

        // Act
        page.confirm.click();

        // Assert
        Assertions.assertTrue(popup.isVisible());
        Assertions.assertEquals("Are you sure you want to proceed?", popup.getMessage().getText());
        Assertions.assertEquals("ui-confirm-popup-icon pi pi-exclamation-triangle", popup.getIcon().getAttribute("class"));
        assertConfiguration(popup.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("ConfirmPopup: Show widget method")
    public void testHideWidget(Page page) {
        // Arrange
        ConfirmPopup popup = page.popup;
        page.confirm.click();
        Assertions.assertTrue(popup.isVisible());

        // Act
        popup.hidePopup();

        // Assert
        Assertions.assertFalse(popup.isVisible());
        assertConfiguration(popup.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("ConfirmPopup: Confirm button pressing NO")
    public void testConfirmNo(Page page) {
        // Arrange
        ConfirmPopup popup = page.popup;
        Assertions.assertFalse(popup.isVisible());
        page.confirm.click();

        // Act
        popup.getNoButton().click();

        // Assert
        Assertions.assertFalse(popup.isVisible());
        Assertions.assertTrue(page.message.isEmpty());
        assertConfiguration(popup.getWidgetConfiguration());
    }

    @Test
    @Order(4)
    @DisplayName("ConfirmPopup: Confirm button pressing YES")
    public void testConfirmYes(Page page) {
        // Arrange
        ConfirmPopup popup = page.popup;
        Assertions.assertFalse(popup.isVisible());
        page.confirm.click();

        // Act
        popup.getYesButton().click();

        // Assert
        Assertions.assertFalse(popup.isVisible());
        Assertions.assertEquals("You have accepted", page.message.getMessage(0).getDetail());
        assertConfiguration(popup.getWidgetConfiguration());
    }

    @Test
    @Order(5)
    @DisplayName("ConfirmPopup: Delete button pressing NO")
    public void testDeleteNo(Page page) {
        // Arrange
        ConfirmPopup popup = page.popup;
        Assertions.assertFalse(popup.isVisible());
        page.delete.click();

        // Act
        popup.getNoButton().click();

        // Assert
        Assertions.assertFalse(popup.isVisible());
        Assertions.assertTrue(page.message.isEmpty());
        assertConfiguration(popup.getWidgetConfiguration());
    }

    @Test
    @Order(6)
    @DisplayName("ConfirmPopup: Delete button pressing YES")
    public void testDeleteYes(Page page) {
        // Arrange
        ConfirmPopup popup = page.popup;
        Assertions.assertFalse(popup.isVisible());
        page.delete.click();

        // Act
        popup.getYesButton().click();

        // Assert
        Assertions.assertFalse(popup.isVisible());
        Assertions.assertEquals("Record deleted", page.message.getMessage(0).getDetail());
        assertConfiguration(popup.getWidgetConfiguration());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("ConfirmPopup Config = " + cfg);
        Assertions.assertTrue(cfg.getBoolean("global"));
        Assertions.assertTrue(cfg.getBoolean("dismissable"));
        Assertions.assertEquals("fade", cfg.getString("showEffect"));
        Assertions.assertEquals("fade", cfg.getString("hideEffect"));
        Assertions.assertEquals("@(body)", cfg.getString("appendTo"));
    }

    public static class Page extends AbstractPrimePage {

        @FindBy(id = "form:confirmpopup")
        ConfirmPopup popup;

        @FindBy(id = "form:message")
        Messages message;

        @FindBy(id = "form:confirm")
        CommandButton confirm;

        @FindBy(id = "form:delete")
        CommandButton delete;

        @Override
        public String getLocation() {
            return "confirmpopup/confirmPopup001.xhtml";
        }
    }
}
