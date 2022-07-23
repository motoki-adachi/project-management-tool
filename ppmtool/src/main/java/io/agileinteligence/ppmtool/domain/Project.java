package io.agileinteligence.ppmtool.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "※プロジェクト名称必須項目です")
    private String projectName;

    @NotBlank(message = "※プロジェクトIDは必須項目です")
    @Size(min = 4, max = 5, message = "※4文字以上5文字以下で入力してください")
    @Column(updatable = false, unique = true) // 更新しない。重複しない。
    private String projectIdentifer;

    @NotBlank(message = "※内容は必須項目です")
    private String description;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date endDate;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private Backlog backlog;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private String projectLeader;

    public Project() {

    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getProjectName() {
	return projectName;
    }

    public void setProjectName(String projectName) {
	this.projectName = projectName;
    }

    public String getProjectIdentifer() {
	return projectIdentifer;
    }

    public void setProjectIdentifer(String projectIdentifer) {
	this.projectIdentifer = projectIdentifer;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
	return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
	this.updatedAt = updatedAt;
    }

    public Backlog getBacklog() {
	return backlog;
    }

    public void setBacklog(Backlog backlog) {
	this.backlog = backlog;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public String getProjectLeader() {
	return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
	this.projectLeader = projectLeader;
    }

    @PrePersist
    protected void onCreate() {
	this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
	this.updatedAt = new Date();
    }

    @Override
    public String toString() {
	return "Project [id=" + id + ", projectName=" + projectName + ", projectIdentifer=" + projectIdentifer
		+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", createdAt="
		+ createdAt + ", updatedAt=" + updatedAt + ", backlog=" + backlog + "]";
    }

}
