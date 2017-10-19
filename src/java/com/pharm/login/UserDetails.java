/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pharm.login;

import java.io.Serializable;

/**
 *
 * @author Gold
 */
public class UserDetails implements Serializable {

    private int id;
    private String first_name;
    private String last_name;
    private String user_name;
    private String role_id;
    private String creatdBy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getCreatdBy() {
        return creatdBy;
    }

    public void setCreatdBy(String creatdBy) {
        this.creatdBy = creatdBy;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

}
