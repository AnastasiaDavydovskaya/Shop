package by.tms.shop.repositories;

import by.tms.shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}
