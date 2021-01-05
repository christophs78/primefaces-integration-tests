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
package org.primefaces.extensions.integrationtests.rating;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.RateEvent;

import lombok.Data;

@Named
@ViewScoped
@Data
public class Rating001 implements Serializable {

    private static final long serialVersionUID = -8778258234357429563L;

    private Integer rating1;
    private Integer rating2 = 3;
    private Integer rating3 = 2;

    public void onrate(RateEvent<Integer> rateEvent) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rate Event", "You rated:" + rateEvent.getRating());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void oncancel() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel Event", "Rate Reset");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
