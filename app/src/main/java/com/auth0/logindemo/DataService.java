package com.auth0.logindemo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by Matt Rowe 11/20/2016. Don't mind the mess.
 */

public class DataService {
    public ArrayList<Entry> list;

    //Constructors
    public DataService() {
        this.list = new ArrayList<Entry>();
    }

    public DataService(int size) {
        this.list = new ArrayList<Entry>(size);
    }

    //ref to ArrayList
    void add(Entry in) {
        this.list.add(in);
    }

    //Returns card type
    int displayType(int index) {
        String seen = this.list.get(index).seen;
        String status = this.list.get(index).status;
        if (status.equals("u")) {
            return 0;
        } else if (status.equals("a")) {
            return 2;
        } else {
            return 1;
        }
    }

    int size() {
        return this.list.size();
    }

    //Returns Entry at index
    Entry get(int index) {
        return this.list.get(index);
    }

    //Generic AF Import
    void importX(String in) {
        JsonObject jsonObject = new JsonParser().parse(in).getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        Entry next = new Entry();
        next.title = jsonObject.get("title").getAsString();
        next.inviter_name = jsonObject.get("inviter_name").getAsString();
        next.inviter_id = jsonObject.get("inviter_id").getAsString();
        next.invitee_name = jsonObject.get("invitee_name").getAsString();
        next.invitee_id = jsonObject.get("invitee_id").getAsString();
        next.timestamp = jsonObject.get("timestamp").getAsString();
        next.description = jsonObject.get("description").getAsString();
        next.start_time = jsonObject.get("start_time").getAsString();
        next.end_time = jsonObject.get("end_time").getAsString();
        next.seen = jsonObject.get("seen").getAsString();
        next.status= jsonObject.get("status").getAsString();
        this.list.add(next);
    }

    //Event style import
    void importEvent(String in) {
        JsonObject jsonObject = new JsonParser().parse(in).getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        Entry next = new Entry();
        next.title = jsonObject.get("name").getAsString();
        next.inviter_name = jsonObject.get("inviter_name").getAsString();
        next.inviter_id = jsonObject.get("inviter_id").getAsString();
        next.invitee_name = jsonObject.get("invitee_name").getAsString();
        next.invitee_id = jsonObject.get("invitee_id").getAsString();
        next.timestamp = jsonObject.get("timestamp").getAsString();
        next.description = jsonObject.get("description").getAsString();
        next.start_time = jsonObject.get("start_time").getAsString();
        next.end_time = jsonObject.get("end_time").getAsString();
        next.seen = jsonObject.get("seen").getAsString();
        next.status= jsonObject.get("status").getAsString();
        this.list.add(next);
    }

}

class Entry {

    public String title = "title here";
    public String inviter_name;
    public String inviter_id;
    public String invitee_name;
    public String invitee_id;
    public String timestamp;
    public String description;
    public String start_time;
    public String end_time;
    public String seen = "u";
    public String status= "u";

    public Entry() {

    }

    public Entry(String title, String inviter_name, String inviter_id, String invitee_name,
                 String invitee_id, String timestamp, String description, String start_time,
                 String end_time, String seen, String status) {

        this.title = title;
        this.inviter_name = inviter_name;
        this.inviter_id = inviter_id;
        this.invitee_name = invitee_name;
        this.invitee_id = invitee_id;
        this.timestamp = timestamp;
        this.description = description;
        this.start_time = start_time;
        this.end_time = end_time;
        this.seen = seen;
        this.status = status;

    }


}