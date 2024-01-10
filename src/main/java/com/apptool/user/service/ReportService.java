package com.apptool.user.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import com.apptool.user.config.dto.FileDto;
import com.apptool.user.config.dto.ReportDto;

import com.apptool.user.config.payload.MessageResponse;
import com.apptool.user.model.File;
import com.apptool.user.model.Report;
import com.apptool.user.model.User;
import com.apptool.user.repository.FileRepository;
import com.apptool.user.repository.ReportRepository;
import com.apptool.user.repository.UserRepository;
import com.apptool.user.utils.FileUploadUtil;

import jakarta.transaction.Transactional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UserRepository usertRepository;

    @Autowired
    private FileRepository fileRepository;

        public List<Report> getAllReports() {
        List<Report> reportList = reportRepository.findAll();
          for (Report report : reportList) {
        List<File> files = fileRepository.findByReportId(report.getId());
        List<FileDto> fileDtos = files.stream()
                .map(file -> {
                    FileDto fileDto = new FileDto();
                    fileDto.setFileUrl(file.getFileUrl());
                    return fileDto;
                })
                .collect(Collectors.toList());
        report.setFiles(fileDtos);
    }

        // for (Report report : reportList) {
        //     List<File> files = fileRepository.findByReportId(report.getId());
        //     report.setFiles(files);
        //     System.out.print(files);
        // }
        
        return reportList;
    }

        public Report getUserById(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
    }

    //@Transactional
    public ResponseEntity<?> saveReport(ReportDto reportDto, MultipartFile[] files) {

        try {
    
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<User> existingUserOptional = usertRepository.findById(userDetails.getId());
        User existingUser = existingUserOptional.get();

    Report report = new Report();
    report.setContent(reportDto.getName());
    report.setDistrict(reportDto.getDistrict());
    report.setDepartment(reportDto.getDepartment());
    report.setRegion(reportDto.getRegion());
    report.setName(reportDto.getName());
    report.setTeam(reportDto.getTeam());
    report.setStatus(false);
    report.setStatusIndemnity(reportDto.getStatusIndemnity());


    report.setCreatedBy(existingUser);
        
             
         
          
        reportRepository.save(report);

        saveFile(files, report);
        MessageResponse messageResponse = new MessageResponse("Report saved successfully");
                return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user: " + e.getMessage());
        }
    }


    @Transactional
    public ResponseEntity<?> updateReport(ReportDto reportDto, Long id, MultipartFile[] files) {

           
        // Vérifier si le report existe
  
        Optional<Report> existingReportOptional = reportRepository.findById(id);
        try{
     
            // L'utilisateur existe, récupérez l'objet User
            Report existingReport = existingReportOptional.get();



            existingReport.setContent(reportDto.getName());
            existingReport.setDistrict(reportDto.getDistrict());
            existingReport.setDepartment(reportDto.getDepartment());
            existingReport.setRegion(reportDto.getRegion());
            existingReport.setName(reportDto.getName());
            existingReport.setTeam(reportDto.getTeam());
        
           // Sauvegardez les modifications
            reportRepository.save(existingReport);
            
              // Supprimez les anciens fichiers
        deleteOldFiles(existingReport.getId());
               // Vérifiez si des fichiers sont fournis pour la mise à jour
        if (files != null && files.length > 0) {
            saveFile(files, existingReport);
        }
          
             MessageResponse messageResponse = new MessageResponse("Raport updated successfully");
            return new ResponseEntity<>(messageResponse, HttpStatus.ACCEPTED); 
         
} catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user: " + e.getMessage());
}
       
    }


    
    @Transactional
    public ResponseEntity<?> deleteUser(Long id) {

        if(reportRepository.findById(id) != null){
            reportRepository.deleteById(id);
              MessageResponse messageResponse = new MessageResponse("User delete successfully");
         return new ResponseEntity<>(messageResponse, HttpStatus.NO_CONTENT);
        }else{
              MessageResponse messageResponse = new MessageResponse("User not found");
         return new ResponseEntity<>(messageResponse, HttpStatus.NOT_FOUND);
        }
       
       
    
    }


       @Transactional
    public ResponseEntity<?> status(Long id) {

        
        Optional<Report> existingReportOptional = reportRepository.findById(id);
        try{
     
            // L'utilisateur existe, récupérez l'objet User
            Report existingReport = existingReportOptional.get();

  

        existingReport.setStatus(true);
        
           // Sauvegardez les modifications
            reportRepository.save(existingReport);
             MessageResponse messageResponse = new MessageResponse("raport validate successfully");
            return new ResponseEntity<>(messageResponse, HttpStatus.ACCEPTED); 
         
} catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user: " + e.getMessage());
}
       
    }
       
    

      public void saveFile(MultipartFile[] files, Report report) {
    try {


        
        Arrays.asList(files).stream().forEach(file -> {
                try {
                    
                    String fileCode = RandomStringUtils.randomAlphanumeric(8);
                    String fileName = fileCode + '-' + file.getOriginalFilename();
                               

                    Path filePath =FileUploadUtil.saveFile(fileName, file);

                    File fil = new File();
                    fil.setFileName(fileName);
                    fil.setFileUrl(filePath.toString());
                    fil.setReport(report);

                    fileRepository.save(fil);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }
  }


  public void deleteOldFiles(Long reportId) {
    List<File> oldFiles = fileRepository.findByReportId(reportId);
    
    for (File oldFile : oldFiles) {
        try {
            Path filePath = Paths.get(oldFile.getFileUrl());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        fileRepository.delete(oldFile);
    }
}

    
    private String saveImageAndGetLink(MultipartFile image) throws IOException {
        // Générer un nom de fichier unique
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();

        // Chemin complet où le fichier sera sauvegardé (ajustez le chemin selon vos besoins)
        Path filePath = Path.of("/chemin/vers/votre/dossier/images", fileName);

        // Sauvegarder le fichier localement
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retourner le lien vers l'image sauvegardée
        return "/chemin/vers/votre/dossier/images/" + fileName;
    }
 
}
