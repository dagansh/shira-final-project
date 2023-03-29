package app.core.servies;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public abstract class ClientService {

	public abstract boolean login(String email, String password);

}
