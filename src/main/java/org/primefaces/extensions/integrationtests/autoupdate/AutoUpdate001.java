/**
 * Copyright 2011-2020 PrimeFaces Extensions
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.extensions.integrationtests.autoupdate;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import lombok.Data;

@Named
@ViewScoped
@Data
public class AutoUpdate001 implements Serializable {

    private static final long serialVersionUID = -7518459955779385834L;
    private String value1;
    private String value2;
    private String value3;

    public void submitGlobal() {
        setValue1("global");
        setValue2("global");
        setValue3("global");
    }

    public void submitEvent1() {
        setValue1("one");
        setValue2("one");
        setValue3("one");
    }

    public void submitEvent2() {
        setValue1("two");
        setValue2("two");
        setValue3("two");
    }
}
