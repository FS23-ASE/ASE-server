package ASE.repository;

import ASE.entity.Cart;
import ASE.entity.Order;
import ASE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("orderRepository")
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findById(long id);

    Order findByBuyerId(long buyerId);

    Order findBySellerId(long sellerId);
}
