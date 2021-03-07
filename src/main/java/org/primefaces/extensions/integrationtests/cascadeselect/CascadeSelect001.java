/*
 * Copyright (c) 2011-2021 PrimeFaces Extensions
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.primefaces.extensions.integrationtests.cascadeselect;

import lombok.Data;
import org.primefaces.event.SelectEvent;
import org.primefaces.extensions.integrationtests.general.model.Driver;
import org.primefaces.extensions.integrationtests.general.service.RealDriverService;
import org.primefaces.extensions.integrationtests.general.utilities.TestUtils;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
@Data
public class CascadeSelect001 implements Serializable {

    private static final long serialVersionUID = -4524605633263059204L;

    private List<SelectItem> gpus;

    private String selectedGpu;

    @PostConstruct
    public void init() {
        gpus = new ArrayList<>();

        SelectItemGroup nVidia = new SelectItemGroup("nVidia");
        SelectItemGroup nv3000 = new SelectItemGroup("3000-Series");
        SelectItemGroup nv2000 = new SelectItemGroup("2000-Series");
        nVidia.setSelectItems(new SelectItem[]{nv3000, nv2000});

        SelectItem nv3090 = new SelectItem( "RTX 3090");
        SelectItem nv3080 = new SelectItem( "RTX 3080");
        SelectItem nv3070 = new SelectItem("RTX 3070");
        nv3000.setSelectItems(new SelectItem[]{nv3090, nv3080, nv3070});

        SelectItem nv2080Ti = new SelectItem("RTX 2080 Ti");
        SelectItem nv2080 = new SelectItem( "RTX 2080");
        SelectItem nv2070 = new SelectItem("RTX 3070");
        nv2000.setSelectItems(new SelectItem[]{nv2080Ti, nv2080, nv2070});

        SelectItemGroup amd = new SelectItemGroup("AMD");
        SelectItemGroup amd6000 = new SelectItemGroup("6000-Series");
        amd.setSelectItems(new SelectItem[]{amd6000});

        SelectItem amd6900 = new SelectItem("Radeon 6900");
        SelectItem amd6800xt = new SelectItem("Radeon 6800 XT");
        SelectItem amd6800 = new SelectItem("Radeon 6800");
        SelectItem amd6700 = new SelectItem("Radeon 6700");
        amd6000.setSelectItems(new SelectItem[]{amd6900, amd6800xt, amd6800, amd6700});

        gpus.add(nVidia);
        gpus.add(amd);
    }

    public void onItemSelect(SelectEvent event) {
        // pause for 1 second to verify AJAX guards are working properly
        TestUtils.wait(1000);

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected GPU", (String)event.getObject());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void submit() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected GPU", selectedGpu);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

}
