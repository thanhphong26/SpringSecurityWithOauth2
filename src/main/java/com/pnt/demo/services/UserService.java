package com.pnt.demo.services;

import com.pnt.demo.components.TokenUtil;
import com.pnt.demo.dtos.UserDTO;
import com.pnt.demo.entities.Role;
import com.pnt.demo.entities.User;
import com.pnt.demo.repositories.RoleRepository;
import com.pnt.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenUtil tokenUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> user=userRepository.findByPhoneNumber(phoneNumber);
        if(user.isEmpty()){
            throw new Exception("User not found");
        }
        User existedUser=user.get();
        if(!passwordEncoder.matches(password,existedUser.getPassword())){
            throw new Exception("Password is incorrect");
        }
        return tokenUtil.generateToken(existedUser);
    }
    @Override public User createUser(UserDTO userDTO) throws Exception{
        String phoneNumber=userDTO.getPhoneNumber();
        if(userRepository.findByPhoneNumber(phoneNumber).isPresent()){
            throw new Exception("Phone number already exists");
        }
        Role role=roleRepository.findByName("user");
        User newUser=User.builder()
                .phoneNumber(userDTO.getPhoneNumber())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .build();
        newUser.setRole(role);
        return userRepository.save(newUser);
    }
}
