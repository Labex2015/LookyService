package br.feevale.labex.controller.mod;

import br.feevale.labex.controller.data.KnowledgesData;
import br.feevale.labex.model.Evaluation;
import br.feevale.labex.model.UserProfile;

import java.util.List;

/**
 * Created by grimmjowjack on 8/14/15.
 */
public class Profile extends UserProfile{

    public List<KnowledgesData> knowledges;
    public List<Evaluation> evaluations;

    public Profile() {}

    public Profile(UserProfile userProfile, List<KnowledgesData> knowledges, List<Evaluation> evaluations1) {
        super(userProfile.id, userProfile.username, userProfile.degree, userProfile.latitude, userProfile.longitude,
                userProfile.picturePath, userProfile.semester, userProfile.requester, userProfile.helper,
                userProfile.evaluations, userProfile.answerPoints, userProfile.helpPoints);
        this.knowledges = knowledges;
        this.evaluations = evaluations1;
    }
}
