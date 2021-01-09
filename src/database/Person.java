package database;

public class Person {
    private int tagID;
    private String name;
    private int balance;
    private int rating;

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(int balance) {
        if ((balance - this.balance > 0)) {
            rating += balance - this.balance;
        }
        this.balance = balance;
    }

    public int getRating() {
        return rating;
    }

    public Person() {
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

    public Person(int tagID, String name, int balance, int rating) {
        this.tagID = tagID;
        this.name = name;
        this.balance = balance;
        this.rating = rating;
    }
}
