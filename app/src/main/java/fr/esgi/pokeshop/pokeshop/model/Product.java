package fr.esgi.pokeshop.pokeshop.model;

public class Product {

    private Integer id;

    private Integer shoppingListId;

    private String name;

    private Integer quantity;

    private Double price;

    public Product(Integer id, String name, Integer quantity, Double price, Integer shoppingListId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.shoppingListId = shoppingListId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(Integer shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
