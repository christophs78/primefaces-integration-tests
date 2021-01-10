package org.primefaces.extensions.integrationtests.utilities;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.extensions.selenium.PrimeSelenium;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestUtils {

    public static void pause(int milliseconds) {
        if (PrimeSelenium.isSafari()) {
            try {
                Thread.sleep(milliseconds);
            }
            catch (InterruptedException ex) {
                System.err.println("Safari pause was interrupted!");
            }
        }
    }

    public static String join(Iterable<? extends CharSequence> values) {
        String result = "";
        if (values != null) {
            result = String.join(", ", values);
        }
        return result;
    }

    public static FacesMessage addMessage(String detail) {
        FacesMessage msg = new FacesMessage(detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return msg;
    }

    public static FacesMessage addMessage(String summary, String detail) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        return msg;
    }
}
