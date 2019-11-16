package com.sduwh.sso.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.sduwh.sso.domain.OperationResponse;
import com.sduwh.sso.domain.User;
import com.sduwh.sso.domain.UserAdditionalField;
import com.sduwh.sso.domain.view.AdminView;
import com.sduwh.sso.exception.resource.ResourceNotFoundException;
import com.sduwh.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * 用户相关controller
 *
 * @author wxp
 */
@RestController
@RequestMapping("/v1")
public class UserController {
  @Resource private UserService userService;

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  @PreAuthorize("hasAuthority('ADMIN')")
  @JsonView(AdminView.class)
  public ResponseEntity<List<User>> findUsers(
      @RequestParam(value = "login", required = false) String login,
      @RequestParam(value = "limit", defaultValue = "10") int limit,
      @RequestParam(value = "offset", defaultValue = "0") int offset) {
    int maxLimit = 200;
    if (limit < 0) {
      limit = 0;
    } else if (limit > maxLimit) {
      limit = maxLimit;
    }
    if (offset < 0) {
      offset = 0;
    }
    User filter = new User();
    if (!StringUtils.isEmpty(login)) {
      filter.setLogin(login);
    }
    return ResponseEntity.ok(
        userService.findUserByTemplate(
            filter, limit, offset, Collections.singletonList(UserAdditionalField.LAST_GRANT)));
  }

  @RequestMapping(value = "/users/authorize", method = RequestMethod.PUT)
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<OperationResponse> authorizeUser(
      @RequestParam("user_id") Long userId, @RequestParam("role") User.Role role) {
    User user = userService.findOneUserById(userId);
    if (user == null) {
      throw new ResourceNotFoundException("user");
    }
    user.setRole(role);
    userService.updateUser(user);
    OperationResponse response = new OperationResponse();
    response.setCode(0);
    return ResponseEntity.ok(response);
  }
}
