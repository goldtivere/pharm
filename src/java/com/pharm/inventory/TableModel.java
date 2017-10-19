/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pharm.inventory;

import java.util.Date;

/**
 *
 * @author Gold
 */
public class TableModel {

    private int id;
    private String vendorId;
    private String vendorName;
    private String vendorPnum;
    private String vendorEmail;
    private String vendorContact;
    private String vendorBank;
    private String vendorAcct;
    private String createdy;
    private Date dateCreated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorPnum() {
        return vendorPnum;
    }

    public void setVendorPnum(String vendorPnum) {
        this.vendorPnum = vendorPnum;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getVendorContact() {
        return vendorContact;
    }

    public void setVendorContact(String vendorContact) {
        this.vendorContact = vendorContact;
    }

    public String getVendorBank() {
        return vendorBank;
    }

    public void setVendorBank(String vendorBank) {
        this.vendorBank = vendorBank;
    }

    public String getVendorAcct() {
        return vendorAcct;
    }

    public void setVendorAcct(String vendorAcct) {
        this.vendorAcct = vendorAcct;
    }

    public String getCreatedy() {
        return createdy;
    }

    public void setCreatedy(String createdy) {
        this.createdy = createdy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
