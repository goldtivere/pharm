/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pharm.rolemgt;

import com.pharm.dbconn.DbConnectionX;
import com.pharm.logic.AESencrp;
import com.pharm.logic.DateManipulation;
import com.pharm.login.UserDetails;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Gold
 */
@ManagedBean(name = "mgt")
@ViewScoped
public class UserMgt implements Serializable {

    private String first_name;
    private String last_name;
    private String username;
    private String pass;
    private String repass;
    private String role;
    private String messangerOfTruth;
    private List<RoleModel> rolelist;
    private String category;
    private String description;
    private RoleModel roledrop = new RoleModel();

    @PostConstruct
    public void init() {
        try {
            rolelist = roleModel();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void refresh() {
        first_name = null;
        last_name = null;
        username = null;
        pass = null;
        repass = null;
        roledrop.setRoleName(null);
    }

    public void insertData() throws Exception {

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        FacesContext ctx = FacesContext.getCurrentInstance();
        UserDetails userObj = (UserDetails) ctx.getExternalContext().getSessionMap().get("sessn_nums");
        FacesMessage msg;
        String on = String.valueOf(userObj);
        if (userObj == null) {
            setMessangerOfTruth("Expired Session, please re-login" + on);
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessangerOfTruth(), getMessangerOfTruth());
            ctx.addMessage(null, msg);
        }
        String createdby = String.valueOf(userObj.getId()) + " " + userObj.getFirst_name() + " " + userObj.getLast_name();
        try {
            con = dbConnections.mySqlDBconnection();
            String testAvaOfName = "SELECT count(*) as total FROM user_details where user_name=? or first_name=? and last_name=?";
            pstmt = con.prepareStatement(testAvaOfName);
            pstmt.setString(1, getUsername());
            pstmt.setString(2, getFirst_name());
            pstmt.setString(3, getLast_name());

            rs = pstmt.executeQuery();
            
            while(rs.next())
            {
                int count=rs.getInt("total");
            if (count== 0) {
                String insert = "insert into user_details (first_name,last_name,user_name,pass_word,role_id,date_created,date_time_created,"
                        + "created_by) "
                        + "values(?,?,?,?,?,?,?,?)";

                pstmt = con.prepareStatement(insert);
                String doEncPwd = AESencrp.encrypt(getPass());
                pstmt.setString(1, getFirst_name());
                pstmt.setString(2, getLast_name());
                pstmt.setString(3, getUsername());
                pstmt.setString(4, doEncPwd);
                pstmt.setString(5, roledrop.getRoleName());
                pstmt.setString(6, DateManipulation.dateAlone());
                pstmt.setString(7, DateManipulation.dateAndTime());
                pstmt.setString(8, createdby);

                pstmt.executeUpdate();

                setMessangerOfTruth("Operation Successful");
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, getMessangerOfTruth(), getMessangerOfTruth());
                ctx.addMessage(null, msg);
                refresh();

            } else {
                setMessangerOfTruth("First Name and Lastname or Username already exists!!");
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessangerOfTruth(), getMessangerOfTruth());
                ctx.addMessage(null, msg);
            }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            setMessangerOfTruth("Err:" + ex.getMessage() + " ,, contact the Developer(Gold on 08131248746)");
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessangerOfTruth(), getMessangerOfTruth());
            ctx.addMessage(null, msg);
        }
    }

    public List<RoleModel> roleModel() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();

        DbConnectionX dbConnections = new DbConnectionX();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            con = dbConnections.mySqlDBconnection();
            String query = "SELECT * FROM pharm_role";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();
            //
            List<RoleModel> lst = new ArrayList<>();
            while (rs.next()) {

                RoleModel modelrole = new RoleModel();
                modelrole.setRoleName(rs.getString("role_name"));
                modelrole.setWat_can_do(rs.getString("what_can_do"));
                //
                lst.add(modelrole);
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
  
    public RoleModel getRoledrop() {
        return roledrop;
    }

    public void setRoledrop(RoleModel roledrop) {
        this.roledrop = roledrop;
    }

    public List<RoleModel> getRolelist() {
        return rolelist;
    }

    public void setRolelist(List<RoleModel> rolelist) {
        this.rolelist = rolelist;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMessangerOfTruth() {
        return messangerOfTruth;
    }

    public void setMessangerOfTruth(String messangerOfTruth) {
        this.messangerOfTruth = messangerOfTruth;
    }

}
