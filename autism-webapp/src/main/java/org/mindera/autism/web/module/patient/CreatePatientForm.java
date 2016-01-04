package org.mindera.autism.web.module.patient;

/**
 * Created by fonsecaj on 09/12/15.
 */
public class CreatePatientForm {

    private String description;

    private String successUrl;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

}
