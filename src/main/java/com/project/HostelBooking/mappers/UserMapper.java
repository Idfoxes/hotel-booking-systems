package com.project.HostelBooking.mappers;

import com.project.HostelBooking.model.user.User;
import com.project.HostelBooking.web.dto.user.UserListResponse;
import com.project.HostelBooking.web.dto.user.UserRequest;
import com.project.HostelBooking.web.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User requestToUser(UserRequest request);

    @Mapping(source = "userId", target = "id")
    User requestToUser(Long userId, UserRequest request);

    UserResponse userToResponse(User user);

    default UserListResponse userListToResponseList(List<User> users) {
        UserListResponse response = new UserListResponse();
        response.setUsers(users.stream().map(this::userToResponse).collect(Collectors.toList()));
        return response;
    }
}
