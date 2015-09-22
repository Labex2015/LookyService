package br.feevale.labex.controller.utils;

/**
 * Created by grimmjowjack on 8/21/15.
 */
public class APIDoc {

    public static final String PROD_CONSU_JSON = "application/json";


    /**
     * KNOWLEDGE API DOCUMENTATION.
     **/
    public static final String DESC_KNOWLEDGE = "Api para listagem de: áreas de conhecimento, disciplinas e cursos." +
            "Adicionar conhecimentos a um dado usuário, listar conhecimentos por usuário e remover.";
    public static final String GROUP_KNOWLEDGE = "KnowledgeMod, Area, Subjects";

    public static final String SUM_SAVE_KNOWLEDGE = "Adiciona conhecimentos baseado no id de um dado user e de uma dada área.";
    public static final String BODY_SAVE_KNOWLEDGE = "{}";

    public static final String SUM_LIST_AREAS = "Listagem de áreas de conhecimentos já cadastradas por outros usuários.";

    public static final String SUM_LIST_SUBJECTS = "Listagem de disciplinas cadastradas, jutamente com seus respectivos cursos.";

    public static final String SUM_DELETE_KNOWLEDGE = "Remove um conhecimento baseado no id do usuário e no id da área.";
    public static final String STATUS_DELETE_KNOWLEDGE = "304 - Caso os dados sejam inválidos, 200 - Se o processo ocorreu em fluxo normal.";

    public static final String SUM_LIST_KNOWLEDGE = "Lista os conhecimentos de um dado usuário.";
    public static final String STATUS_LIST_KNOWLEDGE = "200 - Retorna a lista de conhecimentos; 204 - Nenhum dado disponível, não retorna nada.";

    // ### OBJECTS ###
    public static final String DESC_MOD_KNOWLEDGE = "Objeto usado para transitar dados de conhecimento.";

    public static final String SUM_RESURCES_KNOWLEDGE = "Recursos para cadastro de conhecimento.";
    public static final String STATUS_LIST_KNOWLEDGE_RESOURCE = "200- Lista com valores; 204 - Sem conteúdo";

    /**
     * USER API DOCUMENTATION.
     **/

    public static final String DESC_USERCONTROLLER = "Api para: Listagem de perfil de usuário; Busca de usuários;" +
            "Edição de dados de usuário; Listagem de interações; Iniciar interação; Fechar interação;";
    public static final String GROUP_USER = "Users, Profiles, Searches, Interactions";

    public static final String SUM_SEARCH_HELP = "Através dos parâmetros informados irá efetuar a busca de usuários com " +
            "conhecimentos na área(s) informada(s) e que estejam, além de próximos do usuário, ativos.";

    // ### OBJECTS ###
    public static final String DESC_MOD_SEARCHHELP = "Objeto usado para transitar dados de uma busca de ajuda.";




    /**
     * INTERACTION API DOCUMENTATION.
     **/

}
