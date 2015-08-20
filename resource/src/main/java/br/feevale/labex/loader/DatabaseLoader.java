package br.feevale.labex.loader;

import br.feevale.labex.model.*;
import br.feevale.labex.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grimmjowjack on 8/18/15.
 */
@Service
public class DatabaseLoader {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AreaRepository areaRepository;
    private final DegreeRepository degreeRepository;
    private final KnowledgeRepository knowledgeRepository;

    @Inject
    public DatabaseLoader(UserRepository userRepository, AccountRepository accountRepository,
                          AreaRepository areaRepository, DegreeRepository degreeRepository,
                          KnowledgeRepository knowledgeRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.areaRepository = areaRepository;
        this.degreeRepository = degreeRepository;
        this.knowledgeRepository = knowledgeRepository;
    }

    @PostConstruct
    public void generateUsers() {
        generateAreas();
        if (userRepository.findAll().isEmpty()) {

            Degree ciencia = new Degree();
            ciencia.setName("Ciência da Computação");
            ciencia.setId(1L);
            //degreeRepository.saveAndFlush(ciencia);

            Degree sistemas = new Degree();
            sistemas.setName("Sistemas de Informação");
            sistemas.setId(2L);
            //degreeRepository.saveAndFlush(sistemas);

            AccountType accountType = new AccountType();
            accountType.setId(1L);

            Account account = new Account();
            account.setAccountType(accountType);
            account.setAccount("pablogilvan@gmail.com");
            account.setAppStatus(true);
            account.setProfileStatus(true);
            account.setToken("Qqweq2232#@ad22##_adawdaw$%$ädawdwawad");
            accountRepository.saveAndFlush(account);

            Account account2 = new Account();
            account2.setAccountType(accountType);
            account2.setAccount("wollmann.cassio@gmail.com");
            account2.setAppStatus(true);
            account2.setProfileStatus(true);
            account2.setToken("Qqweawdw343444232#@ad22##_adawdaw$%$ädawdwawad");
            accountRepository.saveAndFlush(account2);

            User pablo = new User();
            pablo.setAccount(account);
            pablo.setEmail("pablogilvan@gmail.com");
            pablo.setLatitude(0f);
            pablo.setLongitude(0f);
            pablo.setName("Pablo Gilvan Borges");
            pablo.setSemester(4);
            pablo.setUsername("pablogilvan");
            pablo.setDegree(ciencia);
            userRepository.saveAndFlush(pablo);


            User cassio = new User();
            cassio.setAccount(account2);
            cassio.setEmail("wollman.cassio@gmail.com");
            cassio.setLatitude(0f);
            cassio.setLongitude(0f);
            cassio.setName("Cassio Wollman");
            cassio.setSemester(7);
            cassio.setUsername("cassioAnaknsunamonn");
            cassio.setDegree(sistemas);
            userRepository.saveAndFlush(cassio);

            List<Knowledge> knowledges = new ArrayList<>();
            knowledges.add(new Knowledge(pablo, new Area(1L, "")));
            knowledges.add(new Knowledge(pablo, new Area(3L, "")));

            knowledges.add(new Knowledge(cassio, new Area(1L, "")));
            knowledges.add(new Knowledge(cassio, new Area(3L, "")));

            knowledgeRepository.save(knowledges);
        }
    }

    public void generateAreas() {

        List<Area> areas = areaRepository.findAll();
        if (areas.isEmpty()) {
            areas.add(new Area("Java"));
            areas.add(new Area("Java EE"));
            areas.add(new Area("NodeJS"));
            areas.add(new Area("MySQL"));
            areas.add(new Area("Android"));
            areas.add(new Area("PHP"));
            areas.add(new Area("JavaScript"));
            areas.add(new Area("Cálculo I"));
            areas.add(new Area("Cálculo II"));

            areaRepository.save(areas);
        }
    }

}