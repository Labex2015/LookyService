package br.feevale.labex.controller.mod;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by grimmjowjack on 8/21/15.
 */
@ApiObject
public class PositionMod extends BaseMod<PositionMod>{

    @ApiObjectField
    @NotNull
    @Min(value = -90)
    @Max(value = 90)
    public Float latitude;

    @ApiObjectField
    @NotNull
    @Min(value = -180)
    @Max(value = 180)
    public Float longitude;

    public PositionMod() {
    }

    public PositionMod(Float latitude, Float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
