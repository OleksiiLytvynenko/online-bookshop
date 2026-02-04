package me.krumka.onlinebookshop.repository.orderitem;

import me.krumka.onlinebookshop.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);
}
