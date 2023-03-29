package app.core.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	boolean existsByEmail(String customerEmail);

	boolean existsByEmailAndPassword(String customerEmail, String customerPassword);

	Customer findByEmailAndPassword(String customerEmail, String customerPassword);
}
