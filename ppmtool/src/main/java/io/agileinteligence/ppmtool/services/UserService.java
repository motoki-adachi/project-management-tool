package io.agileinteligence.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.agileinteligence.ppmtool.domain.User;
import io.agileinteligence.ppmtool.exceptions.UsernameAlreadyExistsException;
import io.agileinteligence.ppmtool.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(User newUser) {

	try {
	    newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
	    newUser.setUsername(newUser.getUsername());
	    return userRepository.save(newUser);
	} catch (Exception e) {
	    throw new UsernameAlreadyExistsException("ユーザー名: " + newUser.getUsername() + "は既に使用されています。");
	}

    }

}
