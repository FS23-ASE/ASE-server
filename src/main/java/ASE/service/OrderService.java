package ASE.service;

import ASE.entity.Order;
import ASE.repository.BookRepository;
import ASE.repository.CartRepository;
import ASE.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(@Qualifier("orderRepository") OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderById(long id){
        return this.orderRepository.findById(id);
    }

    public Order getOrderByBuyerId(long buyerId){
        return this.orderRepository.findByBuyId(buyerId);
    }

    public Order getOrderBySellerId(long sellerId){
        return this.orderRepository.findBySellerId(sellerId);
    }

    public Order createOrder(Order newOrder){

        newOrder = orderRepository.save(newOrder);

        orderRepository.flush();

        log.debug("Created Information for Order: {}", newOrder);
        return newOrder;

    }




}
