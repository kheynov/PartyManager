package database;

public class Person {
    private int tagID;
    private String name;
    private int balance;

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getTagID() {
        return tagID;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public Person(int tagID, String name, int balance) {
        this.tagID = tagID;
        this.name = name;
        this.balance = balance;
    }
}
