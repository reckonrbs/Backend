package com.bankofapis.web.service.recommendation;

import com.bankofapis.core.model.accounts.OBReadProduct;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OBReadProductROI extends OBReadProduct {

    @JsonProperty("roi")
    private double roi;

    @JsonProperty("additionalBenifits")
    private String additionalBenifits;

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getAdditionalBenifits() {
        return additionalBenifits;
    }

    public void setAdditionalBenifits(String additionalBenifits) {
        this.additionalBenifits = additionalBenifits;
    }


}
