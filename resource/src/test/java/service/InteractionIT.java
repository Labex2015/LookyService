package service;

import br.feevale.labex.model.RequestHelp;
import br.feevale.labex.model.User;
import br.feevale.labex.service.InteractionService;
import br.feevale.labex.service.RequestHelpService;
import br.feevale.labex.service.user.UserService;
import com.jayway.restassured.RestAssured;
import mocks.MockUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Inject;

/**
 * Created by 0126128 on 08/06/2015.
 */

/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:9999")*/
public class InteractionIT {


    private User jamesBond;
    private User jasonBourne;

    @Inject
    private UserService service;

    @Inject
    private InteractionService interactionService;

    @Inject
    private RequestHelpService helpService;

    @Value("${local.server.port}")
    private int serverPort;

    //@Before
    public void setUp(){
        jamesBond = service.save(getUserProfile("jamesbond@mi6.com","bond","James Bond"));
        jasonBourne = service.save(getUserProfile("jasonbourne@treadstone.com", "bourne", "Jason Bourne"));


        RestAssured.port = serverPort;
    }


    public User getUserProfile(String email, String username, String name){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setDegree(null);
        user.setSemester(7);
        user.setDeviceKey("213100awdwad2320120dwadwad44334_dawdaw323434__0dwad");
        user.setDescription("Vodka, poker e mulheres.");
        user.setLatitude(42.712202F);
        user.setLongitude(19.359772F);
        user.setUsername(username);
        return user;
    }

    public RequestHelp getUserProfile(User request, User helper){
        RequestHelp help = new RequestHelp(request, helper, "Pedido de ajuda","fugir;threadstone;perseguição", MockUtils.getTime(05,06,2015));
        return help;
    }
}
