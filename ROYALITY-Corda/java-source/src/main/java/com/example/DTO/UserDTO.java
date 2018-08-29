package com.example.DTO;

import net.corda.core.contracts.UniqueIdentifier;

public class UserDTO {
    private UniqueIdentifier linearId;
    private String cor_id;
    private String cor_name;
    private  String phone;
    private  String cor_bill_1;
    private String cor_bill_2;
    private String city;

    public UserDTO() {
    }

    public UserDTO(String cor_id, String cor_name, String phone, String cor_bill_1, String cor_bill_2, String city){
        this.linearId = new UniqueIdentifier();
        this.cor_id = cor_id;
        this.cor_name = cor_name;
        this.phone = phone;
        this.cor_bill_1 = cor_bill_1;
        this.cor_bill_2 = cor_bill_2;
        this.city = city;
    }

    public UserDTO(String cor_id, String cor_name, String phone, String cor_bill_1, String cor_bill_2, String city, UniqueIdentifier linearId){
        this.linearId = linearId;
        this.cor_id = cor_id;
        this.cor_name = cor_name;
        this.phone = phone;
        this.cor_bill_1 = cor_bill_1;
        this.cor_bill_2 = cor_bill_2;
        this.city = city;

    }

    public UniqueIdentifier getLinearId() {
        return linearId;
    }

    public void setLinearId(UniqueIdentifier linearId) {
        this.linearId = linearId;
    }

    public String getCor_id() {
        return cor_id;
    }

    public void setCor_id(String cor_id) {
        this.cor_id = cor_id;
    }

    public String getCor_name() { return cor_name; }

    public void setCor_name(String cor_name) {
        this.cor_name = cor_name;
    }

    public String getPhone() { return phone; }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCor_bill_1() { return cor_bill_1; }

    public void setCor_bill_1(String cor_bill_1) {
        this.cor_bill_1 = cor_bill_1;
    }

    public String getCor_bill_2() { return cor_bill_2; }

    public void setCor_bill_2(String cor_bill_2) {
        this.cor_bill_2 = cor_bill_2;
    }

    public String getCity() {return city;}

    public void setCity(String city){this.city = city;}

}
