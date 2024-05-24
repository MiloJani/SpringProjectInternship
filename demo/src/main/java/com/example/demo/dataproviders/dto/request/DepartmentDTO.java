package com.example.demo.dataproviders.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {

    private Integer departmentId;
    private String departmentName;
}
