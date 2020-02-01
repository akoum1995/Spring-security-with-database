package auth.example.akoum.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import auth.example.akoum.entities.Role;
import auth.example.akoum.entities.User;
import auth.example.akoum.repositories.UserRepository;

@Service(value = "userService")
public class ServiceUserIMPL implements UserDetailsService, IServiceUser{

	
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void register(User u) {
	    u.setPassword(bcryptEncoder.encode(u.getPassword()));
		userRepository.save(u);
		
	}

	@Override
	public List<User> getAll() {
		List<User> list = new ArrayList<>();
		userRepository.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public void delete(int id) {
		userRepository.deleteById(id);		
	}


	@Override
	public User findById(int id) {
		return userRepository.findById(id).get();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
	}
	
	private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		Role role = user.getRole();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		return authorities;
	}

	@Override
	public User finByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}

}
