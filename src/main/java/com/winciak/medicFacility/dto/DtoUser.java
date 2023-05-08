package com.winciak.medicFacility.dto;



import com.winciak.medicFacility.validation.FieldMatch;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
public class DtoUser {

	@NotNull(message = "is required")
	@Size(min = 3, max = 50, message = "has to be at least 3 characters/digit long")
	private String login;

	@NotNull(message = "is required")
	@Size(min = 5, max = 50, message = "has to be at least 5 characters/digit long")
	private String password;
	
	@NotNull(message = "is required")
	@Size(min = 5, max = 50, message = "has to be at least 5 characters/digit long")
	private String matchingPassword;

	@Pattern(regexp = "^[A-Z][A-Za-z]{2,49}", message = "first letter has to be uppercase, length 3-50 letters ")
	private String firstName;

	@Pattern(regexp = "^[A-Z][A-Za-z]{2,49}", message = "first letter has to be uppercase, length 3-50 letters ")
	private String lastName;

	@Size(min = 9, max = 12, message = "9-12 numbers long")
	@Pattern(regexp = "^\\+?\\d*$", message = "+ sign allowed at start, then only numbers, no spaces or dashes")
	private String phoneNumber;

	@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
				message = "Incorrect email")
	private String email;

	//The following restrictions are imposed in the email address' local part by using this regex:
	//
	//    It allows numeric values from 0 to 9.
	//    Both uppercase and lowercase letters from a to z are allowed.
	//    Allowed are underscore “_”, hyphen “-“, and dot “.”
	//    Dot isn't allowed at the start and end of the local part.
	//    Consecutive dots aren't allowed.
	//    For the local part, a maximum of 64 characters are allowed.
	//
	//Restrictions for the domain part in this regular expression include:
	//
	//    It allows numeric values from 0 to 9.
	//    We allow both uppercase and lowercase letters from a to z.
	//    Hyphen “-” and dot “.” aren't allowed at the start and end of the domain part.
	//    No consecutive dots.

	private String role;

	private int whatId;

	public DtoUser() {

	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public int getWhatId() {
		return whatId;
	}

	public void setWhatId(int whatId) {
		this.whatId = whatId;
	}
}
