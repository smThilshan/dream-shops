package com.thilshan.dream_shops.security.user;

import com.thilshan.dream_shops.model.User;
import com.thilshan.dream_shops.service.repository.UserRepository;
import com.thilshan.dream_shops.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ShopUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(()-> new  UsernameNotFoundException("User not found"));
        return ShopUserDetails.buildUserDetails(user);
    }
}
