package br.feevale.labex.gcm.vo;

import br.feevale.labex.model.User;

/**
 * Created by PabloGilvan on 19/12/2014.
 */
public class ResponseHelp {

    public String message;
    public Boolean status;
    public User user;

    public ResponseHelp(String message, Boolean status, User user) {
        this.message = message;
        this.status = status;
        this.user = user;
    }

    public ResponseHelp() {}
}
