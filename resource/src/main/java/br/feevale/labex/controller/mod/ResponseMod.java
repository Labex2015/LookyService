package br.feevale.labex.controller.mod;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by 0126128 on 03/07/2015.
 */
public class ResponseMod extends BaseMod<ResponseMod>{

    @NotNull
    @Min(value = 1)
    public Long id;
    public String text;
    @NotNull
    public Boolean accepted;

    public ResponseMod() {
    }

    public ResponseMod(Long id, String text, Boolean accepted) {
        this.id = id;
        this.text = text;
        this.accepted = accepted;
    }
}
