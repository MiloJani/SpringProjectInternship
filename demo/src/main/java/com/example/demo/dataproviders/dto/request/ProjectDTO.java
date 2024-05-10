package com.example.demo.dataproviders.dto.request;

import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
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


}
