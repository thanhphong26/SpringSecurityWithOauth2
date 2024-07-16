package com.pnt.demo.services;

import com.pnt.demo.dtos.UserDTO;
import com.pnt.demo.entities.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password) throws Exception;
}
