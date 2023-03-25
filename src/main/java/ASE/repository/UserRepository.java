package ASE.repository;

import ASE.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
  User findById(long id);

  User findByUsername(String username);

  User findByEmail(String email);

}
