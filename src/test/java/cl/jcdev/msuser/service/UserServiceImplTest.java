package cl.jcdev.msuser.service;

import cl.jcdev.msuser.dto.UserDTO;
import cl.jcdev.msuser.entity.User;
import cl.jcdev.msuser.exception.EmailAlreadyExistException;
import cl.jcdev.msuser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;
    private User user1;
    private User user2;
    private User user3;

    private List<User> users  = new ArrayList<User>();

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
        user1 = new User("user1", "user1", "user1@mail.com", "123456789");
        user2 = new User("user2", "user2", "user2@mail.com", "123456789");
        user3 = new User("user3", "user3", "user3@mail.com", "123456789");
        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    @Test
    void save() {
        UserDTO userDTO = userService.mapUserDTO(user1);
        User user = userService.mapUser(userDTO);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO userResponse = userService.save(userDTO);
        assertNotNull(userResponse);
        assertEquals(userDTO.getEmail(), userResponse.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void findAll() {
        when(userRepository.findAll()).thenReturn(users);
        List<User> users = userService.findAll();
        assertNotNull(users);
        assertEquals(3, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findById() {
    user1.setId(1L);
    when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user1));
    User user = userService.findById(1L);
    assertNotNull(user);
    assertEquals(user1.getEmail(), user.getEmail());
    verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        userService.deleteById(1L);
        verify(userRepository, times(1)).deleteById(1L);

    }

    @Test
    void update() {
        user1.setId(1L);
        when(userRepository.save(any(User.class))).thenReturn(user1);
        User user = userService.update(user1);
        assertNotNull(user);
        assertEquals(user1.getEmail(), user.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void isEmailAlreadyInUse() {
        when(userRepository.findByEmail(user1.getEmail())).thenReturn(user1);
        assertThrows(EmailAlreadyExistException.class, () -> userService.isEmailAlreadyInUse(user1.getEmail()));
        verify(userRepository, times(1)).findByEmail(user1.getEmail());
    }
}