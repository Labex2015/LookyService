package mocks;

import br.feevale.labex.controller.mod.ResponseMod;

/**
 * Created by 0126128 on 03/07/2015.
 */
public class ResponseMocks {

    public static ResponseMod getPositiveResponse(Long id){
        ResponseMod mod = new ResponseMod();
        mod.accepted = true;
        mod.id = id;
        mod.text = "Olá, vou poder lhe ajudar.";
        return mod;
    }

    public static ResponseMod getNegativeResponse(Long id){
        ResponseMod mod = new ResponseMod();
        mod.accepted = false;
        mod.id = id;
        mod.text = "Olá, vou poder lhe ajudar.";
        return mod;
    }

    public static ResponseMod getInvalidResponse(){
        ResponseMod mod = new ResponseMod();
        mod.accepted = true;
        mod.text = "Olá, vou poder lhe ajudar.";
        return mod;
    }
}
