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
package org.primefaces.extensions.integrationtests.cascadeselect;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CascadeSelect;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.Messages;
import org.primefaces.extensions.selenium.component.model.Msg;

import java.util.List;

public class CascadeSelect002Test extends AbstractPrimePageTest {

    @Test
    @Order(1)
    @DisplayName("CascadeSelect: select and submit item (SelectItems with value and label)")
    public void testBasic(Page page) {
        // Arrange
        CascadeSelect cascadeSelect = page.cascadeSelect;
        cascadeSelect.toggleDropdown();
        assertItems(page, 10);

        // Act
        cascadeSelect.select("nVidia");
        cascadeSelect.select("2000-Series");
        cascadeSelect.select("RTX 2080");
//        page.button.click();

        // Assert
        assertMessage(page, 0, "Selected GPU", "2080");
        assertConfiguration(cascadeSelect.getWidgetConfiguration());

        // Act
        page.button.click();

        // Assert
        assertMessage(page, 0, "Selected GPU", "2080");
        Assertions.assertEquals("RTX 2080", cascadeSelect.getSelectedLabel());
        assertConfiguration(cascadeSelect.getWidgetConfiguration());
    }

    private void assertItems(Page page, int leafItemCount) {
        CascadeSelect cascadeSelect = page.cascadeSelect;
        List<WebElement> options = cascadeSelect.getLeafItems();
        // List<String> labels = cascadeSelect.getLabels();
        Assertions.assertEquals(leafItemCount, options.size());
    }

    private void assertMessage(Page page, int index, String summary, String detail) {
        Msg message = page.messages.getMessage(index);
        Assertions.assertEquals(summary, message.getSummary());
        Assertions.assertEquals(detail, message.getDetail());
    }

    private void assertConfiguration(JSONObject cfg) {
        assertNoJavascriptErrors();
        System.out.println("CascadeSelect Config = " + cfg);
        Assertions.assertTrue(cfg.has("appendTo"));
        Assertions.assertTrue(cfg.has("behaviors"));
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:cascadeSelect")
        CascadeSelect cascadeSelect;

        @FindBy(id = "form:button")
        CommandButton button;

        @FindBy(id = "form:msgs")
        Messages messages;

        @Override
        public String getLocation() {
            return "cascadeselect/cascadeSelect002.xhtml";
        }
    }
}
