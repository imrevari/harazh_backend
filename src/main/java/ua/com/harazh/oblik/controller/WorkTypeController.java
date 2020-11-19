package ua.com.harazh.oblik.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.harazh.oblik.domain.dto.CreateWorkTypeDto;
import ua.com.harazh.oblik.domain.dto.UpdateWorkTypeDto;
import ua.com.harazh.oblik.domain.dto.WorkTypeResponseDto;
import ua.com.harazh.oblik.service.WorkTypeService;

@RestController
@RequestMapping("/work")
public class WorkTypeController {
	
	private WorkTypeService workTypeService;

	@Autowired
	public WorkTypeController(WorkTypeService workTypeService) {
		super();
		this.workTypeService = workTypeService;
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<WorkTypeResponseDto> createNewWorkType(@Valid @RequestBody CreateWorkTypeDto workTypeDto) {
		
		WorkTypeResponseDto toReturn =  workTypeService.createNewWorkType(workTypeDto);
		
		return new ResponseEntity<>(toReturn, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<WorkTypeResponseDto>> geWorkTypes(){
		List<WorkTypeResponseDto> toReturn = workTypeService.getAllWorkTypes();

        return new ResponseEntity<>(toReturn, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<WorkTypeResponseDto> geWorkTypeById(@PathVariable Long id){
		WorkTypeResponseDto toReturn = workTypeService.getWorkTypeById(id);
		
		if (Objects.isNull(toReturn)) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(toReturn, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<WorkTypeResponseDto> updateWorkType(@Valid @RequestBody UpdateWorkTypeDto workTypeDto, @PathVariable Long id){
		
		WorkTypeResponseDto response = workTypeService.updateWorkType(workTypeDto, id);
		
		if (Objects.isNull(response)) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> deleteWorkType(@PathVariable Long carId){
		//TODO write code

        return new ResponseEntity<>( HttpStatus.OK);
	} 
	

}
