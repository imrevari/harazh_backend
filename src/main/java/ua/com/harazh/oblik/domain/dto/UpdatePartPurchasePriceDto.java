package ua.com.harazh.oblik.domain.dto;

public class UpdatePartPurchasePriceDto {
	
	private String name;
	
	private String description;

	private Double retailPrice;
	
	private Double purchasePrice;

	public UpdatePartPurchasePriceDto() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	
	

}
