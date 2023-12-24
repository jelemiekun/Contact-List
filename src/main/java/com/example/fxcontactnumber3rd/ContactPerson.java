package com.example.fxcontactnumber3rd;

public class ContactPerson {
    private String name;
    private String number;
    private String address;
    private byte age;
    private String gender;

    public ContactPerson(String name, String number) {
        this.name = name;
        this.number = number;
        this.address = "";
        this.age = 0;
        this.gender = "N/A";
    }

    public ContactPerson(String name, String number, String addressOrGender, boolean isAddress) {
        this.name = name;
        this.number = number;

        if (isAddress) {
            this.address = addressOrGender;
            this.age = 0;
            this.gender = "N/A";
        } else {
            this.address = "";
            this.age = 0;
            this.gender = addressOrGender;

        }
    }

    public ContactPerson(String name, String number, byte age) {
        this.name = name;
        this.number = number;
        this.address = "";
        this.age = age;
        this.gender = "N/A";
    }

    public ContactPerson(String name, String number, String address, byte age) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.age = age;
        this.gender = "N/A";
    }

    public ContactPerson(String name, String number, byte age, String gender) {
        this.name = name;
        this.number = number;
        this.address = "";
        this.age = age;
        this.gender = gender;
    }

    public ContactPerson(String name, String number, String address, String gender) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.age = 0;
        this.gender = gender;
    }

    public ContactPerson(String name, String number, String address, byte age, String gender) {
        this.name = name;
        this.number = number;
        this.address = address;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
