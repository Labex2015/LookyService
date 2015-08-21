package br.feevale.labex.utils;

/**
 * Created by grimmjowjack on 8/20/15.
 */
public class Utils {


    public static boolean verifyParamEmptyOrNull(Object param){

        if(param == null)
            return false;

        if (param instanceof String) {
            if(((String) param).isEmpty())
                return false;
        } else if(param instanceof Long) {
            if((Long) param == 0)
                return false;
        } else if(param instanceof Integer) {
            if((Integer) param == 0)
                return false;
        }
        return true;
    }
}
