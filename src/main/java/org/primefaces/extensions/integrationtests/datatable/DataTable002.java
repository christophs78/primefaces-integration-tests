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
package org.primefaces.extensions.integrationtests.datatable;

import lombok.Data;
import org.primefaces.event.SelectEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Data
public class DataTable002 implements Serializable {

    private static final long serialVersionUID = -7518459955779385834L;

    private ProgrammingLanguageLazyDataModel lazyDataModel;
    private List<ProgrammingLanguage> filteredProgLanguages;

    private ProgrammingLanguage selectedProgrammingLanguage;

    @PostConstruct
    public void init() {
        lazyDataModel = new ProgrammingLanguageLazyDataModel();
    }

    public void onRowSelect(SelectEvent<ProgrammingLanguage> event) {
        FacesMessage msg = new FacesMessage("ProgrammingLanguage Selected", event.getObject().getId() + " - " + event.getObject().getName());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
