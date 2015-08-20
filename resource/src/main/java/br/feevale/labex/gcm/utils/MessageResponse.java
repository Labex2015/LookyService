package br.feevale.labex.gcm.utils;

/**
 * Created by 0126128 on 15/12/2014.
 */
public class MessageResponse {

    private String message;
    private Boolean status;

    public MessageResponse(String message, Boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
