package ua.com.harazh.oblik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.harazh.oblik.domain.WorkCategory;
import ua.com.harazh.oblik.domain.WorkType;
import ua.com.harazh.oblik.domain.dto.CreateWorkTypeDto;
import ua.com.harazh.oblik.domain.dto.UpdateWorkTypeDto;
import ua.com.harazh.oblik.domain.dto.WorkTypeResponseDto;
import ua.com.harazh.oblik.exception.ExceptionWithMessage;
import ua.com.harazh.oblik.repository.WorkCategoryRepository;
import ua.com.harazh.oblik.repository.WorkTypeRepository;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class WorkTypeService {
	
	
	private WorkTypeRepository workTypeRepository;
	
	private WorkCategoryRepository workCategoryRepository; 
	
	@Value("${work.wrongId}")
	private String wrongWorkIdMessage;
	
	
	
	@Autowired
	public WorkTypeService(WorkTypeRepository workTypeRepository, WorkCategoryRepository workCategoryRepository) {
		super();
		this.workTypeRepository = workTypeRepository;
		this.workCategoryRepository = workCategoryRepository;
	}




	public WorkTypeResponseDto createNewWorkType(CreateWorkTypeDto workTypeDto) {
			
			Optional<WorkCategory> workCategory = workCategoryRepository.findById(workTypeDto.getWorkCategory());
			WorkType workType = new WorkType(workTypeDto);
			workType.setWorkCategory(workCategory.get());
			WorkType toReturn = workTypeRepository.save(workType);

			return new WorkTypeResponseDto(toReturn);
	}




	public List<WorkTypeResponseDto> getAllWorkTypes() {
		
		List<WorkType> workTypes =  workTypeRepository.findAll();
		
		if(workTypes.isEmpty()) {
			return new ArrayList<WorkTypeResponseDto>();
		}
		
		return workTypes.stream().map((e) -> new WorkTypeResponseDto(e)).collect(Collectors.toList());
	}




	public WorkTypeResponseDto updateWorkType(UpdateWorkTypeDto workTypeDto, Long id) {
		Optional<WorkType> optional = workTypeRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new ExceptionWithMessage(wrongWorkIdMessage, "name");
		}
		
		WorkType workType = updateAndSave(optional, workTypeDto);
		
		return new WorkTypeResponseDto(workType);
	}
	
	
	public WorkTypeResponseDto getWorkTypeById(Long id) {
		Optional<WorkType> optional = workTypeRepository.findById(id);
		
		if(!optional.isPresent()) {
			throw new ExceptionWithMessage(wrongWorkIdMessage, "name");
		}
		
		return new WorkTypeResponseDto(optional.get());
	}
	
	
	private WorkType updateAndSave(Optional<WorkType> optional, UpdateWorkTypeDto workTypeDto) {
		WorkType workType = optional.get();
		if (!Objects.isNull(workTypeDto.getName())) { workType.setName(workTypeDto.getName().trim());}
		if (!Objects.isNull(workTypeDto.getDescription())) { workType.setDescription(workTypeDto.getDescription());}
		if (!Objects.isNull(workTypeDto.getPrice())) { workType.setPrice(workTypeDto.getPrice());}
		
		if (!Objects.isNull(workTypeDto)) {
			Optional<WorkCategory> workCategoryOptional = workCategoryRepository.findById(workTypeDto.getWorkCategory());
			workType.setWorkCategory(workCategoryOptional.get());}

		return workTypeRepository.save(workType);
	}





}
