package br.feevale.labex.model;

/**
 * Created by grimmjowjack on 8/17/15.
 */
public class SearchItem {

    public String param;
    public Long subject;
    public int position;
    public int max;

    public SearchItem() { }

    public SearchItem(String param, Long subject, int position, int max) {
        this.param = param;
        this.subject = subject;
        this.position = position;
        this.max = max;
    }
}
