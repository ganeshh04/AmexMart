package com.amexmart.controller.admin;


import com.amexmart.dto.UserResponseDto;
import com.amexmart.model.Role;
import com.amexmart.model.User;
import com.amexmart.repository.RoleRepository;
import com.amexmart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userRepository.findAll()
                .stream()
                .map(u -> modelMapper.map(u, UserResponseDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/users/{id}/roles")
    public ResponseEntity<String> assignRoleToUser(@PathVariable Long id, @RequestParam String roleName) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        // add role (preserve existing roles)
        user.getRoles().add(role);
        userRepository.save(user);

        return ResponseEntity.ok("Role " + roleName + " assigned to user " + user.getUsername());
    }
}

