package br.feevale.labex.controller;

import br.feevale.labex.gcm.GCMService;
import br.feevale.labex.gcm.vo.Content;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by 0126128 on 02/07/2015.
 */
public abstract class BaseController {



    @Async
    protected void sendNotification(String userKey, Content content){
        new Thread(new Runnable() {
            @Override
            public void run() {
                new GCMService().requestGCM(userKey, content);
            }
        }).run();

    }
}
