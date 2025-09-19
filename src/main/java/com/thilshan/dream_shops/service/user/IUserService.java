package com.thilshan.dream_shops.service.user;

import com.thilshan.dream_shops.dto.UserDto;
import com.thilshan.dream_shops.model.User;
import com.thilshan.dream_shops.request.CreateUserRequest;
import com.thilshan.dream_shops.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
