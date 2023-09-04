package ua.com.harazh.oblik.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

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

import ua.com.harazh.oblik.domain.dto.CreateWorkCategoryDto;
import ua.com.harazh.oblik.domain.dto.UpdateWorkCategoryDto;
import ua.com.harazh.oblik.domain.dto.WorkCategoryResponseDto;
import ua.com.harazh.oblik.service.WorkCategoryService;

@RestController
@RequestMapping("/work_category")
public class WorkCategoryController {
	
	private WorkCategoryService workCategoryService;
	
	public WorkCategoryController(WorkCategoryService workCategoryService) {
		super();
		this.workCategoryService = workCategoryService;
	}
	
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SENIOR_USER')")
	public ResponseEntity<WorkCategoryResponseDto> createNewWorkCategory(@Valid @RequestBody CreateWorkCategoryDto workCategoryDto) {
		
		WorkCategoryResponseDto toReturn =  workCategoryService.createNewWorkCategory(workCategoryDto);
		
		return new ResponseEntity<>(toReturn, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<WorkCategoryResponseDto>> geWorkCategories(){
		List<WorkCategoryResponseDto> toReturn = workCategoryService.getAllWorkCategories();
		
        return new ResponseEntity<>(toReturn, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<WorkCategoryResponseDto> getWorkCategoryByid(@PathVariable Long id){
		WorkCategoryResponseDto toReturn = workCategoryService.getWorkCategoryBuId(id);
		
		if (Objects.isNull(toReturn)) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(toReturn, HttpStatus.OK);
	}
	@PutMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SENIOR_USER')")
	public ResponseEntity<WorkCategoryResponseDto> updateWorkCategory(@RequestBody UpdateWorkCategoryDto workCategoryDto, @PathVariable Long id){
		
		WorkCategoryResponseDto response = workCategoryService.updateWorkCategory(workCategoryDto, id);
		
		if (Objects.isNull(response)) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SENIOR_USER')")
	public ResponseEntity<?> deleteWorkType(@PathVariable Long id){
		//TODO write code
		boolean deleted = workCategoryService.deleteWorkCategory(id);
        return new ResponseEntity<>( HttpStatus.OK);
	} 

}
