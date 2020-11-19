package ua.com.harazh.oblik.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.harazh.oblik.domain.Part;
import ua.com.harazh.oblik.domain.PartCount;
import ua.com.harazh.oblik.domain.RepairOrder;
import ua.com.harazh.oblik.domain.dto.ResponsePartCountDto;
//import ua.com.harazh.oblik.exception.ExceptionWithMessage;
import ua.com.harazh.oblik.repository.OrderRepository;
import ua.com.harazh.oblik.repository.PartCountRepository;
import ua.com.harazh.oblik.repository.PartRepository;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class PartCountService {
	
	
	private PartCountRepository partCountRepository;
	private PartRepository partRepository;
	private OrderRepository orderRepository;
	
	@Value("${amount.smaller}")
	private String smallerAmountMessage;
	


	@Autowired
	public PartCountService(PartCountRepository partCountRepository, PartRepository partRepository,
			OrderRepository orderRepository) {
		super();
		this.partCountRepository = partCountRepository;
		this.partRepository = partRepository;
		this.orderRepository = orderRepository;
	}
	
	
	public PartCount createAndSaveNewPartCount(Long partId, Long orderId) {
		Optional<Part> part = partRepository.findById(partId);
		Optional<RepairOrder> order = orderRepository.findById(orderId);
		
		if (part.isPresent() && order.isPresent()) {
			PartCount partCount = new PartCount(part.get(), order.get());
			
			return partCountRepository.save(partCount);
		}
		
		//TODO inform of wrong ID
		return null;
	}
	
	public PartCount createAndSaveNewPartCountWithNumberOfUnits(Part part, RepairOrder order, Integer numberOfUnits) {
		
			PartCount partCount = new PartCount(part, order, numberOfUnits);
			
			return partCountRepository.save(partCount);
		
	}
	
	public PartCount addOneUnit(Long partCountId) {
		Optional<PartCount> part = partCountRepository.findById(partCountId);
		
		if (!part.isPresent()) {
			return null;
		}
		PartCount partCount = part.get();
		partCount.setAmount(partCount.getAmount() + 1);
		
		return partCountRepository.save(partCount);
	}
	
	public ResponsePartCountDto changeAmount(Long partCountId, Integer newAmount) {
		Optional<PartCount> part = partCountRepository.findById(partCountId);
		
		if (!part.isPresent() && newAmount < 1) {
			return null;
		}
		PartCount partCount = part.get();
		partCount.setAmount(newAmount);
		
		return new ResponsePartCountDto (partCountRepository.save(partCount));
	}
	
	public PartCount increaseAmount(PartCount partCount, Integer newAmount) {
	
//		if (newAmount < partCount.getAmount() ) {
//			throw new ExceptionWithMessage(smallerAmountMessage);
//		}
		partCount.setAmount(newAmount + partCount.getAmount());
		
		return partCountRepository.save(partCount);
	}
	
	public ResponsePartCountDto setPartToNullAndCopyData(PartCount partCount) {
	
		partCount.setPartName(partCount.getPart().getName());
		partCount.setRetailPrice(partCount.getPart().getRetailPrice());
		partCount.setPart(null);
		
		return new ResponsePartCountDto (partCountRepository.save(partCount));
	}

	//TODO to be used by admin only
	public boolean deletePartCount(Long id) {
		Optional<PartCount> part = partCountRepository.findById(id);
		
		if (!part.isPresent()) {
			return false;
		}
		partCountRepository.delete(part.get());
		return true;
	}
	



	
	

}
