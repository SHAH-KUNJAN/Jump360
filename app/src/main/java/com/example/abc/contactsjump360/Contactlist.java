package com.example.abc.contactsjump360;


import java.io.Serializable;

public class Contactlist implements Serializable{
    String username;
    String email;
    String address;
    String userno1;
    String userno2;

    public Contactlist(){

    }

    public Contactlist(String username, String email, String address, String userno1, String userno2) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.userno1 = userno1;
        this.userno2 = userno2;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getUserno1() {
        return userno1;
    }

    public String getUserno2() {
        return userno2;
    }
}
