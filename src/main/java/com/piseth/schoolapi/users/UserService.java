package com.piseth.schoolapi.users;

import java.util.List;

public interface UserService {
    User create(User user);

    User update(Long id, User user);

    void delete(Long id);

    User getById(Long id);

    List<User> getAllUsers();

}
