package br.feevale.labex.controller.mod;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by grimmjowjack on 9/9/15.
 */
public class LoginMod extends BaseMod<LoginMod>{

    private String username;
    private String name;
    private Float latitude;
    private Float longitude;
    private String description;
    private String degree;
    private Long degreeID;
    private Integer semester;
    private String picturePath;
    private String deviceKey;

    private String accountID;
    private String token;

    @JsonIgnore
    private String email;

    public LoginMod() {
    }

    public LoginMod(String username, String name, Float latitude, Float longitude, String description,
                    String degree, Integer semester, String picturePath, String accountID) {
        this.username = username;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.degree = degree;
        this.semester = semester;
        this.picturePath = picturePath;
        this.accountID = accountID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getDegreeID() {
        return degreeID;
    }

    public void setDegreeID(Long degreeID) {
        this.degreeID = degreeID;
    }
}
