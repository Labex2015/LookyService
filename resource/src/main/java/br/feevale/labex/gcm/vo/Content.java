package br.feevale.labex.gcm.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class Content implements Serializable{
    @JsonIgnore
    @Transient
    public static final String BODY = "body";
    @JsonIgnore
    @Transient
    public static final String TYPE = "type";

    protected List<String> registration_ids;

    public void addRegId(String regId){
        if(registration_ids == null)
            registration_ids = new LinkedList<String>();
        registration_ids.add(regId);
    }

    public List<String> getRegistration_ids() {
        return registration_ids;
    }

    public void setRegistration_ids(List<String> registration_ids) {
        this.registration_ids = registration_ids;
    }

}
