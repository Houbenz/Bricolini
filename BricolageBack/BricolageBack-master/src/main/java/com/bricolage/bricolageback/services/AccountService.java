package com.bricolage.bricolageback.services;

import com.bricolage.bricolageback.config.security.Roles;
import com.bricolage.bricolageback.config.security.dto.RegistrationForm;
import com.bricolage.bricolageback.dto.ResetPasswordDTO;
import com.bricolage.bricolageback.dto.UserDTO;
import com.bricolage.bricolageback.entities.Role;
import com.bricolage.bricolageback.entities.User;

import java.util.List;

public interface AccountService {
    User saveUser(User user);
    void updateUser(User user, UserDTO userDTO);
    Role saveRole(Role r);
    User findUserByUsername(String username);
    void addRoleToUser(String username, Roles r);
    User signUp(RegistrationForm data);
    User resetPassword(ResetPasswordDTO data);
    List<RegistrationForm> getAllUsersByUsername(String username);
    List<User> getAllUsers();
    boolean blockUser(long id);
}
