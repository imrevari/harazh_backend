package ua.com.harazh.oblik.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.com.harazh.oblik.domain.RepairOrder;

@Repository
public interface OrderRepository extends JpaRepository<RepairOrder, Long>{
	
	
	List<RepairOrder> findByCarIdAndOrderClosed(Long id, LocalDateTime orderClosed);
	
	
	@Query(value = "SELECT * from public.repair_order WHERE car_id = ?1 AND order_closed is not null", nativeQuery = true)
	List<RepairOrder> findByCarIdClosed(Long id);
	
	List<RepairOrder> findByCarId(Long id);
	
	List<RepairOrder> findByCustomerIdAndOrderClosed(Long id, LocalDateTime orderClosed);
	
	@Query(value = "SELECT * from public.repair_order WHERE customer_id = ?1 AND order_closed is not null", nativeQuery = true)
	List<RepairOrder> findByCustomerIdClosed(Long id);
	
	List<RepairOrder> findByCustomerId(Long id);
	
	@Query(value = "SELECT * FROM public.repair_order where order_closed is null ORDER BY id desc limit 10", nativeQuery = true)
	List<RepairOrder> findLastTenOpenedOrders();
	
	@Query(value = "SELECT repair_order.id FROM public.repair_order right join work on repair_order.id = work.repair_order_id where work.id = ?1 and repair_order.order_closed is not null", nativeQuery = true)
	Long findClosedOrderidByWorkId(Long id);
	
}
