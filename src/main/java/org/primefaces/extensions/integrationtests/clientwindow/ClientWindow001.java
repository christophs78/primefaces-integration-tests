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
package org.primefaces.extensions.integrationtests.clientwindow;

import lombok.Data;
import org.primefaces.util.LangUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.ClientWindow;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Named
@ViewScoped
@Data
public class ClientWindow001 implements Serializable {

    private static final long serialVersionUID = -7518459955779385834L;
    private String value;

    @PostConstruct
    public void init() {
        value = "byebye!";
    }

    public void submit() {
        ClientWindow clientWindow = FacesContext.getCurrentInstance().getExternalContext().getClientWindow();
        FacesMessage msg = new FacesMessage("jfwid", clientWindow.getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
