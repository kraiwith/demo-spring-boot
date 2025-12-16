package com.krai.demo_spring_boot.models;

public class OrderModel {
    private Long id;
    private Long menuItemId;
    private Integer quantity;

    public OrderModel() {
    }

    public OrderModel(Long id, Long menuItemId, Integer quantity) {
        this.id = id;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
