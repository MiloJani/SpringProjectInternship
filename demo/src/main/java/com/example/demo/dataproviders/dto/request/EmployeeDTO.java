package com.example.demo.dataproviders.dto.request;


import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
