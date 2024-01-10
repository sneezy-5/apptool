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
import com.apptool.user.model.User;
import com.apptool.user.service.UserService;
import com.apptool.user.utils.GetValidateionErros;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

   
    @GetMapping
    public ResponseEntity<List<User>> showUserList() {
                List<User> userList = userService.getAllUsers();
  
        return  ResponseEntity.ok(userList);
    }


    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDto userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = GetValidateionErros.getValidationErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
        }
    
       
                return  userService.saveUser(userDTO); 
       
    }
    



   
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateUser(@PathVariable Long id,  @RequestBody @Valid UserEditDto userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = GetValidateionErros.getValidationErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
        }


      
           
    
            
                return  userService.updateUser(userDTO,id);
       
    }

    @DeleteMapping("{id}")
     //@ResponseStatus(HttpStatus.NO_CONTENT) 
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        
         return userService.deleteUser(id); 
    
    }

    


   
}