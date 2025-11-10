package com.amexmart.service.impl;


import com.amexmart.dto.UserProfileDto;
import com.amexmart.model.User;
import com.amexmart.repository.UserRepository;
import com.amexmart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User saveUser(User user)
    {
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public UserProfileDto getUserProfile() {
        User user = getCurrentUser();
        return modelMapper.map(user, UserProfileDto.class);
    }

    @Override
    public UserProfileDto updateUserProfile(UserProfileDto userProfileDto) {
        User currentUser = getCurrentUser();

        currentUser.setFirstName(userProfileDto.getFirstName());
        currentUser.setLastName(userProfileDto.getLastName());
        currentUser.setPhoneNumber(userProfileDto.getPhoneNumber());

        User updatedUser = userRepository.save(currentUser);
        return modelMapper.map(updatedUser, UserProfileDto.class);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UsernameNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
