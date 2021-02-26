package com.mohammed.sellersapp.Model;

public class Usermodel {

    String Username;
    String Password;
    String Firstname;
    String Lastname;
    String Phonenumber;
    boolean isadmin;
    String key;


    public Usermodel(String username, String password, String firstname, String lastname, String phonenumber,boolean isadmin,String key) {
        Username = username;
        Password = password;
        Firstname = firstname;
        Lastname = lastname;
        Phonenumber = phonenumber;
        this.isadmin = isadmin;
        this.key = key;

    }
    public Usermodel(){

    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isIsadmin() {
        return isadmin;
    }

    public void setIsadmin(boolean isadmin) {
        this.isadmin = isadmin;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getPhonenumber() {
        return Phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        Phonenumber = phonenumber;
    }

    public String getfullname(){
        return Firstname+" "+Lastname;
    }
}
