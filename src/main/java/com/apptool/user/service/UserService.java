package com.apptool.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apptool.user.config.dto.UserDto;
import com.apptool.user.config.dto.UserEditDto;
import com.apptool.user.config.payload.ErrorResponse;
import com.apptool.user.config.payload.MessageResponse;
import com.apptool.user.model.ERole;
import com.apptool.user.model.Role;
import com.apptool.user.model.User;
import com.apptool.user.repository.RoleRepository;
import com.apptool.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
     private PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;


    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();

        return userList;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
    }

    @Transactional
    public ResponseEntity<?> saveUser(UserDto userDto) {

        try {
               if (userRepository.existsByUsername((userDto.getUsername()))) {
            ErrorResponse errorResponse = new ErrorResponse("Le username  existe déjà");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
            if ( userRepository.existsByEmail(userDto.getEmail())) {
                ErrorResponse errorResponse = new ErrorResponse(" l'email existe déjà");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }

            
        String hashedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

         Set<String> strRoles = userDto.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found1."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found2."));
          roles.add(adminRole);

          break;
        case "mod":
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found3."));
          roles.add(modRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }
        user.setRoles(roles);
         user.setPassword(hashedPassword);
        userRepository.save(user);
        MessageResponse messageResponse = new MessageResponse("User saved successfully");
                return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<?> updateUser(UserEditDto userDto, Long id) {

              if ( userRepository.countByUsernameAndExcludeUserId(userDto.getUsername(),id) > 0) {
            ErrorResponse errorResponse = new ErrorResponse("Le username  existe déjà");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
            if ( userRepository.countByEmailAndExcludeUserId(userDto.getEmail(),id) > 0) {
                ErrorResponse errorResponse = new ErrorResponse(" l'email existe déjà");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        // Vérifier si l'utilisateur existe
  
        Optional<User> existingUserOptional = userRepository.findById(id);
        try{
     
            // L'utilisateur existe, récupérez l'objet User
            User existingUser = existingUserOptional.get();

            // Mettez à jour les propriétés avec les nouvelles valeurs
            existingUser.setEmail(userDto.getEmail());
            existingUser.setUsername(userDto.getUsername());

            

                  Set<String> strRoles = userDto.getRole();
                Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
         existingUser.getRoles().removeAll(existingUser.getRoles());
 
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
        existingUser.getRoles().remove(role);
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
          
              .orElseThrow(() -> new RuntimeException("Error: Role is not found2."));
          roles.add(adminRole);

          break;
        case "mod":
          existingUser.getRoles().remove(role);
          Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found3."));
          roles.add(modRole);

          break;
        default:
          existingUser.getRoles().remove(role);
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }
        existingUser.setRoles(roles);
       
        
           // Sauvegardez les modifications
            userRepository.save(existingUser);
             MessageResponse messageResponse = new MessageResponse("User updated successfully");
            return new ResponseEntity<>(messageResponse, HttpStatus.ACCEPTED); 
         
} catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user: " + e.getMessage());
}
       
    }


    @Transactional
    public ResponseEntity<?> deleteUser(Long id) {

        if(userRepository.findById(id) != null){
            userRepository.deleteById(id);
              MessageResponse messageResponse = new MessageResponse("User delete successfully");
         return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
        }else{
              MessageResponse messageResponse = new MessageResponse("User not found");
         return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
        }
       
       
    
    }

 
}