package org.mindera.autism.web.module.patient;

/**
 * Created by fonsecaj on 09/12/15.
 */
public class CreatePatientForm {

    private String name;

    private String successUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

}
