package io.agileinteligence.ppmtool.exceptions;

public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
	this.username = "Invalid User Name";
	this.password = "Invalid Password";
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}
