package com.example.demo.dataproviders.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoleDTO {
    private Integer roleId;
    private String roleName;
    private List<PermissionDTO> permissions;
}
