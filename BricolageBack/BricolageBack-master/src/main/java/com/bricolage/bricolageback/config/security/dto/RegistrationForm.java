package com.bricolage.bricolageback.config.security.dto;

import com.bricolage.bricolageback.config.security.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class RegistrationForm {
	private long id;
	private String nom;
	private String prenom;
	private String email;
	private String adresse;
	private String phone_number;
	private Roles role;
	private long domaine;
	private String password;
	private boolean premium;
	private String repassword;
}
