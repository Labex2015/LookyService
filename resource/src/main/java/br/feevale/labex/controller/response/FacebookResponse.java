package br.feevale.labex.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by grimmjowjack on 9/15/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FacebookResponse {

    public String category;
    public String name;
    public String access_token;
    public String[] perms;
    public Long id;

    public FacebookResponse() {
    }

    public FacebookResponse(String category, String name, String access_token, String[] perms, Long id) {
        this.category = category;
        this.name = name;
        this.access_token = access_token;
        this.perms = perms;
        this.id = id;
    }
}
