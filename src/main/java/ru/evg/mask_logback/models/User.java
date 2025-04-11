package ru.evg.mask_logback.models;

public class User {
    private String name;
    private String login;
    private String post;
    private String series;
    private String number;

    public User() {
    }

    public User(String name, String login, String post, String series, String number) {
        this.name = name;
        this.login = login;
        this.post = post;
        this.series = series;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
