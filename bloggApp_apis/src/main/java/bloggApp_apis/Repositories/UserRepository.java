package bloggApp_apis.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import bloggApp_apis.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);
}
