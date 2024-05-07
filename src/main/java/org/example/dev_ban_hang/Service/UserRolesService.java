package org.example.dev_ban_hang.Service;


import org.example.dev_ban_hang.DTO.UserRolesDTO;
import org.example.dev_ban_hang.Entity.UserRoles;
import org.example.dev_ban_hang.Mapper.UserRolesMapper;
import org.example.dev_ban_hang.Repository.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRolesService {
    @Autowired
    private UserRolesRepository userRolesRepository;

    private UserRolesMapper userRolesMapper;

    public UserRolesDTO findUserByUsername(String username) {
        UserRoles userRoles = userRolesRepository.findByUsername(username);
        return userRolesMapper.toDto(userRoles);

    }

    public boolean isAdmin(UserRolesDTO userRolesDTO) {
        return userRolesDTO != null && userRolesDTO.getRole() != null && userRolesDTO.getRole() == 0; // Giả sử role của admin là 1
    }

}
