package App.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import App.Entities.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, String>
{}