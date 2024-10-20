package com.example.myapplicationlpu;

public class UserDetails {
    String registration_number, name, program, section, profile_image, dob, cgpa, phone, agg_attendance, roll_number, cookie;

    public UserDetails(String registration_number, String name, String program, String section, String profile_image, String dob, String cgpa, String phone, String agg_attendance, String roll_number, String cookie) {
        this.registration_number = registration_number;
        this.name = name;
        this.program = program;
        this.section = section;
        this.profile_image = profile_image;
        this.dob = dob;
        this.cgpa = cgpa;
        this.phone = phone;
        this.agg_attendance = agg_attendance;
        this.roll_number = roll_number;
        this.cookie = cookie;
    }

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCgpa() {
        return cgpa;
    }

    public void setCgpa(String cgpa) {
        this.cgpa = cgpa;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAgg_attendance() {
        return agg_attendance;
    }

    public void setAgg_attendance(String agg_attendance) {
        this.agg_attendance = agg_attendance;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
