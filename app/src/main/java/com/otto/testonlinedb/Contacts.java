package com.otto.testonlinedb;

/**
 * Created by 212026992 on 8/29/2016.
 */
public class Contacts {
    private String name, email, mobile;

    public Contacts(String name, String email, String mobile)
    {
        this.setName(name);
        this.setEmail(email);
        this.setMobile(mobile);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
