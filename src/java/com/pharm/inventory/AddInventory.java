/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pharm.inventory;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.pharm.dbconn.DbConnectionX;
import com.pharm.logic.DateManipulation;
import com.pharm.login.UserDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Gold
 */
@ManagedBean(name = "ivt")
public class AddInventory {

    private String tablename;
    private String desciption;
    private InventoryModel inventRole = new InventoryModel();
    private boolean makevisible;
   
    public boolean isMakevisible() {
        return makevisible;
    }

    public void setMakevisible(boolean makevisible) {
        this.makevisible = makevisible;
    }
    
    @PostConstruct
    public void init(){
        setMakevisible(false);
       
      
    }

    public InventoryModel getInventRole() {
        return inventRole;
    }

    public void setInventRole(InventoryModel inventRole) {
        this.inventRole = inventRole;
    }

    public List<String> inventoryList() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            con = dbConnections.mySqlDBconnection();
            String query = "SELECT Inventory_name FROM inventory_table";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            //
            List<String> lst = new ArrayList<>();
            String inventoryList;
            while (rs.next()) {

                inventoryList = rs.getString("Inventory_name");
                lst.add(inventoryList);

            }

            return lst;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {

            if (!(con == null)) {
                con.close();
                con = null;
            }
            if (!(pstmt == null)) {
                pstmt.close();
                pstmt = null;
            }

        }
    }

    public boolean testCategoryExist(String category) {
        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage msg = null;
        try {
            con = dbConnections.mySqlDBconnection();
            String query = "SELECT * FROM inventory_table where Inventory_name=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, category);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return false;

            }
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public void makePanelVisible(){
        setMakevisible(true);
       
    }

    public void refresh() {
        setTablename(null);
        setDesciption(null);
    }

    public void insert(ActionEvent event) {

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmts = null;
        ResultSet rs = null;

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;

        String tableName = tablename;
        tableName = tableName.replaceAll("\\s", "_");

        try {

            con = dbConnections.mySqlDBconnection();
            String myTableName = "CREATE TABLE " + tableName + " ("
                    + "`Id` INT NOT NULL,"
                    + "`Item` LONGTEXT NULL,"
                    + "`Quantity` INT NULL,"
                    + "`Description` LONGTEXT NULL,"
                    + "`UnitPrice` DOUBLE NULL,"
                    + "`Amount` DOUBLE NULL,"
                    + "`VendorId` NVARCHAR(100) NULL,"
                    + "`InvoiceId` NVARCHAR(100) NULL,"
                    + "`date_created` DATE NULL,"
                    + "`date_time_created` DATETIME NULL,"
                    + "`Created_by` NVARCHAR(200) NULL,"
                    + "`Role_id` INT NULL,"
                    + "PRIMARY KEY (`Id`))";

            UserDetails userObj = (UserDetails) ctx.getExternalContext().getSessionMap().get("sessn_nums");
            if (testCategoryExist(tablename)) {
                pstmt = con.prepareStatement(myTableName);
                pstmt.executeUpdate();

                String insertTab = "Insert into Inventory_table (Inventory_name,table_name,description,date_created,time_created,created_by,role_id) value (?,?,?,?,?,?,?) ";
                pstmt = con.prepareStatement(insertTab);
                pstmt.setString(1, tablename);
                pstmt.setString(2, tableName);
                pstmt.setString(3, desciption);
                pstmt.setString(4, DateManipulation.dateAlone());
                pstmt.setString(5, DateManipulation.dateAndTime());
                pstmt.setString(6, userObj.getFirst_name() + " " + userObj.getLast_name());
                pstmt.setInt(7, userObj.getId());
                pstmt.executeUpdate();

                loggedIn = true;

                message = new FacesMessage(FacesMessage.SEVERITY_INFO, tablename, "Category Created");
                refresh();

            } else {
                loggedIn = false;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Category Already Exists", "");
                refresh();
            }

            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
        } catch (MySQLSyntaxErrorException ex) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Table Already Exists", "");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

}
