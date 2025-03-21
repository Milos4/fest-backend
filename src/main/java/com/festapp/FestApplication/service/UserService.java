package com.festapp.FestApplication.service;

import java.util.List;
import java.util.Optional;

import com.festapp.FestApplication.dto.BioDTO;
import com.festapp.FestApplication.models.User;


public interface UserService {
    List<User> findAllUsers();
    List<User> searchUsers(String query) ;
    User findUserById(long id);
    
    Optional<BioDTO> getUserBioById(Long userId);
    void updateUserBio(Long userId, BioDTO bioDTO);
}
