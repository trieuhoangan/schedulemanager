package com.core.DAO.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.core.DAO.UserRepository;
import com.core.Model.User;
import com.core.Wrapper.UserFullInfoDTO;
import org.hibernate.query.Query;
@Transactional
@Repository
public class UserRepositoryImpl implements UserRepository{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<User> query = session.createQuery("select u from User u where u.username=:name");
		query.setParameter("name", username);
		List<User> results = query.list();
		if(results.isEmpty())
			return null;
		else return query.getSingleResult();
	}

	@Override
	public void save(User user) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.save(user);
	}

	@Override
	public User findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(User.class, id);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		return session.createQuery("FROM User", User.class).getResultList();
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.update(user);
	}

	@Override
	public void delete(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(user);
		
	}

	@Override
	public List<User> searhUser(String field, String name, String intValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserFullInfoDTO> filterUser(String field, String value, int index_of_page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger countFilterUser(String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserFullInfoDTO> getUserDataPaging(int from, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigInteger getCountUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
