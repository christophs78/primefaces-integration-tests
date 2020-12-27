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
package org.primefaces.extensions.integrationtests.timeline;

import java.io.Serializable;
import java.time.LocalDate;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import lombok.Data;

@Named
@ViewScoped
@Data
public class Timeline002 implements Serializable {

    private static final long serialVersionUID = 3439663389646247697L;

    private TimelineModel<String, ?> model;

    @PostConstruct
    public void init() {
        model = new TimelineModel<>();
        // B.C. Dates
        model.add(TimelineEvent.<String>builder().data("164 B.C.").startDate(LocalDate.of(-164, 6, 12)).build());
        model.add(TimelineEvent.<String>builder().data("163 B.C.").startDate(LocalDate.of(-163, 10, 11)).build());
    }

    public void onSelect(TimelineSelectEvent<String> e) {
        TimelineEvent<String> timelineEvent = e.getTimelineEvent();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected event:", timelineEvent.getData());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
