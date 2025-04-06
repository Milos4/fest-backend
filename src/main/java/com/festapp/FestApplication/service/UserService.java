package com.festapp.FestApplication.service;

import java.util.List;
import java.util.Optional;

import com.festapp.FestApplication.dto.BioDTO;
import com.festapp.FestApplication.models.User;


public interface UserService {
    User getUserById(Long id);

    List<User> findAllUsers();
    List<User> searchUsers(String query) ;
    User findUserById(long id);
    Optional<BioDTO> getUserBioById(Long userId);
    void updateUserBio(Long userId, BioDTO bioDTO);
}
