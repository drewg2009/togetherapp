package com.auth0.logindemo;

import java.util.ArrayList;

/**
 * Created by Matt Rowe 11/20/2016. Don't mind the mess.
 */

public class DataService {
    public ArrayList<Entry> list;

    public DataService() {
        this.list = new ArrayList<Entry>();
    }

    public DataService(int size) {
        this.list = new ArrayList<Entry>(size);
    }

    void add(Entry in) {
        this.list.add(in);
    }

    int displayType(int index) {
        String seen = this.list.get(index).seen;
        String status = this.list.get(index).status;
        if (status.equals("u")) {
            return 0;
        } else {
            return 1;
        }
    }

    int size() {
        return this.list.size();
    }

    Entry get(int index) {
        return this.list.get(index);
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