package com.apptool.user.model;

import java.util.List;

import com.apptool.user.config.dto.FileDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private List<String> team;

    @NotBlank
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @Column(nullable = true, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean status;

    
    //@OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
     @Transient
    //@JsonInclude(JsonInclude.Include.NON_NULL)
    //@JsonIgnore
    private List<FileDto> files;
    
    public Report() {
    }

    public Report(String name, String district, String department, String region, String statusIndemnity, List<String> team, String content, User createdBy, Boolean status) {
        this.name = name;
        this.district = district;
        this.department = department;
        this.region = region;
        this.statusIndemnity = statusIndemnity;
        this.team = team;
        this.content = content;
        this.createdBy = createdBy;
        this.status = status;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getStatus() {
        return status;
    }
     public void setStatus(Boolean status) {
        this.status = status;
    }



    public List<FileDto> getFiles() {
        return files;
    }

    public void setFiles(List<FileDto> files) {
        this.files = files;
    }
    
}
