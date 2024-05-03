package project.contcheck.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.contcheck.tenants.TenantService;
import project.contcheck.domain.User;
import project.contcheck.repositorys.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private TenantService tenantService;

    public UserService(UserRepository repository, TenantService tenantService) {
        this.userRepository = repository;
        this.encoder = new BCryptPasswordEncoder();
        this.tenantService = tenantService;
    }

    @Transactional
    public User createUser(User user) {
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User saved = userRepository.save(user);
        tenantService.initDatabase(user.getUsername());
        return saved;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with the specified username is not found"));
    }
}
