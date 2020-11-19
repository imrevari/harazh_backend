package ua.com.harazh.oblik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.harazh.oblik.domain.WorkCategory;
import ua.com.harazh.oblik.domain.dto.CreateWorkCategoryDto;
import ua.com.harazh.oblik.domain.dto.UpdateWorkCategoryDto;
import ua.com.harazh.oblik.domain.dto.WorkCategoryResponseDto;
import ua.com.harazh.oblik.exception.ExceptionWithMessage;
import ua.com.harazh.oblik.repository.WorkCategoryRepository;

@Service
@Transactional
public class WorkCategoryService {
	
	
	private WorkCategoryRepository workCategoryRepository;

	
	@Autowired
	public WorkCategoryService(WorkCategoryRepository workCategoryRepository) {
		super();
		this.workCategoryRepository = workCategoryRepository;
	}


	public WorkCategoryResponseDto createNewWorkCategory(@Valid CreateWorkCategoryDto workCategoryDto) {
		
		WorkCategory workCategory = new WorkCategory(workCategoryDto);
		
		return new WorkCategoryResponseDto(workCategoryRepository.save(workCategory));
	}


	public List<WorkCategoryResponseDto> getAllWorkCategories() {
		
		List<WorkCategory> workCategories = workCategoryRepository.findAll();
		
		if(workCategories.isEmpty()) {
			return new ArrayList<WorkCategoryResponseDto>();
		}
		
		return workCategories.stream().map((e) -> new WorkCategoryResponseDto(e)).collect(Collectors.toList());
	}


	public WorkCategoryResponseDto updateWorkCategory(UpdateWorkCategoryDto workCategoryDto, Long id) {
		Optional<WorkCategory> optional = workCategoryRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new ExceptionWithMessage("woring id", "name");
		}
		WorkCategory workCategory = optional.get();
		workCategory.setCategoryName(workCategoryDto.getCategoryName());
		
		return new WorkCategoryResponseDto(workCategoryRepository.save(workCategory));
	}


	public WorkCategoryResponseDto getWorkCategoryBuId(Long id) {
		Optional<WorkCategory> optional = workCategoryRepository.findById(id);
		if(!optional.isPresent()) {
			throw new ExceptionWithMessage("woring id", "name");
		}
		
		return new WorkCategoryResponseDto(optional.get());
	}

	
	
	

}
