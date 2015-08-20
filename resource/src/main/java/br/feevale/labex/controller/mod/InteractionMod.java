package br.feevale.labex.controller.mod;

import br.feevale.labex.model.Interaction;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by 0126128 on 03/06/2015.
 */
public class InteractionMod {

    private Long id;
    private RequestHelpMod requestHelp;
    private String chatRoom;
    private Boolean open;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date started;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Date closed;

    public InteractionMod() {}

    public InteractionMod(Interaction interaction) {
        setId(interaction.getId());
        setRequestHelp(new RequestHelpMod(interaction.getRequestHelp()));
        setChatRoom(interaction.getChatRoom());
        setOpen(interaction.getOpen());
        setStarted(interaction.getStarted());
        setClosed(interaction.getClosed());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestHelpMod getRequestHelp() {
        return requestHelp;
    }

    public void setRequestHelp(RequestHelpMod requestHelp) {
        this.requestHelp = requestHelp;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getClosed() {
        return closed;
    }

    public void setClosed(Date closed) {
        this.closed = closed;
    }
}
