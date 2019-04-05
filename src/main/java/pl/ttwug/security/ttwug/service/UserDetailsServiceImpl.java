package pl.ttwug.security.ttwug.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.ttwug.security.ttwug.exception.UserLoginException;
import pl.ttwug.security.ttwug.model.ApplicationUser;
import pl.ttwug.security.ttwug.repository.ApplicationUserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private ApplicationUserRepository applicationUserRepository;


    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserLoginException {
        Optional<ApplicationUser> applicationUserOptional = applicationUserRepository.findByUsername(username);
        if (applicationUserRepository.findByUsername(username).isPresent()) {
            ApplicationUser user = applicationUserOptional.get();
            return new User(user.getUsername(), user.getPassword(), getRoles(user));
        } else
            throw new UserLoginException(username);
    }

    private Collection<? extends GrantedAuthority> getRoles(ApplicationUser applicationUser) {
        return applicationUser.getRoles()
                .stream()
                .map(o1 -> new SimpleGrantedAuthority("ROLE_" + o1))
                .collect(Collectors.toSet());

    }
}