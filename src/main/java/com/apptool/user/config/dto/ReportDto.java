package com.apptool.user.config.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ReportDto {

    @NotBlank
    @Size(min = 4, max = 100)
    private String name;

    @NotBlank
    @Size(min = 4, max = 100)
    private String district;

    @NotBlank
    @Size(min = 4, max = 100)
    private String department;

    @NotBlank
    @Size(min = 4, max = 100)
    private String region;

    @NotBlank
    @Size(min = 4, max = 100)
    private String statusIndemnity;

    @NotEmpty
    // @Size(min = 4, max = 100)
    private List< String> team;

    @NotBlank
    private String content;

    // @NotBlank
    // private String createdBy;

    // private MultipartFile files;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getStatusIndemnity() {
        return statusIndemnity;
    }

    public void setStatusIndemnity(String statusIndemnity) {
        this.statusIndemnity = statusIndemnity;
    }

    public List<String> getTeam() {
        return team;
    }

    public void setTeam(List<String> team) {
        this.team = team;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    // public String getCreatedBy() {
    //     return createdBy;
    // }

    // public void setCreatedBy(String createdBy) {
    //     this.createdBy = createdBy;
    // }

    //  public MultipartFile getFile() {
    //     return files;
    // }

    // public void setFile(MultipartFile file) {
    //     this.files = files;
    // }
}
