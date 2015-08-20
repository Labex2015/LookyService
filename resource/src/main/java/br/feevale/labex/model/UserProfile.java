package br.feevale.labex.model;

import java.math.BigInteger;

/**
 * Created by grimmjowjack on 8/11/15.
 */

public class UserProfile {

    public BigInteger id;
    public String username;
    public String degree;
    public Float latitude;
    public Float longitude;
    public String picturePath;
    public int semester;
    public int requester;
    public int helper;
    public int evaluations;
    public int answerPoints;
    public int helpPoints;

    public UserProfile() {}

    public UserProfile(BigInteger id, String username, String degree, Float latitude, Float longitude,
                       String picturePath, int semester, int requester, int helper,
                       int evaluations, int answerPoints, int helpPoints) {
        this.id = id;
        this.username = username;
        this.degree = degree;
        this.latitude = latitude;
        this.longitude = longitude;
        this.picturePath = picturePath;
        this.semester = semester;
        this.requester = requester;
        this.helper = helper;
        this.evaluations = evaluations;
        this.answerPoints = answerPoints;
        this.helpPoints = helpPoints;
    }
}

