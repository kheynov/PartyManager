package socket;

import database.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Response {
    protected enum Status {
        OK,
        ERROR,
        NOT_FOUND
    }
    private Status responseStatus;
    private Request.ACTION action;
    private int tagID;
    private Person person;

    public void setResponseStatus(Status responseStatus) {
        this.responseStatus = responseStatus;
    }

    public void setAction(Request.ACTION action) {
        this.action = action;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        object.put("action", action);
        object.put("tagID", tagID);
        object.put("status", responseStatus);
        if (person != null){
            JSONArray array = new JSONArray();
            array.add(person.getName());
            array.add(person.getBalance());
            object.put("person", array);
        }
        return object.toJSONString();
    }
}
