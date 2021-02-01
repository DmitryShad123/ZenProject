package com.luchkin.ZenBlog.service;

import com.luchkin.ZenBlog.model.User;
import com.luchkin.ZenBlog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.getOne(id);
    }

    public User saveUserEditing(User user){
        return userRepository.save(user);
    }

    public User saveUser(User user){
        user.setRole("USER");
        return userRepository.save(user);
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User findByNickname(String nickname){
        return userRepository.findByNickname(nickname);
    }

    public void deliteById(Long id){
        userRepository.deleteById(id);
    }

}
