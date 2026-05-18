package com.example;

public interface UserRepository {
    User findById(Long id);
    void save(User user);
    void delete(Long id);
    boolean existsById(Long id);
}
