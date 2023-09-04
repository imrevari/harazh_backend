package ua.com.harazh.oblik.controller;

import java.time.LocalDateTime;
import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.harazh.oblik.domain.Role;
import ua.com.harazh.oblik.domain.dto.*;
import ua.com.harazh.oblik.security.AuthenticatedUserDetails;
import ua.com.harazh.oblik.service.UserService;
import ua.com.harazh.oblik.validator.user.ChangePasswordValidator;
import ua.com.harazh.oblik.validator.user.CreatingUserValidator;
import ua.com.harazh.oblik.validator.user.UpdatingUserValidator;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private UserService userService;
	
	private CreatingUserValidator creatingUserValidator;
	
	private UpdatingUserValidator updatingUserValidator;
	
	private ChangePasswordValidator changePasswordValidator;

	@Autowired
	public UserController(UserService userService, CreatingUserValidator creatingUserValidator,
			UpdatingUserValidator updatingUserValidator, ChangePasswordValidator changePasswordValidator) {
		super();
		this.userService = userService;
		this.creatingUserValidator = creatingUserValidator;
		this.updatingUserValidator = updatingUserValidator;
		this.changePasswordValidator = changePasswordValidator;
	}
	
	
	@InitBinder("userCreationDto")
    protected void initNewUserBinder(WebDataBinder binder) {
        binder.addValidators(creatingUserValidator);
    }
	
	@InitBinder("updateUserDto")
    protected void initUpdateUserBinder(WebDataBinder binder) {
        binder.addValidators(updatingUserValidator);
    }
	
	@InitBinder("changePasswordDto")
    protected void initChangePasswordBinder(WebDataBinder binder) {
        binder.addValidators(changePasswordValidator);
    }
	
	@GetMapping("/me")
    public ResponseEntity<AuthenticatedUserDetails> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(new AuthenticatedUserDetails(user), HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
		
		UserResponseDto newUser = userService.getUserById(id);
		
		if (Objects.isNull(newUser)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}
	
	 @GetMapping("/roles")
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	 public ResponseEntity<Map<String, String>> getRolesOfUsers() {
	        return new ResponseEntity<>(getRoles(), HttpStatus.OK);
	 }
	 
	 private Map<String, String> getRoles() {
	        Map<String, String> roles = new LinkedHashMap<>();
	        for (Role role : Role.values()) {
	            roles.put(role.name(), role.getDisplayedname());
	        }
	        return roles;
	    }

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserResponseDto> createNewUser(@RequestBody @Valid UserCreationDto userCreationDto) {
		
		UserResponseDto newUser = userService.createNewUser(userCreationDto);
		
		if (Objects.isNull(newUser)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}
	

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto, @PathVariable Long id){
		
		UserResponseDto updatedUser = userService.updateUser(updateUserDto, id);
		
		if (Objects.isNull(updatedUser)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

        return new ResponseEntity<>(updatedUser, HttpStatus.CREATED);
		
	}
	

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserResponseDto>> getAllUsers(){
		List<UserResponseDto> users = userService.getAllNondeletedUsers();
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserResponseDto>> deleteUser(@PathVariable Long id) {
		
		userService.deleteUser(id);
		
		List<UserResponseDto> users = userService.getAllNondeletedUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@PostMapping("/{userId}/{workId}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SENIOR_USER')")
	public ResponseEntity<?> assignUserToWork(@PathVariable Long userId, @PathVariable Long workId) {
		
		userService.assignUserToWork(userId, workId);
		
        return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PostMapping("/password")
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        
        userService.changePassword(user, changePasswordDto);
		
        return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@GetMapping("/myworks")
	public ResponseEntity<List<ResponseCompletedWorkDto>> getMyCompletedWork(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
        
        List<ResponseCompletedWorkDto> listToReturn = userService.getResponseCompletedWorkDtoByUser(user);
		
		return new ResponseEntity<>(listToReturn, HttpStatus.OK);
	}
	
	@GetMapping("/userworks/{userId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<ResponseCompletedWorkDto>> getUsersCompletedWorkbyId(@PathVariable Long userId){
		
		List<ResponseCompletedWorkDto> listToReturn = userService.getResponseCompletedWorkDtoByUserId(userId);

		return new ResponseEntity<>(listToReturn, HttpStatus.OK);
	}

	@PostMapping("/report")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<ResponseCompletedWorkDto>> getCompletedWorksByParameters(@RequestBody UsersAndTimeframeDto usersAndTimeframeDto){

		List<ResponseCompletedWorkDto> listToReturn = userService.getCompletedWorksByUsersBetweenDates(usersAndTimeframeDto);

		return new ResponseEntity<>(listToReturn, HttpStatus.OK);
	}

}
