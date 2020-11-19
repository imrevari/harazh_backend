package ua.com.harazh.oblik.controller;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ua.com.harazh.oblik.domain.dto.AddPartCountDto;
import ua.com.harazh.oblik.domain.dto.CreateOrderDto;
import ua.com.harazh.oblik.domain.dto.ResponseOrderDto;
import ua.com.harazh.oblik.service.OrderService;
import ua.com.harazh.oblik.validator.order.AddPartCountValidator;
import ua.com.harazh.oblik.validator.order.CreateOrdervalidator;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private OrderService orderService;
	
	private CreateOrdervalidator createOrdervalidator;
	
	private AddPartCountValidator addPartCountValidator;
	

	@Autowired
	public OrderController(OrderService orderService, CreateOrdervalidator createOrdervalidator,
			AddPartCountValidator addPartCountValidator) {
		super();
		this.orderService = orderService;
		this.createOrdervalidator = createOrdervalidator;
		this.addPartCountValidator = addPartCountValidator;
	}
	
	@InitBinder("createOrderDto")
    protected void initNewOrderBinder(WebDataBinder binder) {
        binder.addValidators(createOrdervalidator);
    }
	
	@InitBinder("addPartCountDto")
    protected void initAddPartCountBinder(WebDataBinder binder) {
        binder.addValidators(addPartCountValidator);
    }



	@PostMapping
	public ResponseEntity<ResponseOrderDto> createNewOrder(@Valid @RequestBody CreateOrderDto createOrderDto){
		
		ResponseOrderDto dto = orderService.createNewOrder(createOrderDto);
		
		if (Objects.isNull(dto)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@GetMapping("/car/open/{id}")
	public ResponseEntity<List<ResponseOrderDto>> getOpenedOrdersByCar(@PathVariable Long id){
		
		List<ResponseOrderDto> orders = orderService.openedOrdersByCar(id);
		
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/car/all/{id}")
	public ResponseEntity<List<ResponseOrderDto>> getAllOrdersByCar(@PathVariable Long id){
		
		List<ResponseOrderDto> orders = orderService.allOrdersByCar(id);
		
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/car/closed/{id}")
	public ResponseEntity<List<ResponseOrderDto>> getClosedOrdersByCar(@PathVariable Long id){
		
		List<ResponseOrderDto> orders = orderService.closedOrdersByCar(id);
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/customer/open/{id}")
	public ResponseEntity<List<ResponseOrderDto>> getOpenedOrdersByCustomer(@PathVariable Long id){
		
		List<ResponseOrderDto> orders = orderService.openedOrdersByCustomer(id);
		
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/customer/all/{id}")
	public ResponseEntity<List<ResponseOrderDto>> getAllOrdersByCustomer(@PathVariable Long id){
		
		List<ResponseOrderDto> orders = orderService.allOrdersByCustomer(id);
		
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/customer/closed/{id}")
	public ResponseEntity<List<ResponseOrderDto>> getClosedOrdersByCustomer(@PathVariable Long id){
		
		List<ResponseOrderDto> orders = orderService.closedOrdersByCustomer(id);
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseOrderDto> getOrderById(@PathVariable Long id){
		
		ResponseOrderDto order = orderService.getOrderDtoById(id);
		
		if (Objects.isNull(order)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ResponseOrderDto>> getLastTenOpenedOrders(){
		
		List<ResponseOrderDto> orders = orderService.getLastTenOpenedOrders();
		
		
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	
	@PostMapping("/work/{workId}/{orderId}")
	public ResponseEntity<ResponseOrderDto> addWork(@PathVariable Long workId, @PathVariable Long orderId){
	
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();

		ResponseOrderDto responseOrderDto = orderService.addWork(workId, orderId, user.getUsername());
		
		return new ResponseEntity<>(responseOrderDto, HttpStatus.OK);
	}
	
	@PostMapping("/part/{orderId}")
	public ResponseEntity<ResponseOrderDto> addPartCount(@PathVariable Long orderId, @Valid @RequestBody AddPartCountDto addPartCountDto){
		
		ResponseOrderDto responseOrderDto = orderService.addPartCount(orderId, addPartCountDto);
		

		if (Objects.isNull(responseOrderDto)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(responseOrderDto, HttpStatus.OK);
	}
	
	
	@GetMapping("/total_to_pay/{id}")
	public ResponseEntity<Double> totalToPay (@PathVariable Long id){
		
		Double totalToPay = orderService.getTotalToPay(id);
		
		return new ResponseEntity<>(totalToPay, HttpStatus.OK);
	}
	
	@GetMapping("/close/{id}")
	public ResponseEntity<ResponseOrderDto> closeOrder(@PathVariable Long id){
		
		ResponseOrderDto totalToPay = orderService.closeOrder(id);
		
		return new ResponseEntity<>(totalToPay, HttpStatus.OK);
	}
	
	@PostMapping("/pay_for/{id}")
	public ResponseEntity<ResponseOrderDto> payFor(@PathVariable Long id){
		
		ResponseOrderDto totalToPay = orderService.payFor(id);
		
		if (Objects.isNull(totalToPay)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(totalToPay, HttpStatus.OK);
	}
	
	@PostMapping("/close_work/{orderId}/{workId}")
	public ResponseEntity<ResponseOrderDto> closeWork(@PathVariable Long orderId, @PathVariable Long workId){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) authentication.getPrincipal();
		
		ResponseOrderDto orderWithClosedWork = orderService.closeWork(orderId, workId, user.getUsername());
		
		if (Objects.isNull(orderWithClosedWork)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(orderWithClosedWork, HttpStatus.OK);
	}
	
	@DeleteMapping("/remove_work/{orderId}/{workId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ResponseOrderDto> removeWork(@PathVariable Long orderId, @PathVariable Long workId){
		ResponseOrderDto responseOrderDto = orderService.removeWork(orderId, workId);
		
		if (Objects.isNull(responseOrderDto)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(responseOrderDto, HttpStatus.OK);
	}

	@DeleteMapping("/remove_part/{orderId}/{partId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ResponseOrderDto> removePartCount(@PathVariable Long orderId, @PathVariable Long partId){
		ResponseOrderDto responseOrderDto = orderService.removePartCount(orderId, partId);
		
		if (Objects.isNull(responseOrderDto)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(responseOrderDto, HttpStatus.OK);
	}
	
	
	
	
	

}
