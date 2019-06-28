package com.example.dataframe.Models;

public class SchoolModel {
    private String schoolName;
    private String schoolId;

    public SchoolModel(String schoolName, String schoolId) {
        this.schoolName = schoolName;
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
}
