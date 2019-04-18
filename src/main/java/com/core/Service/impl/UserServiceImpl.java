package com.core.Service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.DAO.UserRepository;
import com.core.Model.User;
import com.core.Service.UserService;

import com.core.Wrapper.LoginDTO;
import com.core.Wrapper.UserDTO;
import com.core.Wrapper.UserFullInfoDTO;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired(required = false)
	private UserRepository userDao;


    @Override
    public List<UserDTO> findAll() {
        List<User> list = userDao.findAll();
        List<UserDTO> listU = new ArrayList<UserDTO>();
        for (User user : list) {
            UserDTO u = new UserDTO();
            u.setEmail(user.getUsername());
            u.setId(user.getId());

            listU.add(u);
        }
        return listU;
    }

    public User findById(final Long id) {
        return userDao.findById(id);
    }

    public void save(final User user) {
        userDao.save(user);
    }

    public void update(final User user) {
        userDao.update(user);
    }

    public void delete(final Long id) {
        User customer = userDao.findById(id);
        if (customer != null) {
            userDao.delete(customer);
        }
    }

    @Override
    public User loadUserByUsername(String username) {
        for (User user : userDao.findAll()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean checkLogin(User user) {
        for (User userExist : userDao.findAll()) {
            if (StringUtils.equals(user.getUsername(), userExist.getUsername())
                    && StringUtils.equals(user.getPassword(), userExist.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<User> searchUser(String field, String name, String intValue) {
        return userDao.searhUser(field, name, intValue);

    }

    @Override
    public List<UserDTO> findByString(String name) {
        List<UserDTO> u = new ArrayList<>();
        List<User> users = userDao.searhUser("username", name, "1");

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getUsername());
            u.add(userDTO);
        }
        return u;
    }

    @Override
    public List<UserFullInfoDTO> filterUser(String field, String value, int index_of_page, int pageSize) {
        return userDao.filterUser(field, value, index_of_page, pageSize);
    }

    @Override
    public BigInteger countFilterUser(String field, String value) {
        return userDao.countFilterUser(field, value);
    }


    @Override
    public LoginDTO TokenvsProfile(String token, User profile) {
        LoginDTO dto = new LoginDTO();
        dto.setToken(token);
        dto.setProfile(profile);
        return dto;
    }

    public List<UserFullInfoDTO> getUserDataPaging(int from, int offset) {
        return userDao.getUserDataPaging(from, offset);
    }

    public BigInteger getCountUser() {
        return userDao.getCountUser();
    }

	@Override
	public User findByUserName(String name) {
		
		return userDao.findByUsername(name);
	}

}
