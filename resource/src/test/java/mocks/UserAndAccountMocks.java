package mocks;

import br.feevale.labex.controller.data.KnowledgesData;
import br.feevale.labex.controller.mod.Profile;
import br.feevale.labex.controller.mod.UserMod;
import br.feevale.labex.model.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 0126128 on 02/06/2015.
 */
public class UserAndAccountMocks {

    public static Account returnAccountWithId(Long id){
        Account account = new Account("bond007@gmail.com", returnAccountTypeWithId(1L));
        account.setId(id);
        return account;
    }

    public static AccountType returnAccountTypeWithId(Long id){
        AccountType accountType = new AccountType("GOOGLE");
        accountType.setId(id);
        return accountType;
    }

    public static User returnUserSimple(){
        User user = new User();
        user.setName("James Bond");
        user.setEmail("james_bond@mi6.com");
        user.setDegree(null);
        user.setSemester(7);
        user.setDeviceKey("213100awdwad2320120dwadwad44334_dawdaw323434__0dwad");
        user.setDescription("Vodka, poker e mulheres.");
        user.setLatitude(42.712202F);
        user.setLongitude(19.359772F);
        user.setUsername("bond007");
        return user;
    }

    public static User returnUserComplete(Long id){
        User user = returnUserSimple();
        user.setId(id);
        user.setAccount(returnAccountWithId(++id));
        return user;
    }

    public static UserProfile returnUserProfile(Long id){
        UserProfile profile = new UserProfile();
        User user = returnUserComplete(id);
        profile.semester = user.getSemester();
        profile.picturePath = user.getPicturePath();
        profile.id = BigInteger.valueOf(id);
        profile.helpPoints = 0;
        profile.evaluations = 0;
        profile.answerPoints = 0;
        profile.degree = user.getDegree() != null ? user.getDegree().getName() : null;
        profile.helper = 0;
        profile.latitude = 355.232323232f;
        profile.longitude = 324.42242424f;
        profile.username = user.getUsername();

        return profile;
    }

    public static Profile returnProfile(Long id, List<KnowledgesData> knowledgesParam,
                                        List<Evaluation> evalParam){

        return new Profile(returnUserProfile(1L),
                knowledgesParam != null ? knowledgesParam : new ArrayList<>(),
                evalParam != null ? evalParam : new ArrayList<>());
    }


    public static List<UserMod> returnUserModList() {
        List<UserMod> list = new ArrayList<>();
        list.add(new UserMod(returnUserComplete(1L)));
        list.add(new UserMod(returnUserComplete(3L)));
        list.add(new UserMod(returnUserComplete(2L)));
        return list;
    }

    public static List<User> returnUserList() {
        List<User> users = new ArrayList<>();
        users.add(returnUserComplete(1L));
        users.add(returnUserComplete(3L));
        users.add(returnUserComplete(2L));
        return users;
    }
}
