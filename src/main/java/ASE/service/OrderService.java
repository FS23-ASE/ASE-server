package ASE.service;

import ASE.entity.Order;
import ASE.repository.BookRepository;
import ASE.repository.CartRepository;
import ASE.repository.OrderRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Order Service
 * This class is the "worker" and responsible for all functionality related to
 * the order
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back
 * to the caller.
 */
@Service
@Transactional
public class OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(@Qualifier("orderRepository") OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id  the ID of the order to retrieve
     * @return the retrieved order
     */
    public Order getOrderById(long id){
        return this.orderRepository.findById(id);
    }

    /**
     * Retrieves an order by the buyer's ID.
     *
     * @param buyerId  the ID of the buyer
     * @return the retrieved order
     */
    public List<Order> getOrderByBuyerId(long buyerId){
        return this.orderRepository.findByBuyerId(buyerId);
    }

    /**
     * Retrieves an order by the seller's ID.
     *
     * @param sellerId  the ID of the seller
     * @return the retrieved order
     */
    public List<Order> getOrderBySellerId(long sellerId){
        return this.orderRepository.findBySellerId(sellerId);
    }




    /**
     * Creates a new order.
     *
     * @param newOrder  the order to create
     * @return the created order
     */
    public Order createOrder(Order newOrder){

        newOrder = orderRepository.save(newOrder);

        orderRepository.flush();

        log.debug("Created Information for Order: {}", newOrder);

        return newOrder;
    }

    public void deleteOrderById(Long id) {
        Order order = getOrderById(id);
        if (order != null) {
            orderRepository.delete(order);
        } else {
            throw new RuntimeException("No such order");
        }
    }




}
