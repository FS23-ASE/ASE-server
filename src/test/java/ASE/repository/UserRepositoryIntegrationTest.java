package ASE.repository;

import ASE.entity.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Nested
class UserRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    @Test
    void findByUsername_success() {
        // given
        User user = new User();
        user.setUsername("firstname@lastname");
        user.setPassword("password");
        user.setToken("1");
        user.setEmail("1@outlook.com");


        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUsername(user.getUsername());

        // then
        assertNotNull(found.getId());
        assertEquals(found.getUsername(), user.getUsername());
        assertEquals(found.getToken(), user.getToken());
    }
}