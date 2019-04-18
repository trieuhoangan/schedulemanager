package com.core.Service;

import java.math.BigInteger;
import java.util.List;

import com.core.Model.User;
import com.core.Wrapper.LoginDTO;
import com.core.Wrapper.UserDTO;
import com.core.Wrapper.UserFullInfoDTO;

public interface UserService {
	public void save(final User user);

	public void update(final User user);

	public User findById(final Long id);
	
	public void delete(final Long id);

	public List<UserDTO> findAll();
	
	public  User loadUserByUsername(String username);
	
	public  boolean checkLogin(User user);
	
	public LoginDTO TokenvsProfile(String token, User profile);

	public List<User> searchUser(String field, String name, String intValue);

    List<UserDTO> findByString(String name);
	public List<UserFullInfoDTO> filterUser(String field, String value, int index_of_page, int pageSize);
	
	public BigInteger countFilterUser(String field,String value);
	
	public User findByUserName(String name);
}
