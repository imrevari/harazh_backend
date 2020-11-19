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

import ua.com.harazh.oblik.domain.dto.CreatePartPurchasePriceDto;
import ua.com.harazh.oblik.domain.dto.NameDto;
import ua.com.harazh.oblik.domain.dto.PurchasePartResponseDto;
import ua.com.harazh.oblik.domain.dto.RetailPartResponseDto;
import ua.com.harazh.oblik.domain.dto.UpdatePartPurchasePriceDto;
import ua.com.harazh.oblik.service.PartService;

@RestController
@RequestMapping("/parts")
public class PartController {
	
	
	private PartService partService;

	
	@Autowired
	public PartController(PartService partService) {
		super();
		this.partService = partService;
	}
	
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PurchasePartResponseDto> createNewPart(@Valid @RequestBody CreatePartPurchasePriceDto partDto) {
		PurchasePartResponseDto partToReturn = partService.save(partDto);
    
	    return new ResponseEntity<>(partToReturn, HttpStatus.CREATED);       
    }
	
	@GetMapping
	public ResponseEntity<List<RetailPartResponseDto>> getAllPartsWithRetailPrice() {
        List<RetailPartResponseDto> productListItems = partService.getAllPartsWithRetailPrice();

        return new ResponseEntity<>(productListItems, HttpStatus.OK);
    }
	
	@GetMapping("/{partId}")
	public ResponseEntity<RetailPartResponseDto> getPartWithRetailPriceById(@PathVariable Long partId){
		
		RetailPartResponseDto partToReturn = partService.getPartById(partId);
        
        return objectToResponseOrNotFound(partToReturn);
	}
	
	//TODO for admin only
	@GetMapping("/id/{partId}")
	public ResponseEntity<PurchasePartResponseDto> getPartWithPurchasePriceById(@PathVariable Long partId){
		
		PurchasePartResponseDto partToReturn = partService.getPartByIdWithPurchasePrice(partId);
        
        return objectToResponseOrNotFound(partToReturn);
	}
	
	@GetMapping("/name")
	public ResponseEntity<List<RetailPartResponseDto>> getPartsWithRetailPriceByName(@RequestBody NameDto name){
		List<RetailPartResponseDto> partsToReturn = partService.getPartsByName(name.getName());
		
		if (partsToReturn.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(partsToReturn, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<PurchasePartResponseDto> updatePart(@RequestBody UpdatePartPurchasePriceDto createPartPurchasePriceDto, @PathVariable Long id){
		
		PurchasePartResponseDto part = partService.updatePart(createPartPurchasePriceDto, id);
		
		return objectToResponseOrNotFound(part);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> deletePart(@PathVariable Long carId){
		//TODO write code

        return new ResponseEntity<>( HttpStatus.OK);
	}
	
	
	
	private <T>  ResponseEntity<T> objectToResponseOrNotFound(T dto){
		if (Objects.isNull(dto)) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
}
