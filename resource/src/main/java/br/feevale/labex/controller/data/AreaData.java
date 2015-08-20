package br.feevale.labex.controller.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created by grimmjowjack on 8/14/15.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AreaData {

    private String id;
    private String name;

    public AreaData(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
