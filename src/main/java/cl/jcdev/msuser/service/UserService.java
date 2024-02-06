package cl.jcdev.msuser.service;

import cl.jcdev.msuser.dto.UserDTO;
import cl.jcdev.msuser.entity.User;

import java.util.List;

public interface UserService {
    UserDTO save(UserDTO user);
    List<User> findAll();
    User findById(Long id);
    void deleteById(Long id);
    User update(User user);
}
