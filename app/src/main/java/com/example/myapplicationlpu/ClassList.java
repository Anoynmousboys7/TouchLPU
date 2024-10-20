package com.example.myapplicationlpu;

public class ClassList {
    String classname, time, room;

    public ClassList(String classname, String time, String room) {
        this.classname = classname;
        this.time = time;
        this.room = room;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
