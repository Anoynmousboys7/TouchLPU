package com.example.myapplicationlpu;

public class ExamDetails {
    String course_code, course_name, exam_type, room_no, reporting_time, date, time, detainee_status, defaulter_detail;

    public ExamDetails(String course_code, String course_name, String exam_type, String room_no, String reporting_time, String date, String time, String detainee_status, String defaulter_detail) {
        this.course_code = course_code;
        this.course_name = course_name;
        this.exam_type = exam_type;
        this.room_no = room_no;
        this.reporting_time = reporting_time;
        this.date = date;
        this.time = time;
        this.detainee_status = detainee_status;
        this.defaulter_detail = defaulter_detail;
    }
}
