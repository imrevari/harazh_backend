package ua.com.harazh.oblik.service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.harazh.oblik.domain.*;
import ua.com.harazh.oblik.domain.dto.ResponseCompletedWorkDto;
import ua.com.harazh.oblik.domain.dto.ResponseOrderDto;
import ua.com.harazh.oblik.domain.dto.ResponseWorkDto;
import ua.com.harazh.oblik.domain.dto.UsersAndTimeframeDto;
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

	@Value( "${work.cementBeforeDone}" )
	private boolean CEMENT_BEFORE_DONE;

	@Value( "${work.showNonClosedOrderWorks}" )
	private boolean SHOW_NON_CLOSED_ORED_WORKS;

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
			Work work = new Work(workTypeOpt.get(), CEMENT_BEFORE_DONE, user);
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
	
	public ResponseWorkDto copyDataFromWorkType(Work work) {
		
		work.setPrice(work.getWorkType().getPrice());
		work.setWorkName(work.getWorkType().getName());

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
	
	public List<ResponseCompletedWorkDto> getAllDoneWorksByUser(OblikUser user){
		
		List<Work> listOfWorksDoneByUser = workRepository.findByOblikUserAndWorkDone(user, true);
		
		List<ResponseCompletedWorkDto> listToReturn= extendWorksWithOrder(listOfWorksDoneByUser);

		Collections.sort(listToReturn, Comparator.comparing((ResponseCompletedWorkDto a) -> LocalDateTime.parse(a.getOrderClosed())));
		
		return listToReturn;
	}

	public List<ResponseCompletedWorkDto> getAllDoneWorksByUsersAndDates(List<OblikUser> users, UsersAndTimeframeDto usersAndTimeframeDto) {

		List<Work> listOfWorksDoneByUsers = workRepository.findByOblikUserInAndDoneAtBetweenAndWorkDoneOrderByDoneAtAsc(
				users,
				LocalDateTime.parse(usersAndTimeframeDto.getFromDate()),
			    LocalDateTime.parse(usersAndTimeframeDto.getToDate()),
				true);
		List<ResponseCompletedWorkDto> listToReturn = extendWorksWithOrder(listOfWorksDoneByUsers);
		return listToReturn;
	}

	public void removeWorkCategoriesFromWorks(WorkType workType) {
		List<Work> works = workRepository.findByWorkType(workType);
		works.forEach(work -> {work.setWorkType(null);
			workRepository.save(work);}
			);
	}
	
	private Work getWorkByIdOrThrowError(Long id) {
		Optional<Work> workOpt = workRepository.findById(id);
		
		if (!workOpt.isPresent()) {
			throw new ExceptionWithMessage(wrongWorkIdMessage, "id");
		}
		return workOpt.get();
	}
	
	private ResponseOrderDto gerOrderDtoByWorkId(Long Id) {
		
		Long orderId = (!SHOW_NON_CLOSED_ORED_WORKS) ? orderRepository.findClosedOrderidByWorkId(Id) :
				orderRepository.findOrderidByWorkId(Id);
		if(Objects.isNull(orderId)) {
			return null;
		}
		Optional<RepairOrder> repairOrder = orderRepository.findById(orderId);
		return new ResponseOrderDto(repairOrder.get());
	}

	private List<ResponseCompletedWorkDto> extendWorksWithOrder(List<Work> listOfWorks){
		List<ResponseCompletedWorkDto> listToReturn= new ArrayList<>();
		for (Work work : listOfWorks) {
			ResponseOrderDto responseOrderDto = gerOrderDtoByWorkId(work.getId());
			if (!Objects.isNull(responseOrderDto)) {
				listToReturn.add(new ResponseCompletedWorkDto(work, responseOrderDto));
			}
		}
		return listToReturn;
	}
}
