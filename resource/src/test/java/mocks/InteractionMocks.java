package mocks;

import br.feevale.labex.model.Interaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 0126128 on 03/06/2015.
 */
public class InteractionMocks {

    public static Interaction getActiveInteraction(Long idUserRequest, Long idUserHelper){
        return new Interaction(RequestHelpMock.getRequestHelpActivePrivate(idUserRequest, idUserHelper));
    }

    public static Interaction getClosedInteraction(Long idUserRequest, Long idUserHelper){
        Interaction interaction = new Interaction(RequestHelpMock.getRequestHelpActivePrivate(idUserRequest, idUserHelper));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015,30,05);
        interaction.setClosed(calendar.getTime());
        interaction.setOpen(false);
        return interaction;
    }

    public static List<Interaction> getActiveInteractionList(Long idUserRequest, Long... idUserHelpers){
        List<Interaction> interactions = new ArrayList<>();
        for(Long id : idUserHelpers)
            interactions.add(new Interaction(RequestHelpMock.getRequestHelpActivePrivate(idUserRequest, id)));

        return interactions;
    }
}
