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
package org.primefaces.extensions.integrationtests.tabview;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.FindBy;
import org.primefaces.extensions.selenium.AbstractPrimePage;
import org.primefaces.extensions.selenium.AbstractPrimePageTest;
import org.primefaces.extensions.selenium.component.CommandButton;
import org.primefaces.extensions.selenium.component.InputText;
import org.primefaces.extensions.selenium.component.TabView;
import org.primefaces.extensions.selenium.component.model.Tab;

public class TabView002Test extends AbstractPrimePageTest {

    @Test
    @DisplayName("TabView: Test dynamic tabview loading AJAX tabs")
    public void testDynamic(Page page) {
        // Arrange
        TabView tabView = page.tabView;

        // Assert - part 1
        List<Tab> tabs = tabView.getTabs();
        Assertions.assertNotNull(tabs);
        Assertions.assertEquals("Lewis", page.inputtext1.getValue());

        // Act
        tabView.toggleTab(1);

        // Assert - part 2
        assertNoJavascriptErrors();
        Assertions.assertEquals(1, tabView.getSelectedTab().getIndex());
        Assertions.assertEquals("Max", page.inputtext2.getValue());

        // Act
        tabView.toggleTab(2);

        // Assert - part 3
        assertNoJavascriptErrors();
        Assertions.assertEquals(2, tabView.getSelectedTab().getIndex());
        Assertions.assertEquals("Charles", page.inputtext3.getValue());
    }

    public static class Page extends AbstractPrimePage {
        @FindBy(id = "form:tabview")
        TabView tabView;

        @FindBy(id = "form:tabview:inputtext1")
        InputText inputtext1;

        @FindBy(id = "form:tabview:inputtext2")
        InputText inputtext2;

        @FindBy(id = "form:tabview:inputtext3")
        InputText inputtext3;

        @FindBy(id = "form:button")
        CommandButton button;

        @Override
        public String getLocation() {
            return "tabview/tabView002.xhtml";
        }
    }
}
