package cl.jcdev.msuser.service;

import cl.jcdev.msuser.dto.UserDTO;
import cl.jcdev.msuser.entity.User;
import cl.jcdev.msuser.exception.EmailAlreadyExistException;
import cl.jcdev.msuser.exception.ResourceNotFoundException;
import cl.jcdev.msuser.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDTO save(UserDTO userDTO) {
        logger.info("Creating user");

        User user = mapUser(userDTO);
        isEmailAlreadyInUse(user.getEmail());
        try {
            User savedUser = userRepository.save(user);
            return mapUserDTO(savedUser);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        logger.info("Finding all users");
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        logger.info("Finding user with id: " + id);
        return userFindById(id);
    }

    private User userFindById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> {
                    logger.error("User not found");
                    throw new ResourceNotFoundException("User not found!");
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting user with id: " + id);
        userRepository.deleteById(id);
    }

    @Override
    public User update(User user) {
        logger.info("Updating user with id: " + user.getId());
        userFindById(user.getId());
        return userRepository.save(user);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    void isEmailAlreadyInUse(String email) {
        if (this.findUserByEmail(email) != null) {
            logger.error("Email already in use");
            throw new EmailAlreadyExistException("Email already in use");
        }
    }

    public User mapUser(UserDTO userDTO) {
        User user = new User(
                userDTO.getName(),
                userDTO.getLastname(),
                userDTO.getEmail(),
                userDTO.getPhone()
        );

        return user;
    }

    public UserDTO mapUserDTO(User user) {
        UserDTO userDTO = new UserDTO(
                user.getName(),
                user.getLastname(),
                user.getEmail(),
                user.getPhone()
        );
        userDTO.setId(user.getId());
        return userDTO;
    }
}
