package com.example.kursovaya_igdb.model;

public class InvolvedCompany {
    private Company company;
    public InvolvedCompany(Company company) {
        this.company = company;
    }
    InvolvedCompany(){

    }
    public Company getCompany() {
        return company;
    }
}
