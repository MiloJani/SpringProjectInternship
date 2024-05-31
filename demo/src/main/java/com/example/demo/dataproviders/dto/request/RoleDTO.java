package com.example.demo.dataproviders.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Integer roleId;
    private String roleName;
    private List<PermissionDTO> permissions;
}
