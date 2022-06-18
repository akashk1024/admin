package com.greatlearning.microservice.service;

import com.greatlearning.microservice.dto.UserDto;
import com.greatlearning.microservice.entity.Users;
import com.greatlearning.microservice.repository.AuthoritiesRepository;
import com.greatlearning.microservice.repository.UserRepository;
import com.greatlearning.microservice.entity.Authorities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthoritiesRepository authoritiesRepository;

    public Users save(UserDto userDto) {
        Users users = Users.builder().
                username(userDto.getUsername()).
                password(PasswordEncoderFactories.createDelegatingPasswordEncoder()
                        .encode(userDto.getPassword())).
                enabled(userDto.isEnabled()).build();

        Users savedUsers = userRepository.save(users);
        Authorities authoritiesEntity = Authorities.builder()
                .username(userDto.getUsername())
                .authority(userDto.getAuthority()).build();
        authoritiesRepository.save(authoritiesEntity);
        return savedUsers;
    }

    public List<Users> findAll() {
        return userRepository.findAll();
    }

    public UserDto findByUserName(String userName) throws Exception {
        Optional<Users> users = userRepository.findById(userName);
        Optional<Authorities> authorities = authoritiesRepository.findById(userName);
        UserDto userDto = null;
        if (users.isPresent() && authorities.isPresent()) {
            userDto = UserDto.builder()
                    .username(users.get().getUsername())
                    .enabled(users.get().isEnabled())
                    .authority(authorities.get().getAuthority()).build();
        } else
            throw new Exception("User not present");
        return userDto;
    }


    public UserDto updateUser(UserDto inputUserDto) throws Exception {

        String userName = inputUserDto.getUsername();
        Users users = userRepository.getById(userName);
        Authorities authorities = authoritiesRepository.getById(userName);


        if (users != null && authorities != null) {
                users.setEnabled(inputUserDto.isEnabled());
            if(inputUserDto.getUsername() !=null){
                users.setEnabled(inputUserDto.isEnabled());
            }
            users = userRepository.saveAndFlush(users);
            if (inputUserDto.getAuthority() != null) {
                authorities.setUsername(userName);
                authorities.setAuthority(inputUserDto.getAuthority());
                authorities = authoritiesRepository.saveAndFlush(authorities);
            }
            return UserDto.builder()
                    .username(users.getUsername())
                    .enabled(users.isEnabled())
                    .authority(authorities.getAuthority()).build();
        } else
            throw new Exception("User not present");

    }

    public boolean delete(String userName) {
        Optional<Users> users = userRepository.findById(userName);
        Optional<Authorities> authorities = authoritiesRepository.findById(userName);
        if(users.isPresent() && authorities.isPresent()){
            userRepository.delete(userRepository.getById(userName));
            authoritiesRepository.delete(authoritiesRepository.getById(userName));
            return true;
        }
        return false;
    }
}
