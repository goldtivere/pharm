/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pharm.inventory;

import com.pharm.dbconn.DbConnectionX;
import com.pharm.logic.DateManipulation;
import com.pharm.login.UserDetails;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Gold
 */
@ManagedBean(name = "item")
@ViewScoped
public class itemTable implements Serializable {

    private ItemModel itemModel;
    private List<ItemModel> itemMode;
    private String messangerOfTruth;
    
    @PostConstruct
    public void init() {
        try {
          itemModel= new ItemModel();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<ItemModel> getItemMode() {
        return itemMode;
    }

    public void setItemMode(List<ItemModel> itemMode) {
        this.itemMode = itemMode;
    }

    public String getMessangerOfTruth() {
        return messangerOfTruth;
    }

    public void setMessangerOfTruth(String messangerOfTruth) {
        this.messangerOfTruth = messangerOfTruth;
    }

    public ItemModel getItemModel() {
        return itemModel;
    }

    public void setItemModel(ItemModel itemModel) {
        this.itemModel = itemModel;
    }

    public void editValue() {

        try {
            AddInventory invent = new AddInventory();
            ItemModel mode = new ItemModel();

            invent.setVendorId(mode.getVendorId());
            invent.setItem(mode.getItem());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

   
}
