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
package org.primefaces.extensions.integrationtests.dialog;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.PrimeSelenium;
import org.primefaces.extensions.selenium.component.*;

public class Dialog001Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("Dialog: edit values within dialog, OK, close-event")
    public void testBasicOk(Page page) {
        // Arrange

        // Act
        page.buttonShowDialog.click();

        // Assert
        Assertions.assertTrue(PrimeSelenium.isElementDisplayed(page.dialog));

        // Act
        page.inputText2Dialog.setValue("test123");
        page.buttonDlgOk.click();

        // Assert
        Assertions.assertFalse(PrimeSelenium.isElementDisplayed(page.dialog));
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Dialog - close-event"));
        Assertions.assertTrue(page.messages.getMessage(0).getDetail().contains("text2: test123"));
        Assertions.assertEquals("test123", page.inputText2Readonly.getValue());

        // Act
        page.buttonSubmit.click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Submit"));
        Assertions.assertTrue(page.messages.getMessage(0).getDetail().contains("text2: test123"));
        Assertions.assertEquals("test123", page.inputText2Readonly.getValue());

        assertConfiguration(page.dialog.getWidgetConfiguration());
    }

    @Test
    @Order(2)
    @DisplayName("Dialog: edit values within dialog, cancel")
    public void testBasicCancel(Page page) {
        // Arrange

        // Act
        page.buttonShowDialog.click();

        // Assert
        Assertions.assertTrue(PrimeSelenium.isElementDisplayed(page.dialog));

        // Act
        page.inputText2Dialog.setValue("testabc");
        page.buttonDlgCancel.click();

        // Assert
        Assertions.assertFalse(PrimeSelenium.isElementDisplayed(page.dialog));
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Dialog - close-event"));
        Assertions.assertTrue(page.messages.getMessage(0).getDetail().contains("text2: null"));
        Assertions.assertEquals("", page.inputText2Readonly.getValue());

        // Act
        page.buttonSubmit.click();

        // Assert
        Assertions.assertTrue(page.messages.getMessage(0).getSummary().contains("Submit"));
        Assertions.assertTrue(page.messages.getMessage(0).getDetail().contains("text2: null"));
        Assertions.assertEquals("", page.inputText2Readonly.getValue());

        assertConfiguration(page.dialog.getWidgetConfiguration());
    }

    @Test
    @Order(3)
    @DisplayName("Dialog: show, hide & title")
    public void testAPI(Page page) {
        // Arrange

        // Act
        page.dialog.show();

        // Assert
        Assertions.assertTrue(PrimeSelenium.isElementDisplayed(page.dialog));
        Assertions.assertEquals("Modal Dialog", page.dialog.getTitle());

        // Act
        page.dialog.hide();

        // Assert
        Assertions.assertFalse(PrimeSelenium.isElementDisplayed(page.dialog));

        assertConfiguration(page.dialog.getWidgetConfiguration());
    }


    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("Dialog Config = " + cfg);
        Assertions.assertTrue(cfg.has("modal"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:msgs")
        Messages messages;

        @FindBy(id = "form:text1")
        InputText inputText1;

        @FindBy(id = "form:date")
        DatePicker datePicker;

        @FindBy(id = "form:text2Readonly")
        InputText inputText2Readonly;

        @FindBy(id = "form:button")
        CommandButton button;

        @FindBy(id = "form:buttonSubmit")
        CommandButton buttonSubmit;

        @FindBy(id = "form:buttonShowDialog")
        CommandButton buttonShowDialog;

        @FindBy(id = "form:text2Dialog")
        InputText inputText2Dialog;

        @FindBy(id = "form:buttonDlgOk")
        CommandButton buttonDlgOk;

        @FindBy(id = "form:buttonDlgCancel")
        CommandButton buttonDlgCancel;

        @FindBy(id = "form:dlg")
        Dialog dialog;

        @Override
        public String getLocation() {
            return "dialog/dialog001.xhtml";
        }
    }
}
