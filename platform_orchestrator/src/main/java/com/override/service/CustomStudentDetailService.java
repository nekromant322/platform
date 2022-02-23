package com.override.service;

import com.override.models.StudentAccount;
import com.override.repositories.StudentAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomStudentDetailService implements UserDetailsService {

    private final StudentAccountRepository studentAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return Optional.ofNullable(studentAccountRepository.findFirstByLogin(login))
                .map(CustomStudentDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с логином " + login + " не найден!"));
    }

    private static class CustomStudentDetails implements UserDetails {

        private final String login;
        private final String password;
        private final List<GrantedAuthority> authorities;

        public CustomStudentDetails(StudentAccount student) {
            this.login = student.getLogin();
            this.password = student.getPassword();
            this.authorities = student.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return login;
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
    }
}
