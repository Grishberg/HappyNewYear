package com.github.grishberg.hny.backend;

public class Employee {
    private String name;
    private Group group;
    private String giftNumber;
    private String password;
    private String myGift;

    public Employee(String name, Group group, String password) {
        super();
        this.name = name;
        this.group = group;
        this.password = password;
    }

    public Employee() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " giftNumber = " + giftNumber;
    }

    public String getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(int giftNumber) {
        this.giftNumber = String.valueOf(giftNumber);
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getPassword() {
        return password;
    }

    public String getMyGift() {
        return myGift;
    }

    public void setMyGift(String myGift) {
        this.myGift = myGift;
    }
}
