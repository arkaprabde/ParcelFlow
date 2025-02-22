package App.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import App.Entities.Vendor;

@Repository
public interface VendorRepo extends JpaRepository<Vendor, String>
{}