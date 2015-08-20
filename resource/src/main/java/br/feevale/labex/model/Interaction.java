package br.feevale.labex.model;

import br.feevale.labex.utils.MD5Hashing;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 0126128 on 28/05/2015.
 */
@Entity(name = "interaction")
public class Interaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name="request_help_id")
    private RequestHelp requestHelp;

    @Column(nullable = false, length = 98)
    private String chatRoom;

    @Column(nullable = false)
    private Boolean open;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date started;

    @Temporal(TemporalType.TIMESTAMP)
    private Date closed;

    @OneToOne(mappedBy="interaction")
    private Evaluation evaluation;

    public Interaction() {
    }

    public Interaction(RequestHelp request) {
        this.requestHelp = request;
        if(this.id == null || this.id == 0){
            this.started = new Date();
            this.open = true;
            String param = this.requestHelp.getRequester().getUsername().replace(" ","").trim().concat("_"
                    .concat(this.requestHelp.getHelper().getUsername().replace(" ","").trim().concat(
                            new SimpleDateFormat("ddMMyyy").format(new Date()))));
            try {
                this.chatRoom = MD5Hashing.generateHash(param);
            } catch (Exception e) {
                this.chatRoom = param;
            }
        }
    }

    public Long getId() {
        if(id == null)
            this.id = 0L;
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public RequestHelp getRequestHelp() {
        return requestHelp;
    }

    public void setRequestHelp(RequestHelp requestHelp) {
        this.requestHelp = requestHelp;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
