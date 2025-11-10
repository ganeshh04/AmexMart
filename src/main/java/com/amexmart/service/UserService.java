package com.amexmart.service;

import com.amexmart.dto.UserProfileDto;
import com.amexmart.model.User;

import java.util.List;

public interface UserService
{
    public User saveUser(User user) ;

    User getCurrentUser();

    UserProfileDto getUserProfile();

    UserProfileDto updateUserProfile(UserProfileDto userProfileDto);

    List<User> getAllUsers();

    User getUserById(Long id);

    void deleteUser(Long id);
}
