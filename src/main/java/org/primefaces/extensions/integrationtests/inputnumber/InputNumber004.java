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
package org.primefaces.extensions.integrationtests.inputnumber;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

import lombok.Data;

@Named
@ViewScoped
@Data
public class InputNumber004 implements Serializable {

    private static final long serialVersionUID = -7518459955779385834L;

    @Positive
    @Max(999999)
    private Integer integer;

    @Positive
    @DecimalMax("999999.99")
    private BigDecimal decimal;

    @PostConstruct
    public void init() {
        integer = Integer.valueOf(66);
        decimal = new BigDecimal("6.78");
    }

}
