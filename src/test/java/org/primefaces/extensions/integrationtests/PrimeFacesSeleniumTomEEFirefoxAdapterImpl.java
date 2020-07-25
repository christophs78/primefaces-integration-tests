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
package org.primefaces.extensions.integrationtests;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.primefaces.extensions.selenium.spi.PrimeSeleniumAdapter;

public class PrimeFacesSeleniumTomEEFirefoxAdapterImpl extends PrimeFacesSeleniumTomEEAdapter implements PrimeSeleniumAdapter {

    private static final String HEADLESS_MODE_SYSPROP_NAME = "webdriver.headless";

    private static final String HEADLESS_MODE_SYSPROP_VAL_DEFAULT = "false";

    @Override
    public WebDriver createWebDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setHeadless(
                    Boolean.parseBoolean(
                                System.getProperty(HEADLESS_MODE_SYSPROP_NAME, HEADLESS_MODE_SYSPROP_VAL_DEFAULT)
                    )
        );
        return new FirefoxDriver(options);
    }

}
