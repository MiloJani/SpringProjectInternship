package com.example.demo.dto;

import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

public class ProjectDTO {

    @Id
    private Integer project_id;

    private String project_name;

    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Temporal(TemporalType.DATE)
    private Date end_date;

    public ProjectDTO() {
    }

    public ProjectDTO(Integer project_id, String project_name, Date start_date, Date end_date) {
        this.project_id = project_id;
        this.project_name = project_name;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public ProjectDTO(String project_name, Date start_date, Date end_date) {
        this.project_name = project_name;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Integer getProject_id() {
        return project_id;
    }

    public void setProject_id(Integer project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
