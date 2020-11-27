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

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.data.FilterEvent;

import lombok.Data;

@Named
@ViewScoped
@Data
public class DataTable009 implements Serializable {

    private static final long serialVersionUID = -7518459955779385834L;

    private List<ProgrammingLanguage> progLanguages;
    private List<ProgrammingLanguage> filteredProgLanguages;

    @Inject
    private ProgrammingLanguageService service;

    @PostConstruct
    public void init() {
        progLanguages = service.getLangs();
    }

    public void filterListener(FilterEvent filterEvent) {
        // show actual filter as message
        filterEvent.getFilterBy().values().stream()
                    .filter(filterMeta -> filterMeta.getFilterValue() != null)
                    .forEach(filterMeta -> {
                                    FacesMessage msg = new FacesMessage("FilterValue for " + filterMeta.getField(), filterMeta.getFilterValue().toString());
                                    FacesContext.getCurrentInstance().addMessage(null, msg);
                                }
                    );

        // show actual filtered values (attention: filter is not yet applied - see https://github.com/primefaces/primefaces/issues/1390)
        DataTable dataTable = (DataTable) filterEvent.getSource();
        String filterValuesFlat = "null";
        if (dataTable.getFilteredValue() != null) {
            List<ProgrammingLanguage> filteredProgLanguagesAtEvent = (List<ProgrammingLanguage>) dataTable.getFilteredValue();

            filterValuesFlat = filteredProgLanguagesAtEvent.stream().map(lang -> lang.getName()).collect(Collectors.joining(","));
        }

        FacesMessage msg = new FacesMessage("FilteredValue(s)", filterValuesFlat);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
