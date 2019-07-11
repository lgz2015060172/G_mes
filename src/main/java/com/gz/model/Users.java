package com.gz.model;

import lombok.Builder;

@Builder
public class Users {
    private Integer id;

    private String account;

    private String paaword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPaaword() {
        return paaword;
    }

    public void setPaaword(String paaword) {
        this.paaword = paaword == null ? null : paaword.trim();
    }
}