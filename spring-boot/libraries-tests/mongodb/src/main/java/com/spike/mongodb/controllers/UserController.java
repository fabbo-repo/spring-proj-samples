package com.spike.mongodb.controllers;

import com.spike.mongodb.models.dtos.UserDto;
import com.spike.mongodb.models.requests.UserRequest;
import com.spike.mongodb.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = UserController.CONTROLLER_PATH)
@Validated
@Tag(name = UserController.SWAGGER_TAG)
@RequiredArgsConstructor
public class UserController {
    public static final String SWAGGER_TAG = "User API";

    @SuppressWarnings("squid:S1075")
    public static final String CONTROLLER_PATH = "/user";

    @SuppressWarnings("squid:S1075")
    public static final String GET_USER_SUB_PATH = "/{id}";

    private final UserService userService;

    @GetMapping(GET_USER_SUB_PATH)
    public ResponseEntity<UserDto> getUser(
            @PathVariable final String id
    ) {
        final UserDto response = userService.getUser(id);
        return ResponseEntity
                .ok(response);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody @Valid final UserRequest request
    ) {
        final UserDto response = userService.createUser(request);
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path(GET_USER_SUB_PATH)
                .buildAndExpand(response.getId()).toUri();
        return ResponseEntity
                .created(location)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> searchUsers(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final Integer age
    ) {
        final List<UserDto> responseList = userService.searchUser(
                name,
                age
        );
        return ResponseEntity
                .ok(responseList);
    }
}
