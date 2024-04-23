package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

public class EmployeeDTO {

    @Id
    private Integer employee_id;
    @NotBlank(message = "Field is required")
    private String first_name;
    private String last_name;
    private String job_title;
    private int salary;

    public EmployeeDTO() {
    }

    public EmployeeDTO(Integer employee_id, String first_name, String last_name, String job_title, int salary) {
        this.employee_id = employee_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.job_title = job_title;
        this.salary = salary;
    }

    public EmployeeDTO(String first_name, String last_name, String job_title, int salary) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.job_title = job_title;
        this.salary = salary;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
