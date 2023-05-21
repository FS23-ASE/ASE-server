package ASE.repository;


import ASE.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The OrderRepository interface is responsible for managing the persistence and retrieval of Order entities.
 */
@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order
     * @return the order with the specified ID
     */
    Order findById(long id);

    /**
     * Retrieves an order by the buyer ID.
     *
     * @param buyerId the ID of the buyer
     * @return the orders associated with the buyer ID
     */
    List<Order> findByBuyerId(long buyerId);

    /**
     * Retrieves an order by the seller ID.
     *
     * @param sellerId the ID of the seller
     * @return the orders associated with the seller ID
     */
    List<Order> findBySellerId(long sellerId);

    /**
     * Delete an order according to its id
     *
     * @param id the ID of the order
     *
     */
    void deleteById(long id);
}
