package ua.com.harazh.oblik.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.harazh.oblik.domain.OblikUser;
import ua.com.harazh.oblik.domain.RepairOrder;
import ua.com.harazh.oblik.domain.Work;
import ua.com.harazh.oblik.domain.WorkType;
import ua.com.harazh.oblik.domain.dto.ResponseCompletedWorkDto;
import ua.com.harazh.oblik.domain.dto.ResponseOrderDto;
import ua.com.harazh.oblik.domain.dto.ResponseWorkDto;
import ua.com.harazh.oblik.exception.ExceptionWithMessage;
import ua.com.harazh.oblik.repository.OrderRepository;
import ua.com.harazh.oblik.repository.WorkRepository;
import ua.com.harazh.oblik.repository.WorkTypeRepository;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class WorkService {
	
	private WorkTypeRepository workTypeRepository;
	
	private WorkRepository workRepository;
	
	private OrderRepository orderRepository;
	
	@Value("${work.wrongId}")
	private String wrongWorkIdMessage;

	@Autowired
	public WorkService(WorkTypeRepository workTypeRepository, WorkRepository workRepository, OrderRepository orderRepository) {
		super();
		this.workTypeRepository = workTypeRepository;
		this.workRepository = workRepository;
		this.orderRepository = orderRepository;
	}
	
	
	public Work createAndSaveNewWork(Long workTypeId, OblikUser user) {
		Optional<WorkType> workTypeOpt = workTypeRepository.findById(workTypeId);
		
		if (workTypeOpt.isPresent()) {
			
			Work work = new Work(workTypeOpt.get());
			work.setOblikUser(user);
			return workRepository.save(work);
		}
		
		throw new ExceptionWithMessage(wrongWorkIdMessage, "workTypeId");
//		return null;
	}
	
	public ResponseWorkDto setWorkDone(Long workId) {
		
		Work work = getWorkByIdOrThrowError(workId);
		
		work.setWorkDone(true);
		
		return new ResponseWorkDto (workRepository.save(work));
		
	}
	
	public ResponseWorkDto setWorkTypeToNullAndCopyData(Work work) {
		
		work.setPrice(work.getWorkType().getPrice());
		work.setWorkName(work.getWorkType().getName());
		work.setWorkType(null);
		
		return new ResponseWorkDto (workRepository.save(work));
		
	}
	
	public boolean deleteWork(Long id) {
		
		Work work = getWorkByIdOrThrowError(id);
		
		workRepository.delete(work);
		
		return true;
	}
	
	public Work setUserForWork(OblikUser user, Long id) {
		Work work = getWorkByIdOrThrowError(id);
		
		work.setOblikUser(user);
		return workRepository.save(work);
	}
	
	public List<ResponseCompletedWorkDto> getAllClosedDoneWorksByUser(OblikUser user){
		
		List<Work> listOfWorksDoneByUser = workRepository.findByOblikUserAndWorkDone(user, true);
		
		List<ResponseCompletedWorkDto> listToReturn= new ArrayList<>();
		for (Work work : listOfWorksDoneByUser) {
			ResponseOrderDto responseOrderDto = gerOrderDtoByWorkId(work.getId());
			if (!Objects.isNull(responseOrderDto)) {
				listToReturn.add(new ResponseCompletedWorkDto(work, responseOrderDto));
			}
		}
		
		return listToReturn;
	}
	
	private Work getWorkByIdOrThrowError(Long id) {
		Optional<Work> workOpt = workRepository.findById(id);
		
		if (!workOpt.isPresent()) {
			throw new ExceptionWithMessage(wrongWorkIdMessage, "id");
		}
		return workOpt.get();
	}
	
	private ResponseOrderDto gerOrderDtoByWorkId(Long Id) {
		
		Long orderId = orderRepository.findClosedOrderidByWorkId(Id);
		if(Objects.isNull(orderId)) {
			return null;
		}
		Optional<RepairOrder> repairOrder = orderRepository.findById(orderId);
		return new ResponseOrderDto(repairOrder.get());
	}
	
	
	

}
