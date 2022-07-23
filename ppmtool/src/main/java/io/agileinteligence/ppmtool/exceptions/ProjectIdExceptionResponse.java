package io.agileinteligence.ppmtool.exceptions;

public class ProjectIdExceptionResponse {

    private String projectIdentifier;

    public ProjectIdExceptionResponse() {
    }

    public ProjectIdExceptionResponse(String projectIdentifier) {
	super();
	this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
	return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
	this.projectIdentifier = projectIdentifier;
    }

}