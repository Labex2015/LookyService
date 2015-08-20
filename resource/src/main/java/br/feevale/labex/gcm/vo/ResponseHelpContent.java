package br.feevale.labex.gcm.vo;

import br.feevale.labex.gcm.utils.GCMVariables;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by PabloGilvan on 19/12/2014.
 */
public class ResponseHelpContent extends Content{

    protected Map<String, Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(ResponseHelp body) {
        if(this.data == null)
            this.data = new HashMap<String, Object>();

        this.data.put(BODY,body);
        this.data.put(TYPE, GCMVariables.TYPE_RESPONSE_HELP);
    }
}
