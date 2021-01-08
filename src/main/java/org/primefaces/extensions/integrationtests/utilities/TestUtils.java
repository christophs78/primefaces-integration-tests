package org.primefaces.extensions.integrationtests.utilities;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

    public static String join(Iterable<? extends CharSequence> values) {
        String result = "";
        if (values != null) {
            result = String.join(", ", values);
        }
        return result;
    }

    public static FacesMessage addMessage(String message) {
        FacesMessage msg = new FacesMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return msg;
    }
}
