package org.example.dev_ban_hang.Mapper;

import lombok.RequiredArgsConstructor;
import org.example.dev_ban_hang.DTO.UserRolesDTO;
import org.example.dev_ban_hang.Entity.UserRoles;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class UserRolesMapper implements EntityMapper<UserRoles, UserRolesDTO> {

    @Override
    public UserRoles toEntity(UserRolesDTO dto) {
        return UserRoles
                .builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .role(dto.getRole())
                .build();
    }

    @Override
    public UserRolesDTO toDto(UserRoles entity) {
        return UserRolesDTO
                .builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .role(entity.getRole())
                .build();
    }

    @Override
    public List<UserRoles> toEntity(List<UserRolesDTO> Dto) {
        return List.of();
    }

    @Override
    public List<UserRolesDTO> toDto(List<UserRoles> entity) {
        List<UserRolesDTO> dtos = new ArrayList<>();
        entity.forEach(UserRoles ->
                {
                        UserRolesDTO userRolesDTO = toDto(UserRoles);
                        dtos.add(userRolesDTO);
                }
        );
        return dtos;
    }
}
