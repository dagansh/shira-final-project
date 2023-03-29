package app.core.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Company;

public interface CompanyRepo extends JpaRepository<Company, Integer> {
	boolean existsByName(String companyName);

	boolean existsByEmail(String companyEmail);

	boolean existsByEmailAndPassword(String companyEmail, String companyPassword);

	Optional<Company> findByEmail(String companyEmail);
}
