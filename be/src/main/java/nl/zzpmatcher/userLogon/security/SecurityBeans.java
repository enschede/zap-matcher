package nl.zzpmatcher.userLogon.security;

import nl.zzpmatcher.userLogon.business.User;
import nl.zzpmatcher.userLogon.business.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class SecurityBeans {

    private UserRepository userRepository;

    @Autowired
    public SecurityBeans(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {

        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

                final User user = userRepository.findUserByEmailaddress(s);

                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {

                        return Arrays
                                .stream(getIndividualRoles())
                                .map(this::mapToGrantedAuthority)
                                .collect(Collectors.toList());
                    }

                    private GrantedAuthority mapToGrantedAuthority(String role) {
                        return () -> role;
                    }

                    private String[] getIndividualRoles() {
                        return user.getRoles().split(",");
                    }

                    @Override
                    public String getPassword() {
                        return user != null ? user.getPassword() : null;
                    }

                    @Override
                    public String getUsername() {
                        return user.getEmailaddress();
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return true;
                    }
                };
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
