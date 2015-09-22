package br.feevale.labex.controller.mod;

/**
 * Created by grimmjowjack on 9/20/15.
 */
public class GlobalHelpMod extends BaseMod<GlobalHelpMod>{

    public Long idUser;
    public Long idRequestHelp;
    public String title;
    public String description;
    public String params;


    public GlobalHelpMod() {}

    public GlobalHelpMod(Long idUser, Long idRequestHelp, String title, String description, String params) {
        this.idUser = idUser;
        this.idRequestHelp = idRequestHelp;
        this.title = title;
        this.description = description;
        this.params = params;
    }

}
