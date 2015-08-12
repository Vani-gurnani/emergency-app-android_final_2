package com.example.sunnygurnani.multimenu;

/**
 * Created by sunnygurnani on 7/7/15.
 */
public class Contact {

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name_user1) {
        this.firstName = name_user1;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String contactnumber1) {
        this.phoneNumber = contactnumber1;
    }

    private String firstName;
    private String phoneNumber;

}