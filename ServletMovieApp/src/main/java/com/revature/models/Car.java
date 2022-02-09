package com.revature.models;
import annotations.Column;
import annotations.PrimaryKey;
public class Car {
    @PrimaryKey
    private int id;
    @Column
    private String make;
    @Column
    private String color;
    @Column
    private double price;


    public Car() {
    }

    public Car(String make, String color, double price) {
        this.make = make;
        this.color = color;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", make='" + make + '\'' +
                ", color='" + color + '\'' +
                ", price=" + price +
                '}';
    }
}
