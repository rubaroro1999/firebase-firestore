package com.example.myapplication;

public class proudcts {


    private String id;
    private String name;
    private String code;

    private String price;
    private String desc;

    public proudcts(String name, String code, String price, String desc) {
        this.setName(name);
        this.setCode(code);
        this.setPrice(price);
        this.setDesc(desc);
    }

    public proudcts() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
}