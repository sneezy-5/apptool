package com.apptool.user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.apptool.user.config.dto.ReportDto;
import com.apptool.user.config.dto.StatusDto;
import com.apptool.user.model.Report;
import com.apptool.user.service.ReportService;
import com.apptool.user.utils.GetValidateionErros;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reports")
public class ReportController {
    
      @Autowired
    private ReportService reportService;

   
    @GetMapping
    public ResponseEntity<List<Report>> showRepotList() {
                List<Report> userList = reportService.getAllReports();
  
        return  ResponseEntity.ok(userList);
    }


    @PostMapping
     public ResponseEntity<?> saveReport(@Valid @ModelAttribute ReportDto reportDTO, BindingResult bindingResult, @RequestParam("files") MultipartFile[] files ) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = GetValidateionErros.getValidationErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
        }

     
      
        //System.out.print(reportDTO.getFile());
       
                return  reportService.saveReport(reportDTO, files); 
       
    }
    

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateReport(@PathVariable Long id, @Valid @ModelAttribute ReportDto reportDTO, BindingResult bindingResult, @RequestParam(value = "files", required = false) MultipartFile[] files) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = GetValidateionErros.getValidationErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
        }

                return  reportService.updateReport(reportDTO,id,files);
       
    }


     @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> statusReport(@PathVariable Long id, @Valid @RequestBody StatusDto statusDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> validationErrors = GetValidateionErros.getValidationErrors(bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
        }

                return  reportService.status(id);
       
    }


     @DeleteMapping("{id}")
     //@ResponseStatus(HttpStatus.NO_CONTENT) 
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        
         return reportService.deleteUser(id); 
    
    }

}
