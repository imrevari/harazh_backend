package ua.com.harazh.oblik.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PartCount {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//good connection
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "part_id")
	private Part part;
	
	@Column(nullable = false)
	private Integer amount;

	//good connection
	@ManyToOne
	@JoinColumn(name = "repair_order_id", nullable = false)
	private RepairOrder repairOrder;

	private String partName;

	private Double retailPrice;

	private Double purchasePrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oblik_user_id")
	private OblikUser oblikUser;
	
	

	public PartCount() {
		super();
	}

	

	public PartCount(Part part, RepairOrder repairOrder) {
		super();
		this.part = part;
		this.amount = 1;
		this.repairOrder = repairOrder;
		this.purchasePrice = part.getPurchasePrice();
		this.retailPrice = part.getRetailPrice();
	}
	
	public PartCount(Part part, RepairOrder repairOrder, Integer amount) {
		super();
		this.part = part;
		this.amount = amount;
		this.repairOrder = repairOrder;
		this.purchasePrice = part.getPurchasePrice();
		this.retailPrice = part.getRetailPrice();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Part getPart() {
		return part;
	}



	public void setPart(Part part) {
		this.part = part;
	}



	public Integer getAmount() {
		return amount;
	}



	public void setAmount(Integer amount) {
		this.amount = amount;
	}



	public RepairOrder getRepairOrder() {
		return repairOrder;
	}



	public void setRepairOrder(RepairOrder repairOrder) {
		this.repairOrder = repairOrder;
	}



	public String getPartName() {
		return partName;
	}



	public void setPartName(String partName) {
		this.partName = partName;
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

	public OblikUser getOblikUser() {
		return oblikUser;
	}

	public void setOblikUser(OblikUser oblikUser) {
		this.oblikUser = oblikUser;
	}
}
