package com.rcaspe.CarRegistry.service;

import com.rcaspe.CarRegistry.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    public UserEntity save(UserEntity newUser);

    byte[] getUserImage(Long id);

    void addUserImage(Long id, MultipartFile file) throws IOException;
}

