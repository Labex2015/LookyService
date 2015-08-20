package br.feevale.labex.repository;

import br.feevale.labex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor{

    public static final String USER_BY_INTERACTION = "Select u.* from user as u where u.id"+
            " = (SELECT CASE " +
            " WHEN rh.helper_id != :id" +
            " THEN rh.helper_id"+
            " ELSE rh.requester_id"+
            " END as ID"+
            " FROM interaction as i inner join request_help as rh on i.request_help_id = rh.id"+
            " where i.id = :idInteraction)";

    public static final String USER_PROFILE = "select u.id, u.username, d.name, u.latitude, " +
            "       u.longitude, u.picture_path, u.semester, "+
            "       (select count(id) from request_help where requester_id = :idUser) as requester, "+
            "       (select count(id) from request_help where helper_id = :idUser) as helper, "+
            "       (select count(id) from evaluation where interaction_id in "+
            "                                         (select id from interaction where request_help_id in "+
            "                                                                     (select id from request_help where helper_id = :idUser))) as evaluations, "+
            "       ifnull((select sum(answer_points) from evaluation where interaction_id in "+
            "                                         (select id from interaction where request_help_id in " +
            "                                                                     (select id from request_help where helper_id = :idUser))),0) as answerPoints, "+
            "       ifnull((select sum(help_points) from evaluation where interaction_id in "+
            "                                         (select id from interaction where request_help_id in "+
            "                                                                     (select id from request_help where helper_id = :idUser))),0) as helpPoints"+
            " from user as u left join degree as d on u.degree_id = d.id where u.id = :idUser";


    public static final String SEARCH_HELP = "select u.*, d.name as degree " +
                                             " from user as u left join degree as d on u.degree_id = d.id " +
                                             " inner join knowledge as k on u.id = k.user_id" +
                                             " inner join area as a on a.id = k.area_id" +
                                             " where upper(a.name) like upper(:param) ";
    public static final String SEARCH_HELP_AND = "and k.subject_id = :idSubject";

    public static final String SEARCH_HELP_END = " group by u.id LIMIT :init,:max";



    @Query(nativeQuery = true, value = USER_BY_INTERACTION)
    User findUserByInteraction(@Param("idInteraction") Long idInteraction,@Param("id")  Long id);



    @Query(nativeQuery = true, value = USER_PROFILE)
    List<Object[]> loadProfile(@Param("idUser") Long idUser);

    @Query(nativeQuery = true, value = SEARCH_HELP+SEARCH_HELP_END)
    List<User> searchUsersToHelp(@Param("param") String param,
                                 @Param("init") int init, @Param("max") int max);

    @Query(nativeQuery = true, value = SEARCH_HELP+SEARCH_HELP_AND+SEARCH_HELP_END)
    List<User> searchUsersToHelp(@Param("param") String param, @Param("idSubject") Long idSubject,
                                 @Param("init") int init, @Param("max") int max);
}