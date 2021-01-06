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
