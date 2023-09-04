package ua.com.harazh.oblik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.harazh.oblik.domain.OblikUser;
import ua.com.harazh.oblik.domain.Role;
import ua.com.harazh.oblik.domain.dto.*;
import ua.com.harazh.oblik.exception.ExceptionWithMessage;
import ua.com.harazh.oblik.repository.UserRepository;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class UserService {
	
	private UserRepository userRepository;
	
	private PasswordEncoder bCryptPasswordEncoder;
	
	private WorkService workService;
	
	@Value("${user.wrongId}")
	private String wrongUserIdMessage;
	
	@Value("${user.wrongIdOrDel}")
	private String wrongUserIdMessageOrDeletedAlready;
	
	@Value("${password.wrongPass}")
	private String passwordIncorrect;

	@Autowired
	public UserService(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, WorkService workService) {
		super();
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.workService = workService;
	}

	public UserResponseDto createNewUser(UserCreationDto userCreatonDto) {
		
		OblikUser userToSave = new OblikUser(userCreatonDto);
		userToSave.setPassword(bCryptPasswordEncoder.encode(userCreatonDto.getPassword()));
		
		OblikUser oblikUser = userRepository.save(userToSave);
		
		return new UserResponseDto(oblikUser);
	}

	public UserResponseDto updateUser(UpdateUserDto userCreatonDto, Long id) {
		
		
		OblikUser oblikUser = getUserByidOrThrowError(id);
		
		if(!Objects.isNull(userCreatonDto.getRole())) oblikUser.setRole(Role.valueOf(userCreatonDto.getRole().toString()));
		if(!Objects.isNull(userCreatonDto.getName())) oblikUser.setName(userCreatonDto.getName());
		if(!Objects.isNull(userCreatonDto.getPercentage())) oblikUser.setPercentage(userCreatonDto.getPercentage());
		
		OblikUser newUser = userRepository.save(oblikUser);
		
		return new UserResponseDto(newUser);
	}

	public List<UserResponseDto> getAllNondeletedUsers() {
		List<OblikUser> users = userRepository.findAll();
		
		List<UserResponseDto> listoToReturn = new ArrayList<>();
		
		if (users.isEmpty()) {
			return listoToReturn;
		}
		
		for(OblikUser user : users) {
			if(!user.isDeleted()) {
				listoToReturn.add(new UserResponseDto(user));
			}
		}

		return listoToReturn;
	}

	public void deleteUser(Long id) {
		
		OblikUser user = getUserByidOrThrowError(id);
		
		user.setDeleted(true);
		
		userRepository.save(user);
		
	}

	public UserResponseDto getUserById(Long id) {
		
		OblikUser user = getUserByidOrThrowError(id);
		
		return new UserResponseDto(user);
	}
	
	public void assignUserToWork(Long userId, Long workId) {
		
		OblikUser user = getUserByidOrThrowError(userId);
		workService.setUserForWork(user, workId);
		
	}
	
	public void changePassword(UserDetails username, ChangePasswordDto changePasswordDto) {
			
		OblikUser user = getOblikUserByUserDetails(username);
		
		if(bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {
			user.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
		}else {
			throw new ExceptionWithMessage(passwordIncorrect, "oldPassword");
		}
	}
	
	public List<ResponseCompletedWorkDto>  getResponseCompletedWorkDtoByUser(UserDetails username) {
	
		OblikUser user = getOblikUserByUserDetails(username);
		return workService.getAllDoneWorksByUser(user);
	}
	
	public List<ResponseCompletedWorkDto>  getResponseCompletedWorkDtoByUserId(Long id) {
		
		OblikUser user = getUserByidOrThrowError(id);
		return workService.getAllDoneWorksByUser(user);
	}

	public List<ResponseCompletedWorkDto> getCompletedWorksByUsersBetweenDates(UsersAndTimeframeDto usersAndTimeframeDto) {
		List<OblikUser> users = usersAndTimeframeDto.getUsers().stream().map((e) ->
				getUserByidOrThrowError(e)).collect(Collectors.toList());

		return workService.getAllDoneWorksByUsersAndDates(users, usersAndTimeframeDto);
	}
	
    
	private OblikUser getOblikUserByUserDetails(UserDetails username) {
		Optional<OblikUser> optional = userRepository.findByName(username.getUsername());
		return optional.get();
	}
	
	private OblikUser getUserByidOrThrowError(Long id) {
		Optional<OblikUser> optional = userRepository.findById(id);
		
		if(!optional.isPresent() || optional.get().isDeleted()) {
			throw new ExceptionWithMessage(wrongUserIdMessageOrDeletedAlready, "id");
		}
		
		return optional.get();
	}



}
