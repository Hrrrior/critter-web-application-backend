package ee.taltech.critter.repository;

import ee.taltech.critter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    List<User> findAllByUsername(String username);
}
