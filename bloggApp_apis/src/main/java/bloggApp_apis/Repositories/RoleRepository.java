package bloggApp_apis.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import bloggApp_apis.Entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
