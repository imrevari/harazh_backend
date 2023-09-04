package ua.com.harazh.oblik.domain;

import javax.persistence.*;

import ua.com.harazh.oblik.domain.dto.CreatePartPurchasePriceDto;
import ua.com.harazh.oblik.domain.dto.PartSupplier;

@Entity
public class Part {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@Column(nullable = false)
	private Double retailPrice;

	@Column(nullable = false)
	private Double purchasePrice;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "part_supplier_id")
	private PartSupplier partSupplier;
	

	public Part() {
		super();
	}

	
	
	public Part(CreatePartPurchasePriceDto dto) {
		super();
		this.name = dto.getName().trim();
		this.description = dto.getDescription();
		this.retailPrice = dto.getRetailPrice();
		this.purchasePrice = dto.getPurchasePrice();
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}


	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public PartSupplier getPartSupplier() {
		return partSupplier;
	}

	public void setPartSupplier(PartSupplier partSupplier) {
		this.partSupplier = partSupplier;
	}
}
