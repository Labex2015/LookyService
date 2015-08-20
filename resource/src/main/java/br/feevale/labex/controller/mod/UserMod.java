package br.feevale.labex.controller.mod;

import br.feevale.labex.model.User;

/**
 * Created by 0126128 on 01/06/2015.
 */
public class UserMod {

    private Long id;
    private String username;
    private String name;
    private Float latitude;
    private Float longitude;
    private String description;
    private String degree;
    private Integer semester;
    private String picturePath;
    private byte[] picture;

    public UserMod() {
    }

    public UserMod(User user) {
        loadUserMod(user);
    }

    private void loadUserMod(User user) {
        setId(user.getId());
        setUsername(user.getUsername());
        setName(user.getName());
        setLongitude(user.getLongitude());
        setLatitude(user.getLatitude());
        setDescription(user.getDescription());
        setSemester(user.getSemester());
        setPicturePath(user.getPicturePath());

        if(user.getDegree() != null)
            setDegree(user.getDegree().getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
