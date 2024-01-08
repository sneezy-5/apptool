package com.apptool.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.apptool.user.config.dto.UserDto;
import com.apptool.user.config.dto.UserEditDto;
import com.apptool.user.config.payload.ErrorResponse;
import com.apptool.user.config.payload.MessageResponse;
import com.apptool.user.model.User;
import com.apptool.user.repository.UserRepository;
import com.apptool.user.service.UserService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //  @Autowired
    // private UserRepository userRepository;
    
    @GetMapping
    public ResponseEntity<List<User>> showUserList(Model model) {
                List<User> userList = userService.getAllUsers();
  
        return  ResponseEntity.ok(userList);
    }


    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDto userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = getValidationErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
        }
    
       
                return  userService.saveUser(userDTO); 
       
    }
    



   
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateUser(@PathVariable Long id,  @RequestBody @Valid UserEditDto userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = getValidationErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
        }


      
           
    
            
                return  userService.updateUser(userDTO,id);
       
    }

    @DeleteMapping("{id}")
     //@ResponseStatus(HttpStatus.NO_CONTENT) 
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        
         return userService.deleteUser(id); 
    
    }

    private Map<String, String> getValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }


   
}