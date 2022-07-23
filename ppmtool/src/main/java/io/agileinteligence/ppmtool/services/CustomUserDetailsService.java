package io.agileinteligence.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.agileinteligence.ppmtool.domain.User;
import io.agileinteligence.ppmtool.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

	User user = userRepository.findByUsername(username);
	if (user == null) {
	    new UsernameNotFoundException("ユーザーが見つかりませんでした");
	}
	return user;
    }

    @Transactional
    public User loadByUserById(Long id) {
	User user = userRepository.getById(id);
	if (user == null) {
	    new UsernameNotFoundException("ユーザーが見つかりませんでした");
	}
	return user;
    }

}
