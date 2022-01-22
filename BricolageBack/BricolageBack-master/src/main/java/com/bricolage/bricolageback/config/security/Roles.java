package com.bricolage.bricolageback.config.security;

public enum Roles {
	  CLIENT("CLIENT"),
	  BRICOLEUR("BRICOLEUR"),
	  ADMINISTRATEUR("ADMINISTRATEUR");
	  
	  private String role = "";
	   
	  Roles (String role){
	    this.role = role;
	  }
	   
	  public String getRole() {
		return role;
	  }

	   public void setRole(String role) {
			this.role = role;
	   }

	   public String toString(){
		    return role;
	   }
}
