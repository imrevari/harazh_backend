package ua.com.harazh.oblik.domain.dto;

import ua.com.harazh.oblik.domain.Part;

public class PurchasePartResponseDto extends RetailPartResponseDto{
	
	
	private Double purchasePrice;

	public PurchasePartResponseDto() {
		super();
	}

	public PurchasePartResponseDto(Part part) {
		super(part);
		this.purchasePrice = part.getPurchasePrice();
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	
	
	

}
