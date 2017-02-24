package com.haramy.clico.model;

/**
 * Project: Clico.
 * Created by Dell on 10/07/2016.
 * Copyright (C) 2016 : Rami Hamrouni
 */
public class Deal {
    private int id;
    private String title;
    private String location;
    private String dateFrom;
    private String dateTo;

    public Deal() {
    }

    public Deal(String title, String location, String dateFrom , String dateTo) {
        this.title = title;
        this.location = location;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }
}
