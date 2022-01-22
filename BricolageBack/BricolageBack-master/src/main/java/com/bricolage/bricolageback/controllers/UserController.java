package com.bricolage.bricolageback.controllers;

import com.bricolage.bricolageback.config.security.Roles;
import com.bricolage.bricolageback.config.security.dto.RegistrationForm;
import com.bricolage.bricolageback.dto.ResetPasswordDTO;
import com.bricolage.bricolageback.dto.UserDTO;
import com.bricolage.bricolageback.entities.Domaine;
import com.bricolage.bricolageback.entities.Role;
import com.bricolage.bricolageback.entities.Signalement;
import com.bricolage.bricolageback.entities.User;
import com.bricolage.bricolageback.repositories.RoleRepository;
import com.bricolage.bricolageback.repositories.SignalementRepository;
import com.bricolage.bricolageback.services.AccountService;
import com.bricolage.bricolageback.services.DomaineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private SignalementRepository signalementRepository;
	@Autowired
	private DomaineService domaineService;
	@PostMapping("/users")
	public User signUp(@RequestBody RegistrationForm data) {
		return accountService.signUp(data);
		}

	@GetMapping("/users/userByUsername")
	public User getUserByUsername(@RequestParam String username) {
		return accountService.findUserByUsername(username);
		}

	@PostMapping("/users/resetPassword")
	public User resetPassword(@RequestBody ResetPasswordDTO data) {
		return accountService.resetPassword(data);
		}

	@PostMapping("/userRoles")
	public void associateUserWithRole(@RequestParam String email,@RequestParam Roles role) {
		accountService.addRoleToUser(email, role);
	}
	@GetMapping("/roles")
	public List<Role> getRoles() {
		return roleRepository.findAll();
	}


	@DeleteMapping(value = "users/block/{id}")
	public ResponseEntity<Boolean> blockUser(@PathVariable(name = "id") Integer id){
		boolean result = accountService.blockUser(id);
		return ResponseEntity.status(result ? HttpStatus.OK : HttpStatus.FORBIDDEN).body(result);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = accountService.getAllUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<List<RegistrationForm>> getAllUsersByUsername(@PathVariable(name = "username") String username) {
		List<RegistrationForm> users = accountService.getAllUsersByUsername(username);
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@GetMapping("/users/self")
	public ResponseEntity<UserDTO> getUser(Authentication authentication){
		User user = accountService.findUserByUsername((String) authentication.getPrincipal());

		Domaine domaine = user.getDomaine();
		long idDomaine = domaine==null?0:domaine.getId();

		return new ResponseEntity<UserDTO>(
				new UserDTO(0, user.getNom(),
						user.getPrenom(),
						"",
						user.getTelephone(),
						"",
						user.getAdresse(),
						user.getRoles().toString(),
						String.valueOf(idDomaine),
						user.isPremium(),
						"",0)
				,HttpStatus.OK);

	}

	@PostMapping("/users/self")
	public ResponseEntity<Boolean> editUser(Authentication authentication, @RequestBody UserDTO userDTO){

		User user = accountService.findUserByUsername((String) authentication.getPrincipal());

		accountService.updateUser(user,userDTO);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/users/self")
	public ResponseEntity<Boolean> deleteUser(Authentication authentication){
		User user = accountService.findUserByUsername((String) authentication.getPrincipal());

		accountService.blockUser(user.getId());

		return new ResponseEntity<>(HttpStatus.OK);
	}
	@GetMapping("/signalements")
	public List<Signalement> getSignalements(){
		return signalementRepository.findAll();
	}

}