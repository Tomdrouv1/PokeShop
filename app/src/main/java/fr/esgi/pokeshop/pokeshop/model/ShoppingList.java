package fr.esgi.pokeshop.pokeshop.model;

import java.util.Date;

public class ShoppingList {

    private Integer id;

    private String name;

    private Date created_date;

    private Boolean completed;

    public ShoppingList(Integer id, String name, Date created_date, Boolean completed) {
        this.id = id;
        this.name = name;
        this.created_date = created_date;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
