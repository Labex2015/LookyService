package controller;

import br.feevale.labex.Application;
import br.feevale.labex.model.Account;
import br.feevale.labex.model.AccountType;
import br.feevale.labex.model.User;
import br.feevale.labex.service.InteractionService;
import br.feevale.labex.service.user.UserService;
import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static com.jayway.restassured.RestAssured.when;

/**
 * Created by 0126128 on 01/06/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:9999")
public class UserControllerIT {

    @Inject
    private UserService service;

    @Inject
    private InteractionService interactionService;


    @Value("${local.server.port}")
    private int serverPort;

    @Before
    public void setUp(){
        service.save(getDefaultUser());
        service.save(getDefaultUser2());
        RestAssured.port = serverPort;
    }

    @Test
    public void loadProfileUserNotFound(){
        when().get("/user/{id}/profile", 2656559565656556L)
                .then().statusCode(HttpStatus.SC_NO_CONTENT);

    }

    @Test
    public void loadProfileUserFound(){
        when().get("/user/{id}/profile", 1L)
                .then().statusCode(HttpStatus.SC_OK);

    }

    //TODO;
    public void loadUserHelperByInteractionId(){

    }

    private User getDefaultUser(){
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

        AccountType accountType = new AccountType("GOOGLE");
        service.saveAccountType(accountType);
        Account account = new Account("bond007@gmail.com", accountType);
        service.saveAccount(account);
        user.setAccount(account);
        return user;
    }

    private User getDefaultUser2(){
        User user = new User();
        user.setName("Jason Bourne");
        user.setEmail("bourne@threadstone.com");
        user.setDegree(null);
        user.setSemester(7);
        user.setDeviceKey("213100awdwad2320120dwadwad44334_dawdaw323434__0dwad");
        user.setDescription("Vodka, poker e mulheres.");
        user.setLatitude(42.712202F);
        user.setLongitude(19.359772F);
        user.setUsername("bourne");

        AccountType accountType = new AccountType("GOOGLE");
        service.saveAccountType(accountType);
        Account account = new Account("bourne@gmail.com", accountType);
        service.saveAccount(account);
        user.setAccount(account);
        return user;
    }


}
