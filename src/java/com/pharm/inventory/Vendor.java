/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pharm.inventory;

import com.pharm.dbconn.DbConnectionX;
import com.pharm.logic.AESencrp;
import com.pharm.logic.DateManipulation;
import com.pharm.login.UserDetails;
import com.pharm.rolemgt.RoleModel;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableColumn.CellEditEvent;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Gold
 */
@ManagedBean(name = "bank")
@ViewScoped
public class Vendor implements Serializable {

    private List<BankModel> bankmodel;
    private BankModel bankmode = new BankModel();
    private String messangerOfTruth;
    private String vendor_name;
    private String vendor_address;
    private String vendor_phone;
    private String vendor_email;
    private String vendor_bank;
    private String vendor_acct;
    private Boolean poss;
    private Boolean status;
    private TableModel selectedList;
    private List<TableModel> selectList;
    private List<TableModel> vendorList;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public TableModel getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(TableModel selectedList) {
        this.selectedList = selectedList;
    }

    public List<TableModel> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<TableModel> selectList) {
        this.selectList = selectList;
    }

    public List<TableModel> getVendorList() {
        return vendorList;
    }

    public void setVendorList(List<TableModel> vendorList) {
        this.vendorList = vendorList;
    }

    public Boolean getPoss() {
        return poss;
    }

    public void setPoss(Boolean poss) {
        this.poss = poss;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public String getVendor_address() {
        return vendor_address;
    }

    public void setVendor_address(String vendor_address) {
        this.vendor_address = vendor_address;
    }

    public String getVendor_phone() {
        return vendor_phone;
    }

    public void setVendor_phone(String vendor_phone) {
        this.vendor_phone = vendor_phone;
    }

    public String getVendor_email() {
        return vendor_email;
    }

    public void setVendor_email(String vendor_email) {
        this.vendor_email = vendor_email;
    }

    public String getVendor_bank() {
        return vendor_bank;
    }

    public void setVendor_bank(String vendor_bank) {
        this.vendor_bank = vendor_bank;
    }

    public String getVendor_acct() {
        return vendor_acct;
    }

    public void setVendor_acct(String vendor_acct) {
        this.vendor_acct = vendor_acct;
    }

    public String getMessangerOfTruth() {
        return messangerOfTruth;
    }

    public void setMessangerOfTruth(String messangerOfTruth) {
        this.messangerOfTruth = messangerOfTruth;
    }

    public List<BankModel> getBankmodel() {
        return bankmodel;
    }

    public void setBankmodel(List<BankModel> bankmodel) {
        this.bankmodel = bankmodel;
    }

    public BankModel getBankmode() {
        return bankmode;
    }

    public void setBankmode(BankModel bankmode) {
        this.bankmode = bankmode;
    }

    @PostConstruct
    public void init() {
        try {
            vendorList = vendorTable();
            bankmodel = bankModels();
            status = detectStatus();
            poss = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //detect the rolePriviledge of the logged in user
    public Boolean detectStatus() {
        FacesContext context = FacesContext.getCurrentInstance();
        UserDetails userObj = (UserDetails) context.getExternalContext().getSessionMap().get("sessn_nums");
        String stat = String.valueOf(userObj.getRole_id());

        return stat.equalsIgnoreCase("admin");
    }

    public List<BankModel> bankModels() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            con = dbConnections.mySqlDBconnection();
            String query = "SELECT * FROM bank_table";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            //
            List<BankModel> lst = new ArrayList<>();
            while (rs.next()) {

                BankModel bank = new BankModel();
                bank.setId(rs.getInt("Id"));
                bank.setBankName(rs.getString("bank_name"));
                //
                lst.add(bank);
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

    //refreshes vendor registration form
    public void refresh() {
        setVendor_name("");
        setVendor_phone("");
        setVendor_email("");
        setVendor_bank("");
        setVendor_address("");
        setVendor_acct("");
        bankmode.setBankName("");

    }

    //insert vendor's details
    public void insertData() throws Exception {

        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FacesMessage msg;
        UserDetails userObj = (UserDetails) context.getExternalContext().getSessionMap().get("sessn_nums");
        String on = String.valueOf(userObj);

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

            // test to see if vendor is already registered
            String testVenName = "Select * from vendor_table where vendor_name=? and isdeleted=?";
            pstmt = con.prepareStatement(testVenName);
            pstmt.setString(1, getVendor_name());
            pstmt.setBoolean(2, false);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                setMessangerOfTruth("Vendor Name Aleady exists!!");
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                context.addMessage(null, msg);

            } else {

                String vencount = "SELECT count(id) as total FROM vendor_table";
                pstmt = con.prepareStatement(vencount);

                rs = pstmt.executeQuery(vencount);

                while (rs.next()) {
                    int count = rs.getInt("total");
                    if (count == 0) {
                        int vendor_id = count + 1;
                        String vendor = "VEN" + String.valueOf(vendor_id);
                        String insert = "insert into vendor_table (vendor_id,vendor_name,vendor_phone,vendor_email,vendor_contact,vendor_bank,vendor_acct_num,"
                                + "created_by,role_id,date_created,date_time_created,Role,updatedBy,isdeleted) "
                                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                        pstmt = con.prepareStatement(insert);

                        pstmt.setString(1, vendor);
                        pstmt.setString(2, getVendor_name());
                        pstmt.setString(3, getVendor_phone());
                        pstmt.setString(4, getVendor_email());
                        pstmt.setString(5, getVendor_address());
                        pstmt.setString(6, bankmode.getBankName());
                        pstmt.setString(7, getVendor_acct());
                        pstmt.setString(8, createdby);
                        pstmt.setString(9, roleId);
                        pstmt.setString(10, DateManipulation.dateAlone());
                        pstmt.setString(11, DateManipulation.dateAndTime());
                        pstmt.setString(12, createdId);
                        pstmt.setString(13, createdby);
                        pstmt.setBoolean(14, false);

                        pstmt.executeUpdate();

                        setMessangerOfTruth("Vendor Created!!");
                        vendorList = vendorTable();
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                        context.addMessage(null, msg);
                        refresh();

                    } else {
                        int vendor_id = count + 1;
                        String vendor = "VEN" + String.valueOf(vendor_id);
                        String insert = "insert into vendor_table (vendor_id,vendor_name,vendor_phone,vendor_email,vendor_contact,vendor_bank,vendor_acct_num,"
                                + "created_by,role_id,date_created,date_time_created,Role,updatedBy,isdeleted) "
                                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                        pstmt = con.prepareStatement(insert);

                        pstmt.setString(1, vendor);
                        pstmt.setString(2, getVendor_name());
                        pstmt.setString(3, getVendor_phone());
                        pstmt.setString(4, getVendor_email());
                        pstmt.setString(5, getVendor_address());
                        pstmt.setString(6, bankmode.getBankName());
                        pstmt.setString(7, getVendor_acct());
                        pstmt.setString(8, createdby);
                        pstmt.setString(9, roleId);
                        pstmt.setString(10, DateManipulation.dateAlone());
                        pstmt.setString(11, DateManipulation.dateAndTime());
                        pstmt.setString(12, createdId);
                        pstmt.setString(13, createdby);
                        pstmt.setBoolean(14, false);

                        pstmt.executeUpdate();

                        setMessangerOfTruth("Vendor Created!!");
                        vendorList = vendorTable();
                        msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                        context.addMessage(null, msg);
                        refresh();

                    }
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

    //populates table
    public List<TableModel> vendorTable() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            con = dbConnections.mySqlDBconnection();
            String query = "SELECT * FROM vendor_table where isdeleted=? order by id desc";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, "true");
            rs = pstmt.executeQuery();
            //
            List<TableModel> lst = new ArrayList<>();
            while (rs.next()) {

                TableModel ven = new TableModel();
                ven.setId(rs.getInt("id"));
                ven.setVendorId(rs.getString("vendor_id"));
                ven.setVendorName(rs.getString("vendor_name"));
                ven.setVendorPnum(rs.getString("vendor_phone"));
                ven.setVendorEmail(rs.getString("vendor_email"));
                ven.setVendorContact(rs.getString("vendor_contact"));
                ven.setVendorBank(rs.getString("vendor_bank"));
                ven.setVendorAcct(rs.getString("vendor_acct_num"));
                ven.setCreatedy(rs.getString("created_by"));
                ven.setDateCreated(rs.getDate("date_created"));

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

    public void onRowEdit(RowEditEvent event) throws Exception {

        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FacesMessage msg;
        UserDetails userObj = (UserDetails) context.getExternalContext().getSessionMap().get("sessn_nums");
        String on = String.valueOf(userObj);

        if (userObj == null) {
            setMessangerOfTruth("Expired Session, please re-login" + on);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessangerOfTruth(), getMessangerOfTruth());
            context.addMessage(null, msg);
        }
        String createdby = String.valueOf(userObj.getId() + " " + userObj.getFirst_name() + " " + userObj.getLast_name());
        String createdId = String.valueOf(userObj.getId());
        String roleId = String.valueOf(userObj.getRole_id());

        try {
            con = dbConnections.mySqlDBconnection();

            String testname = "select vendor_Id,count(vendor_name) as total from pharm_db.vendor_table where vendor_name=? and isdeleted=?";
            pstmt = con.prepareStatement(testname);
            pstmt.setString(1, ((TableModel) event.getObject()).getVendorName());
            pstmt.setBoolean(2, false);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int count = rs.getInt("total");
                String venId = rs.getString("vendor_Id");

                if (count <= 1 && venId == null) {
                    String update = "update pharm_db.vendor_table set vendor_name=?,vendor_phone=?,"
                            + "vendor_email=?,vendor_contact=?,"
                            + "vendor_bank=?,vendor_acct_num=?,Role=?,updatedBy=?,DateUpdated=? where vendor_Id=?";

                    pstmt = con.prepareStatement(update);

                    pstmt.setString(1, ((TableModel) event.getObject()).getVendorName());
                    pstmt.setString(2, ((TableModel) event.getObject()).getVendorPnum());
                    pstmt.setString(3, ((TableModel) event.getObject()).getVendorEmail());
                    pstmt.setString(4, ((TableModel) event.getObject()).getVendorContact());
                    pstmt.setString(5, ((TableModel) event.getObject()).getVendorBank());
                    pstmt.setString(6, ((TableModel) event.getObject()).getVendorAcct());
                    pstmt.setString(7, roleId);
                    pstmt.setString(8, createdby);
                    pstmt.setString(9, DateManipulation.dateAndTime());
                    pstmt.setString(10, ((TableModel) event.getObject()).getVendorId());

                    pstmt.executeUpdate();

                    setMessangerOfTruth("Vendor " + ((TableModel) event.getObject()).getVendorId() + " Updated!!");
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                    context.addMessage(null, msg);
                    refresh();
                } else if (count <= 1 && venId.equals(((TableModel) event.getObject()).getVendorId())) {
                    String update = "update pharm_db.vendor_table set vendor_name=?,vendor_phone=?,"
                            + "vendor_email=?,vendor_contact=?,"
                            + "vendor_bank=?,vendor_acct_num=?,Role=?,updatedBy=?,DateUpdated=? where vendor_Id=?";

                    pstmt = con.prepareStatement(update);

                    pstmt.setString(1, ((TableModel) event.getObject()).getVendorName());
                    pstmt.setString(2, ((TableModel) event.getObject()).getVendorPnum());
                    pstmt.setString(3, ((TableModel) event.getObject()).getVendorEmail());
                    pstmt.setString(4, ((TableModel) event.getObject()).getVendorContact());
                    pstmt.setString(5, ((TableModel) event.getObject()).getVendorBank());
                    pstmt.setString(6, ((TableModel) event.getObject()).getVendorAcct());
                    pstmt.setString(7, roleId);
                    pstmt.setString(8, createdby);
                    pstmt.setString(9, DateManipulation.dateAndTime());
                    pstmt.setString(10, ((TableModel) event.getObject()).getVendorId());

                    pstmt.executeUpdate();

                    setMessangerOfTruth("Vendor " + ((TableModel) event.getObject()).getVendorId() + " Updated!!");
                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                    context.addMessage(null, msg);
                    refresh();

                } else {
                    setMessangerOfTruth("Vendor Name Aleady exists!!");

                    msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                    context.addMessage(null, msg);
                }
            }
        } catch (Exception e) {
            setMessangerOfTruth("Error!!");
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, String.valueOf(e), String.valueOf(e));
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

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((TableModel) event.getObject()).getVendorId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void deleteCar() throws Exception {

        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        FacesMessage msg;
        UserDetails userObj = (UserDetails) context.getExternalContext().getSessionMap().get("sessn_nums");
        String on = String.valueOf(userObj);

        if (userObj == null) {
            setMessangerOfTruth("Expired Session, please re-login" + on);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessangerOfTruth(), getMessangerOfTruth());
            context.addMessage(null, msg);
        }

        try {
            con = dbConnections.mySqlDBconnection();

            // delete registered vendor
            String Vendelete = "update vendor_table set isDeleted=? where vendor_id=?";
            pstmt = con.prepareStatement(Vendelete);
            pstmt.setBoolean(1, true);
            pstmt.setString(2, selectedList.getVendorId());
            pstmt.executeUpdate();
            vendorList = vendorTable();
            setMessangerOfTruth("Vendor " + selectedList.getVendorId() + " deleted!!");
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
            context.addMessage(null, msg);

        } catch (Exception e) {
            setMessangerOfTruth("Error!!");
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, String.valueOf(e), String.valueOf(e));
            context.addMessage(null, msg);
            e.printStackTrace();

        }
    }

}
