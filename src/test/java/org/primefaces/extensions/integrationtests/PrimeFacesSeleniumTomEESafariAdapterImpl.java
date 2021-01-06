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

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.primefaces.extensions.selenium.spi.PrimeSeleniumAdapter;

public class PrimeFacesSeleniumTomEESafariAdapterImpl extends PrimeFacesSeleniumTomEEAdapter implements PrimeSeleniumAdapter {

    @Override
    public WebDriver createWebDriver() {
        SafariOptions safariOptions = new SafariOptions();
        /*
         * Safari does not support headless as of september 2020
         * https://github.com/SeleniumHQ/selenium/issues/5985
         * https://discussions.apple.com/thread/251837694
         */
        //safariOptions.setHeadless(PrimeFacesSeleniumTomEEAdapter.isHeadless());
        safariOptions.setCapability("safari:diagnose", "true");
        return new SafariDriver(safariOptions);
    }

}
