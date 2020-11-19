package ua.com.harazh.oblik.service;

import java.time.LocalDateTime;
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

import ua.com.harazh.oblik.domain.Car;
import ua.com.harazh.oblik.domain.Customer;
import ua.com.harazh.oblik.domain.OblikUser;
import ua.com.harazh.oblik.domain.Part;
import ua.com.harazh.oblik.domain.PartCount;
import ua.com.harazh.oblik.domain.RepairOrder;
import ua.com.harazh.oblik.domain.Work;
import ua.com.harazh.oblik.domain.dto.AddPartCountDto;
import ua.com.harazh.oblik.domain.dto.CreateOrderDto;
import ua.com.harazh.oblik.domain.dto.ResponseOrderDto;
import ua.com.harazh.oblik.exception.ExceptionWithMessage;
import ua.com.harazh.oblik.repository.CarRepository;
import ua.com.harazh.oblik.repository.CustomerRepository;
import ua.com.harazh.oblik.repository.OrderRepository;
import ua.com.harazh.oblik.repository.PartCountRepository;
import ua.com.harazh.oblik.repository.PartRepository;
import ua.com.harazh.oblik.repository.UserRepository;
import ua.com.harazh.oblik.repository.WorkRepository;

@Service
@Transactional
@PropertySource("classpath:messages.properties")
public class OrderService {
	
	private OrderRepository orderRepository;
	
	private CarRepository carRepository;
	
	private CustomerRepository customerRepository;
	
	private WorkService workService;
	
	private PartCountService partCountService;
	
	private PartRepository partRepository;
	
	private WorkRepository workRepository;
	
	private PartCountRepository partCountRepository;
	
	private UserRepository userRepository;
	
	@Value("${car.wrongId}")
	private String wrongCarIdMessage;
	
	@Value("${cust.wrongId}")
	private String wrongCustomerIdMessage;
	
	@Value("${order.wrongId}")
	private String wrongOrderIdMessage;
	
	@Value("${work.wrongId}")
	private String wrongWorkIdMessage;
	
	@Value("${part.orderClosed}")
	private String orderClosedMessage;

	@Autowired
	public OrderService(OrderRepository orderRepository, CarRepository carRepository,
			CustomerRepository customerRepository, WorkService workService, PartCountService partCountService,
			PartRepository partRepository, WorkRepository workRepository, PartCountRepository partCountRepository,
			UserRepository userRepository) {
		super();
		this.orderRepository = orderRepository;
		this.carRepository = carRepository;
		this.customerRepository = customerRepository;
		this.workService = workService;
		this.partCountService = partCountService;
		this.partRepository = partRepository;
		this.workRepository = workRepository;
		this.partCountRepository = partCountRepository;
		this.userRepository = userRepository;
	}

	public ResponseOrderDto createNewOrder(CreateOrderDto createOrderDto) {
		
		Optional<Car> car = carRepository.findById(createOrderDto.getCar());	
			
		Optional<Customer> customer = customerRepository.findById(createOrderDto.getCustomer());	
		
		RepairOrder order = new RepairOrder(customer.get(), car.get());
		order.setAmountPayedInAdvance(Objects.isNull(createOrderDto.getAmountPayedInAdvance()) ? 0 : createOrderDto.getAmountPayedInAdvance() );
		order.setProblem(createOrderDto.getProblem());
		
		RepairOrder newOrder = orderRepository.save(order);

		return new ResponseOrderDto(newOrder);
	}

	public List<ResponseOrderDto> openedOrdersByCar(Long id) {
		
		ifCarExists(id);
		
		List<RepairOrder> listOfOrders = orderRepository.findByCarIdAndOrderClosed(id, null);
		
		if(listOfOrders.isEmpty()) {
			return new ArrayList<ResponseOrderDto>(); 
		}
		
		return listOfOrders.stream().map((e) -> new ResponseOrderDto(e)).collect(Collectors.toList());
	}

	public List<ResponseOrderDto> allOrdersByCar(Long id) {
		
		ifCarExists(id);
		
		List<RepairOrder> listOfOrders = orderRepository.findByCarId(id);
		
		if(listOfOrders.isEmpty()) {
			return new ArrayList<ResponseOrderDto>(); 
		}
		
		return listOfOrders.stream().map((e) -> new ResponseOrderDto(e)).collect(Collectors.toList());
	}
	
	public List<ResponseOrderDto> closedOrdersByCar(Long id) {
		
		ifCarExists(id);

		List<RepairOrder> listOfOrders = orderRepository.findByCarIdClosed(id);
		
		if(listOfOrders.isEmpty()) {
			return new ArrayList<ResponseOrderDto>(); 
		}
		
		return listOfOrders.stream().map((e) -> new ResponseOrderDto(e)).collect(Collectors.toList());
	}
	
	public List<ResponseOrderDto> openedOrdersByCustomer(Long id) {

		ifCustomerExists(id);
		
		List<RepairOrder> listOfOrders = orderRepository.findByCustomerIdAndOrderClosed(id, null);
		
		if(listOfOrders.isEmpty()) {
			return new ArrayList<ResponseOrderDto>(); 
		}
		
		return listOfOrders.stream().map((e) -> new ResponseOrderDto(e)).collect(Collectors.toList());
	}
	
	public List<ResponseOrderDto> allOrdersByCustomer(Long id) {

		ifCustomerExists(id);
		
		List<RepairOrder> listOfOrders = orderRepository.findByCustomerId(id);
		
		if(listOfOrders.isEmpty()) {
			return new ArrayList<ResponseOrderDto>(); 
		}
		
		return listOfOrders.stream().map((e) -> new ResponseOrderDto(e)).collect(Collectors.toList());
		
	}
	
	public List<ResponseOrderDto> closedOrdersByCustomer(Long id) {
		
		ifCustomerExists(id);
		
		List<RepairOrder> listOfOrders = orderRepository.findByCustomerIdClosed(id);
		
		if(listOfOrders.isEmpty()) {
			return new ArrayList<ResponseOrderDto>(); 
		}
		
		return listOfOrders.stream().map((e) -> new ResponseOrderDto(e)).collect(Collectors.toList());
	}
	
	public List<ResponseOrderDto> getLastTenOpenedOrders() {
		
		List<RepairOrder> listOfOrders = orderRepository.findLastTenOpenedOrders();
		
		if(listOfOrders.isEmpty()) {
			return new ArrayList<ResponseOrderDto>(); 
		}
		
		return listOfOrders.stream().map((e) -> new ResponseOrderDto(e)).collect(Collectors.toList());
	}

	
	public ResponseOrderDto addWork(Long workId, Long orderId, String userName) {
		
		Optional<RepairOrder> orderOptional = orderRepository.findById(orderId);
		
		if(!orderOptional.isPresent()) {
			throw new ExceptionWithMessage(wrongOrderIdMessage, "orderId");
		}
		
		Optional<OblikUser> user = userRepository.findByName(userName);
		
		Work work = workService.createAndSaveNewWork(workId, user.get());
		
//		if (Objects.isNull(work)) {
//			throw new ExceptionWithMessage(wrongWorkIdMessage);
//		}
		RepairOrder order = orderOptional.get();
		
		order.getWorks().add(work);
		
		RepairOrder toReturn = orderRepository.save(order);
		
		return new ResponseOrderDto(toReturn);
	}
	
	public ResponseOrderDto addPartCount(Long orderId, AddPartCountDto addPartCountDto) {
		
		Optional<RepairOrder> orderOptional = orderRepository.findById(orderId);
			
		if (!orderOptional.isPresent()) {
			throw new ExceptionWithMessage(wrongOrderIdMessage, "orderId");
		}
		//cannot add new part to closed orders
		if( !Objects.isNull(orderOptional.get().getOrderClosed())) {
			throw new ExceptionWithMessage(orderClosedMessage, "orderId");
		}
		
		RepairOrder order = orderOptional.get();
		
		// if part exists, update it
		for(PartCount partCount : order.getPartCounts()) {
			if(partCount.getPart().getId() == addPartCountDto.getPartId()) {
				partCountService.increaseAmount(partCount, addPartCountDto.getAmount());
				return new ResponseOrderDto(order);
			}
		}
		
		Optional<Part> part = partRepository.findById(addPartCountDto.getPartId());
		
		PartCount newPartCount = partCountService.createAndSaveNewPartCountWithNumberOfUnits(part.get(), order, addPartCountDto.getAmount());
	
		order.getPartCounts().add(newPartCount);	
		
		RepairOrder toReturn = orderRepository.save(order);
		
		return new ResponseOrderDto(toReturn);
	}
	
	public Double getTotalToPay(Long id) {
		
		
		RepairOrder order = getOrderById(id);
		
		Double totalWorkDone = totalWorkDone(order);
		
		Double totalSpareParts = totalSparePart(order);
		
		return totalWorkDone + totalSpareParts - order.getAmountPayedInAdvance();
	}
	
	
	public ResponseOrderDto closeOrder(Long id) {
		
		RepairOrder order = getOrderById(id);
		
		if(!Objects.isNull(order.getOrderClosed())) {
			throw new ExceptionWithMessage(orderClosedMessage, "id");
		}
		
		order.setOrderClosed(LocalDateTime.now());
		
		if(!order.getPartCounts().isEmpty()) {
			closeAllPartCounts(order);
		}
		
		if(!order.getWorks().isEmpty()) {
			closeAllWorks(order);
		}

		return new ResponseOrderDto(order);
	}
	
	public ResponseOrderDto payFor(Long id) {
		
		RepairOrder order = getOrderById(id);
		
		order.setPayedFor(true);
		
		return new ResponseOrderDto(orderRepository.save(order));
	}
	
	public ResponseOrderDto removeWork(Long orderId, Long workId) {
		
		RepairOrder order = getOrderById(orderId);
		
		List<Work> copyOfWorks = new ArrayList<>();
		copyOfWorks.addAll(order.getWorks());				
		for(Work work : copyOfWorks) {
			if(work.getId() == workId) {
				order.getWorks().remove(work);
				workRepository.delete(work);
			}
		}
		return new ResponseOrderDto(orderRepository.save(order));
	}
	
	public ResponseOrderDto removePartCount(Long orderId, Long partId) {
		
		RepairOrder order = getOrderById(orderId);
		List<PartCount> parts = new ArrayList<>();
		parts.addAll(order.getPartCounts());
		
		for(PartCount part : parts){
			if(part.getId() == partId) {
				order.getPartCounts().remove(part);
				partCountRepository.delete(part);
			}
		}
		return new ResponseOrderDto(orderRepository.save(order));
	}
	
	public ResponseOrderDto closeWork(Long orderId, Long workId, String username) {
		
		RepairOrder order = getOrderById(orderId);
		
		Optional<OblikUser> user = userRepository.findByName(username);
		
		for (Work work : order.getWorks()) {
			if (work.getId() == workId) {
				work.setWorkDone(true);
				work.setOblikUser(user.get());
				workRepository.save(work);
//				workService.setWorkDone(workId);
				return new ResponseOrderDto(order);
			}
		}
		
		return null;
	}

	
	public ResponseOrderDto getOrderDtoById(Long id) {
		
		return new ResponseOrderDto(getOrderById(id));
	}	
	
	private void closeAllWorks(RepairOrder order) {
		for(Work work : order.getWorks()) {
			workService.setWorkTypeToNullAndCopyData(work);
		}
		
	}

	private void closeAllPartCounts(RepairOrder order) {		
		for(PartCount partCount : order.getPartCounts()) {
			partCountService.setPartToNullAndCopyData(partCount);
		}		
	}

	private Double totalSparePart(RepairOrder repairOrder) {
		
		if(Objects.isNull(repairOrder.getOrderClosed()) ) {
			return totalPartPriceForOpenedOrder(repairOrder);
		}
			return totalPartPriceForClosedOrder(repairOrder);
	}

	private Double totalPartPriceForClosedOrder(RepairOrder repairOrder) {
		Double toReturn = 0d;
		
		for(PartCount partCount : repairOrder.getPartCounts()) {
				toReturn += (partCount.getRetailPrice() * partCount.getAmount());
		}
		return toReturn;
	}

	private Double totalPartPriceForOpenedOrder(RepairOrder repairOrder) {
		Double toReturn = 0d;
		
		for(PartCount partCount : repairOrder.getPartCounts()) {
				toReturn += (partCount.getPart().getRetailPrice() * partCount.getAmount());
		}
		return toReturn;
	}

	private Double totalWorkDone(RepairOrder repairOrder) {

		if(Objects.isNull(repairOrder.getOrderClosed()) ) {
			return totalWorkDoneForOpenedOrder(repairOrder);
		}
			return totalWorkDoneForClosedOrder(repairOrder);
		
	}
	
	private Double totalWorkDoneForClosedOrder(RepairOrder repairOrder) {
		Double toReturn = 0d;
		
		for(Work work : repairOrder.getWorks()) {
			if (work.isWorkDone()) {
				toReturn += work.getPrice();
			}
		}
		return toReturn;
	}
	
	private Double totalWorkDoneForOpenedOrder(RepairOrder repairOrder) {
		Double toReturn = 0d;
		
		for(Work work : repairOrder.getWorks()) {
			if (work.isWorkDone()) {
				toReturn += work.getWorkType().getPrice();
			}
		}
		return toReturn;
	}

	private boolean ifCarExists(Long id){
		Optional<Car> car = carRepository.findById(id);	
		
		if (car.isPresent()) {
			return true;
		}
		throw new ExceptionWithMessage(wrongCarIdMessage, "carId");
		
	}
	
	private boolean ifCustomerExists(Long id){
		Optional<Customer> customer = customerRepository.findById(id);	
		
		if (customer.isPresent()) {
			return true;
		}
		throw new ExceptionWithMessage(wrongCustomerIdMessage, "carId");
	}
	
	private RepairOrder getOrderById(Long id) {
		Optional<RepairOrder> orderOptional = orderRepository.findById(id);
		
		if(!orderOptional.isPresent()) {
			throw new ExceptionWithMessage(wrongOrderIdMessage, "orderId");
		}
		
		return orderOptional.get();
		
	}

	

}
