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
package org.primefaces.extensions.integrationtests.chips;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.extensions.integrationtests.utilities.TestUtils;

import lombok.Data;

@Named
@ViewScoped
@Data
public class Chips003 implements Serializable {

    private static final long serialVersionUID = -8778258234357429563L;

    private List<String> values;

    @PostConstruct
    public void init() {
        values = Arrays.asList("One", "Two", "One", "Two");
    }

    public void submit() {
        TestUtils.addMessage(TestUtils.join(values));
    }

}
