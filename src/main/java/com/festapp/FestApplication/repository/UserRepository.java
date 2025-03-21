package com.festapp.FestApplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.festapp.FestApplication.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	
    List<User> findByBioFirstNameContainingIgnoreCaseOrBioLastNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(String username, String firstName, String lastName);
    
    User findById(long id);

}
