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
package org.primefaces.extensions.integrationtests.autocomplete;

import lombok.Data;
import org.primefaces.extensions.integrationtests.general.model.Driver;
import org.primefaces.extensions.integrationtests.general.service.GeneratedDriverService;
import org.primefaces.util.LangUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
@Data
public class AutoComplete002 implements Serializable {

    private static final long serialVersionUID = 5157497001324985194L;

    @Inject
    private GeneratedDriverService service;

    private Driver driver;

    @PostConstruct
    public void init() {
        driver = service.getDrivers().get(4);
    }

    public void submit() {
        if (driver != null) {
            FacesMessage msg = new FacesMessage("Driver", "id: " + driver.getId()+ ", name: " + driver.getName());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
