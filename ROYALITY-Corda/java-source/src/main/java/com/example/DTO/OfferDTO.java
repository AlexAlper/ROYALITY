package com.example.DTO;


import net.corda.core.contracts.UniqueIdentifier;

public class OfferDTO {
    private UniqueIdentifier linearId;
    private String id;
    private String phone;
    private String city;
    private String name;

    public OfferDTO() {
    }

    public OfferDTO(String id, String phone, String city, String name){
        this.linearId = new UniqueIdentifier();
        this.id = id;
        this.phone = phone;
        this.city = city;
        this.name = name;

    }

    public OfferDTO(String id, String phone, String city, String name,  UniqueIdentifier linearId){
        this.linearId = linearId;
        this.id = id;
        this.phone = phone;
        this.city = city;
        this.name = name;
    }

    public UniqueIdentifier getLinearId() {
        return linearId;
    }

    public void setLinearId(UniqueIdentifier linearId) {
        this.linearId = linearId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName(){return  name; }

    public void setName(String name) {this.name = name;}



}
