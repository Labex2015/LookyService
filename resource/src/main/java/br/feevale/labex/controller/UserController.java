package br.feevale.labex.controller;


import br.feevale.labex.controller.data.KnowledgesData;
import br.feevale.labex.controller.mod.*;
import br.feevale.labex.controller.response.FacebookResponse;
import br.feevale.labex.controller.response.FacebookRootResponse;
import br.feevale.labex.controller.utils.APIDoc;
import br.feevale.labex.exceptions.InvalidFieldException;
import br.feevale.labex.exceptions.UnauthorizedAccessException;
import br.feevale.labex.gcm.vo.NotificationContent;
import br.feevale.labex.model.*;
import br.feevale.labex.service.InteractionService;
import br.feevale.labex.service.KnowledgeService;
import br.feevale.labex.service.user.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.pojo.ApiVisibility;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@Api(description = APIDoc.DESC_USERCONTROLLER,
        name = "UserController",
        group = APIDoc.GROUP_USER,
        visibility = ApiVisibility.PUBLIC)
@RestController
public class UserController extends BaseController{

    private static final String CLIENT_ID = "1079617689354-csllf39rm4htea8r03vvcgm99e904j4u.apps.googleusercontent.com";
    private static final String ANDROID_CLIENT_ID = "1079617689354-cdrmdhe6i88vvqedaq3ggcov65c9pcia.apps.googleusercontent.com";
    private static final String APPS_DOMAIN_NAME = "";


    private static final String FACEBOOK_APP_ID = "1639860942950097";
    private static final String FACEBOOK_APP_SECRET = "b6b9dfe8fd8317146129a63dda07692b";

    private final UserService service;
    private final InteractionService interactionService;
    private final KnowledgeService knowledgeService;

    private final Logger log = Logger.getLogger(getClass().getName());

    @Inject
    public UserController(UserService service, InteractionService serviceInteractions,
                          KnowledgeService knowledgeService) {
        this.service = service;
        this.interactionService = serviceInteractions;
        this.knowledgeService = knowledgeService;
    }

    @RequestMapping(value = "/user/{id}/profile/private", method = RequestMethod.GET)
    public HttpEntity loadProfile(@PathVariable Long id){
            if(id == 0)
                return new ResponseEntity(String.format("Parâmetro inválido. => %s", id),HttpStatus.UNPROCESSABLE_ENTITY);

            UserMod userMod = service.getProfile(id);
            if(userMod != null && userMod.getId() > 0)
                return new ResponseEntity(userMod, HttpStatus.OK);
            else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/user/{id}/profile", method = RequestMethod.GET)
    public HttpEntity loadPublicProfile(@PathVariable Long id){
        try {
            if (id == 0)
                return new ResponseEntity(String.format("Parâmetro inválido. => %s", id), HttpStatus.UNPROCESSABLE_ENTITY);

            UserProfile profile = service.getPublicProfile(id);
            if (profile != null) {
                List<KnowledgesData> list = knowledgeService.findKnowledgesByUser(id);
                List<Evaluation> evaluations = interactionService.getEvaluationsForOne(id);
                Profile profileComplete = new Profile(profile, list, evaluations);

                return new ResponseEntity(profileComplete, HttpStatus.OK);
            } else
                return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/{idUser}/interactions", method = RequestMethod.GET)
    public HttpEntity getActiveInteractions(@PathVariable Long idUser) {
        if(idUser == 0)
            return new ResponseEntity(String.format("Parâmetro inválido-> %s", idUser),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        List<Interaction> interactions = interactionService.findActiveInteractions(idUser);
        if(interactions.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return processInteractionsReturn(interactions);
    }

    @RequestMapping(value = "/user/{idUser}/interaction/{idInteraction}", method = RequestMethod.PUT)
    public HttpEntity closeInteraction(@PathVariable Long id, @PathVariable Long idInteraction,
                                       @RequestBody EvaluationMod evaluationMod) {
        if(id == 0 || idInteraction == 0)
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);

        Interaction interaction = interactionService.findById(idInteraction);
        if(interaction == null)
            return new ResponseEntity(String.format("Nenhuma interação localizada para o " +
                    "ID %s", idInteraction), HttpStatus.NO_CONTENT);

        User helper = service.getUserHelperInteraction(idInteraction);

        if(helper.getId() != id){
            if(evaluationMod == null)
                return new ResponseEntity("Você deve enviar uma avaliação.", HttpStatus.UNPROCESSABLE_ENTITY);
            try {
                EvaluationMod.validateEvaluation(evaluationMod);
            }catch (InvalidFieldException e){
                return new ResponseEntity(String.format("Parâmetro %s inválido, verificar requisitos.",
                        e.getField()), HttpStatus.UNPROCESSABLE_ENTITY);
            }
        }

        User userToNotify = service.getUserByInteraction(idInteraction, id);
        sendNotification(userToNotify.getDeviceKey(),getNotification(userToNotify));


        return new ResponseEntity(interactionService.closeInteraction(interaction, evaluationMod) ? HttpStatus.OK :
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiMethod(produces = APIDoc.PROD_CONSU_JSON,
               consumes = APIDoc.PROD_CONSU_JSON,
               summary = APIDoc.SUM_SEARCH_HELP)
    @ApiBodyObject(clazz = SearchItem.class)
    @RequestMapping(value = "/user/{idUser}/help/search:{param}/{subject}/{position}/{max}", method = RequestMethod.GET)
    public HttpEntity searchHelp(@PathVariable Long idUser,@PathVariable String param,@PathVariable Long subject,
                                 @PathVariable Integer position, @PathVariable Integer max) {

        SearchItem search = new SearchItem(param,subject,position,max);

        if(search.param.isEmpty())
            return new ResponseEntity("Parâmetro de busca inválido.",
                    HttpStatus.UNPROCESSABLE_ENTITY);

        List<UserMod> resultList = service.searchUsersToHelp(search.param, search.position, search.max,
                                                             search.subject, idUser);
        if(resultList.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        return new ResponseEntity(resultList, HttpStatus.OK);
    }


    @RequestMapping(value = "/user/{idUser}/position", method = RequestMethod.PUT)
    public HttpEntity updateUserPosition(@PathVariable(value = "idUser")Long idUser,
                                         @RequestBody PositionMod position){
        try{
            position.validateMe(position);
            User user = service.findById(idUser);
            if(user == null)
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            user.setLongitude(position.longitude);
            user.setLatitude(position.latitude);
            service.save(user);

            return new ResponseEntity(HttpStatus.OK);
        }catch (InvalidFieldException e){
            return new ResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

//    (53 07 80 12 36 43 36 86    -    057)
    @RequestMapping(value = "/user/signin/google", method = RequestMethod.POST)
    public HttpEntity login(@RequestBody LoginMod loginMod){
        try{
            loginMod.validateMe(loginMod);
            String userId = validateGoogleId(loginMod);
            loginMod.setAccountID(userId);
            User user = service.login(loginMod, AccountType.GOOGLE);
            if(user != null){
                return new ResponseEntity(new UserMod(user), HttpStatus.OK);
            }
            return new ResponseEntity("Problemas ao salvar dados.", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch(Exception e){
            e.printStackTrace();
            if(e instanceof InvalidFieldException)
                return new ResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            else if(e instanceof UnauthorizedAccessException)
                return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);

            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/user/signin/facebook", method = RequestMethod.POST)
    public HttpEntity loginFacebook(@RequestBody LoginMod loginMod){
        try{
            loginMod.validateMe(loginMod);
            String token = validateFacebook(loginMod);
            loginMod.setToken(token);
            User user = service.login(loginMod, AccountType.FACEBOOK);
            if(user != null){
                return new ResponseEntity(new UserMod(user), HttpStatus.OK);
            }
            return new ResponseEntity("Problemas ao salvar dados.", HttpStatus.INTERNAL_SERVER_ERROR);

        }catch(Exception e){
            e.printStackTrace();
            if(e instanceof InvalidFieldException)
                return new ResponseEntity(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
            else if(e instanceof UnauthorizedAccessException)
                return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);

            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String validateFacebook(LoginMod loginMod) {
        String url = "https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id="+FACEBOOK_APP_ID+
                     "&client_secret="+FACEBOOK_APP_SECRET+"&fb_exchange_token="+loginMod.getToken();
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url,String.class);
//        String token = response.substring(5,response.indexOf("&"));
//        return token;
        return response;
    }

    private HttpEntity processInteractionsReturn(List<Interaction> interactions) {
        List<InteractionMod> mods = new ArrayList<InteractionMod>();
        for(Interaction i : interactions)
            mods.add(new InteractionMod(i));

        return new ResponseEntity<List<InteractionMod>>(mods, HttpStatus.OK);
    }

    private NotificationContent getNotification(User user){
        NotificationContent content = new NotificationContent();
        content.setData(String.format("O usuário %s encerrou a interação entre vocês.", user.getUsername()));

        return content;
    }


    private String validateGoogleId(LoginMod loginMod){

        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory mJFactory = new JacksonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, mJFactory)
                .setAudience(Arrays.asList(CLIENT_ID))
                .build();

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(loginMod.getToken());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            if (payload != null && payload.getEmailVerified()) {
                loginMod.setEmail(payload.getEmail());
                return payload.getSubject();
            } else {
                throw  new UnauthorizedAccessException("Invalid ID token.");
            }
        } else {
            throw  new UnauthorizedAccessException("Invalid ID token.");
        }
    }


}
