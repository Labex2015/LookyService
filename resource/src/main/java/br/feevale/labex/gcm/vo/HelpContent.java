package br.feevale.labex.gcm.vo;

import br.feevale.labex.controller.mod.RequestHelpMod;
import br.feevale.labex.gcm.utils.GCMVariables;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class HelpContent extends Content {

    protected Map<String, Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(RequestHelpMod body) {
        if(this.data == null)
            this.data = new HashMap<String, Object>();

        this.data.put(BODY,body);
        this.data.put(TYPE, GCMVariables.TYPE_REQUEST_HELP);
    }
}
