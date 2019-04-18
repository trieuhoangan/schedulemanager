package com.core.DAO;


import java.math.BigInteger;
import java.util.List;

import com.core.Model.User;
import com.core.Wrapper.UserFullInfoDTO;

public interface UserRepository{
	public User findByUsername(String username);
	public void save(User user);
	public User findById(Long id);
	public List<User> findAll();
	public void update(User user);
	public void delete(User user);
	public List<User> searhUser(String field, String name, String intValue);
	public List<UserFullInfoDTO> filterUser(String field, String value, int index_of_page, int pageSize);
	public BigInteger countFilterUser(String field, String value);
	public List<UserFullInfoDTO> getUserDataPaging(int from, int offset);
	public BigInteger getCountUser();	
}
