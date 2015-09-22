package br.feevale.labex.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by grimmjowjack on 9/15/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookRootResponse {

    public FacebookResponse[] data;

    public FacebookRootResponse() { }

    public FacebookRootResponse(FacebookResponse[] data) {
        this.data = data;
    }

    public FacebookResponse[] getData() {
        return data;
    }

    public void setData(FacebookResponse[] data) {
        this.data = data;
    }
}
