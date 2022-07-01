package elements.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import elements.repository.UsersRepository;
import reactor.core.publisher.Mono;

@Service
public class UsersService implements ReactiveUserDetailsService{

    @Autowired
    private UsersRepository repository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return repository.findByUsername(username).cast(UserDetails.class);
    }
    
}
