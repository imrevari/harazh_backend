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

import ua.com.harazh.oblik.domain.Part;
import ua.com.harazh.oblik.domain.dto.CreatePartPurchasePriceDto;
import ua.com.harazh.oblik.domain.dto.PurchasePartResponseDto;
import ua.com.harazh.oblik.domain.dto.RetailPartResponseDto;
import ua.com.harazh.oblik.domain.dto.UpdatePartPurchasePriceDto;
import ua.com.harazh.oblik.exception.ExceptionWithMessage;
import ua.com.harazh.oblik.repository.PartRepository;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class PartService {
	
	private PartRepository partRepository;
	
	@Value("${part.wrongId}")
	private String wrongPartIdMessage;

	
	@Autowired
	public PartService(PartRepository partRepository) {
		super();
		this.partRepository = partRepository;
	}
	
	
	public PurchasePartResponseDto save(CreatePartPurchasePriceDto part) {
		
		Part toReturn = partRepository.save(new Part(part));
		
		return new PurchasePartResponseDto(toReturn);
	}
	
	public List<RetailPartResponseDto> getAllPartsWithRetailPrice(){
		List<Part> partsList = partRepository.findAll();
		
		return partsToDtoOrNewLits(partsList);
	}

	public RetailPartResponseDto getPartById(Long partId) {
		Optional<Part> part = partRepository.findById(partId);
		
		return partOptionalToDtoOrNull(part);
		
	}
	
	public PurchasePartResponseDto getPartByIdWithPurchasePrice(Long partId) {
		Optional<Part> part = partRepository.findById(partId);
		
		return part.map((e) -> new PurchasePartResponseDto(e)).orElse(null);
	}

	public List<RetailPartResponseDto> getPartsByName(String name) {
		List<Part> parts = partRepository.findByNameContaining(name);
		return partsToDtoOrNewLits(parts);
	}

	public PurchasePartResponseDto updatePart(UpdatePartPurchasePriceDto createPartPurchasePriceDto, Long id) {
		Optional<Part> part = partRepository.findById(id);
		
		if (!part.isPresent()) {
			throw new ExceptionWithMessage(wrongPartIdMessage, "name");
		}
		Part partToRetunr = updateAndSave(part, createPartPurchasePriceDto);
		
		return new PurchasePartResponseDto(partToRetunr);
	}
	
	
	
	
	private List<RetailPartResponseDto> partsToDtoOrNewLits(List<Part> parts){
		if(parts.isEmpty()) {
			return new ArrayList<RetailPartResponseDto>();
		}
		
		return parts.stream().map((element) -> new RetailPartResponseDto(element)).collect(Collectors.toList());
	}
	
	private RetailPartResponseDto partOptionalToDtoOrNull(Optional<Part> part) {
			return part.map((e) -> new RetailPartResponseDto(e)).orElse(null);
	}
	
	private Part updateAndSave(Optional<Part> part, UpdatePartPurchasePriceDto createPartPurchasePriceDto) {
		Part partToRetunr = part.get();
		if (!Objects.isNull(createPartPurchasePriceDto.getName())) { partToRetunr.setName(createPartPurchasePriceDto.getName());}
		if (!Objects.isNull(createPartPurchasePriceDto.getDescription())) { partToRetunr.setDescription(createPartPurchasePriceDto.getDescription());}
		if (!Objects.isNull(createPartPurchasePriceDto.getPurchasePrice())) { partToRetunr.setPurchasePrice(createPartPurchasePriceDto.getPurchasePrice());}
		if (!Objects.isNull(createPartPurchasePriceDto.getRetailPrice())) { partToRetunr.setRetailPrice(createPartPurchasePriceDto.getRetailPrice());}

		return partRepository.save(partToRetunr);
	}

	
}
