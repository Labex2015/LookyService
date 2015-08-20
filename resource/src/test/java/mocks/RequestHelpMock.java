package mocks;

import br.feevale.labex.controller.mod.RequestHelpMod;
import br.feevale.labex.model.RequestHelp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 0126128 on 03/06/2015.
 */
public class RequestHelpMock {

    public static br.feevale.labex.model.RequestHelp getRequestHelpActivePrivate(Long idUser, Long idUserHelper){
        return new br.feevale.labex.model.RequestHelp(UserAndAccountMocks.returnUserComplete(idUser),"Me ajude.", "java; ee; query; jpa",
                new Date(), UserAndAccountMocks.returnUserComplete(idUserHelper), RequestHelp.WAITING, br.feevale.labex.model.RequestHelp.PRIVATE);
    }

    public static br.feevale.labex.model.RequestHelp getRequestHelpClosedPrivate(Long idUser, Long idUserHelper){
        return new br.feevale.labex.model.RequestHelp(UserAndAccountMocks.returnUserComplete(idUser),"Me ajude.", "java; ee; query; jpa",
                new Date(), UserAndAccountMocks.returnUserComplete(idUserHelper), br.feevale.labex.model.RequestHelp.CLOSED, br.feevale.labex.model.RequestHelp.PRIVATE);
    }

    public static br.feevale.labex.model.RequestHelp getRequestHelpActiveGlobal(Long idUser, Long idUserHelper){
        return new br.feevale.labex.model.RequestHelp(UserAndAccountMocks.returnUserComplete(idUser),"Me ajude.", "java; ee; query; jpa",
                new Date(), UserAndAccountMocks.returnUserComplete(idUserHelper), br.feevale.labex.model.RequestHelp.ACCEPTED, br.feevale.labex.model.RequestHelp.GLOBAL);
    }

    public static List<br.feevale.labex.model.RequestHelp> getActiveRequestHelp(Long idUser, Long... idUsersAux){
        List<br.feevale.labex.model.RequestHelp> list = new ArrayList<>();
        for(Long id : idUsersAux){
            list.add(getRequestHelpActivePrivate(idUser, id));
        }
        return list;
    }

    public static RequestHelpMod getRequestHelpMod() {
        RequestHelpMod helpMod = new RequestHelpMod();
        helpMod.setParams("java;c++;programação III;desenvolvimento");
        helpMod.setText("his error seldom occurs in most Web traffic, particularlyhis error seldom occurs in most Web traffic, particularly");
        helpMod.setDate(new Date());
        return helpMod;
    }

    public static RequestHelpMod getRequestHelpModInvalid() {
        RequestHelpMod helpMod = new RequestHelpMod();
        helpMod.setParams("his error seldom occurs in most Web traffic, particularly");
        helpMod.setText("aes");
        helpMod.setDate(new Date());
        return helpMod;
    }
}
