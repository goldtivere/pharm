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
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Gold
 */
@ManagedBean(name = "ivt")
@ViewScoped
public class AddInventory implements Serializable {

    private String tablename;
    private String desciption;
    private InventoryModel inventRole = new InventoryModel();
    private TableModel tabModel = new TableModel();
    private boolean makevisible;
    private String vendorId;
    private String item;
    private String category;
    private String invoiceId;
    private String amount;
    private String unitprice;
    private String quantity;
    private String messangerOfTruth;
    private ItemModel itemtable;
    private String type;
    private List<ItemModel> itemModel;
    private String itemtype;
    private Date startDate;
    private Date endDate;
    private String barcode;
    private String strFromDate;
    private String strToDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ItemModel getItemtable() {
        return itemtable;
    }

    public void setItemtable(ItemModel itemtable) {
        this.itemtable = itemtable;
    }

    public List<ItemModel> getItemModel() {
        return itemModel;
    }

    public void setItemModel(List<ItemModel> itemModel) {
        this.itemModel = itemModel;
    }

    public String getMessangerOfTruth() {
        return messangerOfTruth;
    }

    public void setMessangerOfTruth(String messangerOfTruth) {
        this.messangerOfTruth = messangerOfTruth;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(String unitprice) {
        this.unitprice = unitprice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public TableModel getTabModel() {
        return tabModel;
    }

    public void setTabModel(TableModel tabModel) {
        this.tabModel = tabModel;
    }

    public boolean isMakevisible() {
        return makevisible;
    }

    public void setMakevisible(boolean makevisible) {
        this.makevisible = makevisible;
    }

    @PostConstruct
    public void init() {
        try {
            itemModel = ItemTable();
            itemtable = new ItemModel();
            setAmount("");
            setUnitprice("");
            setQuantity("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public InventoryModel getInventRole() {
        return inventRole;
    }

    public void setInventRole(InventoryModel inventRole) {
        this.inventRole = inventRole;
    }

    public List<String> vendorTable() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            con = dbConnections.mySqlDBconnection();
            String query = "SELECT * FROM vendor_table where isdeleted=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, "true");
            rs = pstmt.executeQuery();
            //
            List<String> lst = new ArrayList<>();
            String vendorName;
            while (rs.next()) {

                vendorName = rs.getString("vendor_name");

                lst.add(vendorName);
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

    public List<String> vendorItem() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            con = dbConnections.mySqlDBconnection();
            String query = "SELECT type FROM vendortype";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            //
            List<String> lst = new ArrayList<>();
            String vendorName;
            while (rs.next()) {

                vendorName = rs.getString("type");

                lst.add(vendorName);
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

    public List<String> completVendor(String val) {
        List<String> com = new ArrayList();
        try {
            for (String value : vendorTable()) {
                if (value.toUpperCase().contains(val.toUpperCase())) {
                    com.add(value);
                }

            }
            return com;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    public List<String> completItem(String val) {
        List<String> com = new ArrayList();
        try {
            for (String value : vendorItem()) {
                if (value.toUpperCase().contains(val.toUpperCase())) {
                    com.add(value);
                }

            }
            return com;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

    public List<String> completOn(String val) {
        List<String> com = new ArrayList();
        try {
            for (String value : inventoryList()) {
                if (value.toUpperCase().contains(val.toUpperCase())) {
                    com.add(value);
                }

            }
            return com;
        } catch (Exception e) {
            e.printStackTrace();
            return null;

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

    public void makePanelVisible() {
        setMakevisible(true);

    }

    public void refresh() {
        setAmount("");
        setCategory("");
        setType("");
        setInvoiceId("");
        setVendorId("");
        setUnitprice("");
        setQuantity("");
        setItem("");
        setBarcode("");
        setStartDate(null);
        setEndDate(null);

    }

    public void refreshDialog() {
        setTablename("");
        setDesciption("");
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
            String myTableName = "CREATE TABLE " + tableName + "CAT" + " ("
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

                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Category Created", "");
                refreshDialog();

            } else {
                loggedIn = false;
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Category Already Exists", "");
                refreshDialog();
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

    //populates table
    public List<ItemModel> ItemTable() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            con = dbConnections.mySqlDBconnection();
            String query = "SELECT * FROM vendor_item order by id desc";
            pstmt = con.prepareStatement(query);

            rs = pstmt.executeQuery();
            //
            List<ItemModel> lst = new ArrayList<>();
            while (rs.next()) {

                ItemModel ven = new ItemModel();
                ven.setId(rs.getInt("id"));
                ven.setVendorId(rs.getString("vendor_id"));
                ven.setItem(rs.getString("item"));
                ven.setAmount(rs.getDouble("amount"));
                ven.setInvoiceId(rs.getString("invoice_id"));
                ven.setQuantity(rs.getDouble("quantity"));
                ven.setCategory(rs.getString("category"));
                ven.setUnitprice(rs.getDouble("unit_price"));
                ven.setCreatedBy(rs.getString("createdby"));
                ven.setDateCreated(rs.getDate("datecreated"));
                ven.setType(rs.getString("type"));
                ven.setStartDate(rs.getString("mndate"));
                ven.setEnddate(rs.getString("exdate"));
                ven.setBarcode(rs.getString("barcode"));

                lst.add(ven);
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

    public void insertItem() throws Exception {

        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FacesMessage msg;
        UserDetails userObj = (UserDetails) context.getExternalContext().getSessionMap().get("sessn_nums");
        String on = String.valueOf(userObj);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        if (userObj == null) {
            setMessangerOfTruth("Expired Session, please re-login" + on);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessangerOfTruth(), getMessangerOfTruth());
            context.addMessage(null, msg);
        }

        String createdby = String.valueOf(userObj.getFirst_name() + " " + userObj.getLast_name());
        String createdId = String.valueOf(userObj.getId());
        String roleId = String.valueOf(userObj.getRole_id());
        try {
            con = dbConnections.mySqlDBconnection();
            String vendorQ = "select count(*) as vendorQ from vendor_table where vendor_name=? ";
            pstmt = con.prepareStatement(vendorQ);

            pstmt.setString(1, getVendorId());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                int counts = rs.getInt("vendorQ");
                if (counts > 0) {

                    String vendorI = "select count(*) as vendorI from inventory_table where inventory_name=? ";
                    pstmt = con.prepareStatement(vendorI);

                    pstmt.setString(1, getCategory());

                    rs = pstmt.executeQuery();

                    while (rs.next()) {
                        int coun = rs.getInt("vendorI");
                        if (coun > 0) {

                            String vendorIs = "select count(*) as vendorIs from vendortype where type=? ";
                            pstmt = con.prepareStatement(vendorIs);
                            pstmt.setString(1, getType());
                            rs = pstmt.executeQuery();

                            while (rs.next()) {
                                int couns = rs.getInt("vendorIs");
                                if (couns > 0) {

                                    //test if invoice id exists
                                    String vencount = "SELECT count(invoice_id) as invoice FROM vendor_item where invoice_id=?";
                                    pstmt = con.prepareStatement(vencount);
                                    pstmt.setString(1, getInvoiceId());
                                    rs = pstmt.executeQuery();

                                    while (rs.next()) {
                                        int count = rs.getInt("invoice");
                                        if (count > 0) {

                                            setMessangerOfTruth("Invoice Id already Exists!!");
                                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                                            context.addMessage(null, msg);

                                        } else {

                                            String strFromDate = format.format(getStartDate());
                                            String strToDate = format.format(getEndDate());

                                            String insert = "insert into vendor_item (vendor_id,item,category,invoice_id,amount,unit_price,"
                                                    + "quantity,createdBy,role,datecreated,type,barcode,mndate,exdate) "
                                                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                                            pstmt = con.prepareStatement(insert);

                                            pstmt.setString(1, getVendorId());
                                            pstmt.setString(2, getItem());
                                            pstmt.setString(3, getCategory());
                                            pstmt.setString(4, getInvoiceId());
                                            pstmt.setDouble(5, Double.parseDouble(getAmount()));
                                            pstmt.setDouble(6, Double.parseDouble(getUnitprice()));
                                            pstmt.setDouble(7, Double.parseDouble(getQuantity()));
                                            pstmt.setString(8, createdby);
                                            pstmt.setString(9, roleId);
                                            pstmt.setString(10, DateManipulation.dateAndTime());
                                            pstmt.setString(11, getType());
                                            pstmt.setString(12, getBarcode());
                                            pstmt.setString(13, strFromDate);
                                            pstmt.setString(14, strToDate);
                                            pstmt.executeUpdate();

                                            setMessangerOfTruth("Item Added!!");
                                            itemModel = ItemTable();
                                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                                            context.addMessage(null, msg);
                                            refresh();
                                        }
                                    }

                                } else {
                                    setMessangerOfTruth("Item does not exist!!");

                                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                                    context.addMessage(null, msg);
                                }
                            }

                        } else {
                            setMessangerOfTruth("Category does not exist!!");

                            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                            context.addMessage(null, msg);
                        }
                    }

                } else {
                    setMessangerOfTruth("Vendor does not exist!!");

                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                    context.addMessage(null, msg);
                }
            }
        } catch (Exception e) {
            setMessangerOfTruth("Error!!");
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
            context.addMessage(null, msg);
            e.printStackTrace();

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

    public void inserttype(ActionEvent event) {

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

            UserDetails userObj = (UserDetails) ctx.getExternalContext().getSessionMap().get("sessn_nums");

            String insertTab = "Insert into vendortype (type,createdby,datecreated,roleid) value (?,?,?,?) ";
            pstmt = con.prepareStatement(insertTab);
            pstmt.setString(1, getType());
            pstmt.setString(2, userObj.getFirst_name() + " " + userObj.getLast_name());
            pstmt.setString(3, DateManipulation.dateAndTime());
            pstmt.setInt(4, userObj.getId());
            pstmt.executeUpdate();

            loggedIn = true;

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Vendor Item Created", "");
            refreshDialog();

            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void insertItems(ActionEvent event) {

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmts = null;
        ResultSet rs = null;

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;

        try {

            con = dbConnections.mySqlDBconnection();

            UserDetails userObj = (UserDetails) ctx.getExternalContext().getSessionMap().get("sessn_nums");

            String insertTab = "Insert into vendortype (type,createdby,datecreated,roleid) value (?,?,?,?) ";
            pstmt = con.prepareStatement(insertTab);
            pstmt.setString(1, getItemtype());
            pstmt.setString(2, userObj.getFirst_name() + " " + userObj.getLast_name());
            pstmt.setString(3, DateManipulation.dateAndTime());
            pstmt.setInt(4, userObj.getId());
            pstmt.executeUpdate();

            loggedIn = true;

            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Created", "");
            setItemtype("");

            FacesContext.getCurrentInstance().addMessage(null, message);
            context.addCallbackParam("loggedIn", loggedIn);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void onRowSelect(SelectEvent event) {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesMessage msg = null;
        String vendorIds=((ItemModel) event.getObject()).getVendorId();
                 setMessangerOfTruth("Vendor " + vendorIds + " deleted!!");
        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
        ctx.addMessage(null, msg);
        setItem(vendorIds);
    }

    public void onRowUnselect(UnselectEvent event) {
        System.out.println(" Vendor" + ((ItemModel) event.getObject()).getStartDate());
        FacesMessage msg = new FacesMessage("Car Unselected", ((ItemModel) event.getObject()).getVendorId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

}
