package com.example;

public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void createUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty");
        }
        userRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Welcome", "Welcome to our service!");
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User does not exist");
        }
        User user = userRepository.findById(id);
        userRepository.delete(id);
        emailService.sendEmail(user.getEmail(), "Account Deleted", "Your account has been deleted.");
    }

    public boolean userExists(Long id) {
        return userRepository.existsById(id);
    }
}
