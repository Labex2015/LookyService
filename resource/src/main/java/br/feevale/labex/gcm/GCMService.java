package br.feevale.labex.gcm;

import br.feevale.labex.gcm.utils.GCMVariables;
import br.feevale.labex.gcm.utils.MessageResponse;
import br.feevale.labex.gcm.vo.Content;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class GCMService {

    public MessageResponse requestGCM(String idUSer, Content content){
        content = createContent(idUSer, content);
        return POST2GCM.post(GCMVariables.apiKey, content);
    }

    public static Content createContent(String idUser, Content content){
        content.addRegId(idUser);
        return content;
    }
}
