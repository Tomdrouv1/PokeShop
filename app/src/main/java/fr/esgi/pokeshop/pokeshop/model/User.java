package fr.esgi.pokeshop.pokeshop.model;

public class User {

    private String username;

    private String lastname;

    private String email;

    private String token;

    public User(String username, String lastname, String email, String token) {
        this.username = username;
        this.lastname = lastname;
        this.email = email;
        this.token = token;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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
}
