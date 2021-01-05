package socket;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class Request {
    protected enum ACTION {
        ADD_USER,
        EDIT_BALANCE,
        EDIT_NAME,
        DELETE_USER,
        GET_USER
    }

    private ACTION action;
    private String name;
    private int tagID;
    private int balance;

    public ACTION getAction() {
        return action;
    }

    public String getName() {
        return name;
    }

    public int getTagID() {
        return tagID;
    }

    public int getBalance() {
        return balance;
    }

    public Request(String message){
        System.out.println(message);
        parseRequest(message);
    }

    public void parseRequest(String message) {
        try {
            JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(message);
            action = ACTION.valueOf(jsonObject.get("action").toString());
            switch (action){
                case ADD_USER -> {
                    name = jsonObject.get("name").toString();
                    tagID = Integer.parseInt((String) jsonObject.get("tagID"));
                    balance = Integer.parseInt((String) jsonObject.get("balance"));
                }
                case EDIT_NAME -> {
                    tagID = Integer.parseInt((String) jsonObject.get("tagID"));
                    name = jsonObject.get("name").toString();
                }
                case EDIT_BALANCE -> {
                    tagID = Integer.parseInt((String) jsonObject.get("tagID"));
                    balance = Integer.parseInt((String) jsonObject.get("balance"));
                }
                case DELETE_USER, GET_USER -> tagID = Integer.parseInt((String) jsonObject.get("tagID"));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
