package br.feevale.labex.gcm.vo;

import br.feevale.labex.gcm.utils.GCMVariables;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 0126128 on 30/06/2015.
 */
public class NotificationContent extends Content{

    protected Map<String, Object> data;

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(String notification) {
        if(this.data == null)
            this.data = new HashMap<String, Object>();

        this.data.put(BODY,notification);
        this.data.put(TYPE, GCMVariables.TYPE_REQUEST_HELP);
    }
}
