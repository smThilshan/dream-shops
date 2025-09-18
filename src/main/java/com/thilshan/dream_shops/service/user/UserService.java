package com.thilshan.dream_shops.service.user;

import com.thilshan.dream_shops.dto.UserDto;
import com.thilshan.dream_shops.model.Cart;
import com.thilshan.dream_shops.model.User;
import com.thilshan.dream_shops.request.CreateUserRequest;
import com.thilshan.dream_shops.request.UserUpdateRequest;
import com.thilshan.dream_shops.service.exception.AlreadyExistException;
import com.thilshan.dream_shops.service.exception.ResourceNotFoundException;
import com.thilshan.dream_shops.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(req.getFirstName());
                    user.setLastName(req.getLastName());
                    user.setEmail(req.getEmail());
                    user.setPassword(req.getPassword());

                    Cart cart = new Cart();
                    cart.setUser(user);
                    user.setCart(cart);

                    return userRepository.save(user);
                }).orElseThrow(()-> new AlreadyExistException("Oops!" +request.getEmail() +" already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    return userRepository.save(user);
                }).orElseThrow(()-> new ResourceNotFoundException("User not found with id " + userId));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete,
                ()-> {throw new ResourceNotFoundException("User not found");});;
    }

    @Override
    public UserDto convertUserToDto(User user) {
       return modelMapper.map(user, UserDto.class);
    }
}
