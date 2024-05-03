package project.contcheck.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.contcheck.domain.User;
import project.contcheck.repositorys.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetail implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private Set<GrantedAuthority> set = new HashSet<>();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            new UsernameNotFoundException("User not exists by Username");
        }
        GrantedAuthority authorities = new SimpleGrantedAuthority(user.get().getAuthorities().toString());
        set.add(authorities);

        return new org.springframework.security.core.userdetails.User(username, user.get().getPassword(), set);
    }
}