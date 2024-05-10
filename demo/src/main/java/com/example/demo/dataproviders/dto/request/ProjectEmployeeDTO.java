package com.example.demo.dataproviders.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEmployeeDTO {

    Integer projectId;
    Integer employeeId;
}
