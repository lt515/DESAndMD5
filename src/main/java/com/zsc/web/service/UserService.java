package com.zsc.web.service;

import com.zsc.web.util.DESUtil;
import com.zsc.web.domain.User;
import com.zsc.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username){
        return userRepository.findByUsername(username).get();
    }

    public boolean login(User user,User u,String key) throws Exception {
        if(DESUtil.decryption(u.getPassword(),key) == user.getPassword()){
            return true;
        }
        return false;
    }

    public User regist(User user){
        return userRepository.save(user);
    }

}
