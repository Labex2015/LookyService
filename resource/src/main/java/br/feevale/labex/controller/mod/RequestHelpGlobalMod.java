package br.feevale.labex.controller.mod;

/**
 * Created by grimmjowjack on 9/21/15.
 */
public class RequestHelpGlobalMod {

    public Long idRequestHelp;
    public String title;
    public String description;
    public String params;
    public UserMod user;

    public RequestHelpGlobalMod(Long id, String title, String description, String params) {
        this.idRequestHelp = id;
        this.title = title;
        this.description = description;
        this.params = params;
    }

    public RequestHelpGlobalMod(Long idRequestHelp, String title, String description, String params, UserMod user) {
        this(idRequestHelp,title,description,params);
        this.user = user;
    }


}
