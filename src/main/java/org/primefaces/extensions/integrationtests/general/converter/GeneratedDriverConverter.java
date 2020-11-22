package org.primefaces.extensions.integrationtests.general.converter;

import org.primefaces.extensions.integrationtests.general.model.Driver;
import org.primefaces.extensions.integrationtests.general.service.GeneratedDriverService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@FacesConverter(value = "generatedDriverConverter", managed = true)
public class GeneratedDriverConverter implements Converter {

    @Inject
    private GeneratedDriverService driverService;

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                int id = Integer.parseInt(value);
                return driverService.getDrivers().stream().filter(d -> d.getId() == id).findFirst().get();
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid driver."));
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object driver) {
        if(driver != null) {
            return String.valueOf(((Driver)driver).getId());
        }
        else {
            return null;
        }
    }
}
