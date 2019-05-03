package com.core.DAO.impl;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.core.DAO.DayRepository;
import com.core.Model.Day;
import com.core.Model.Form;


@Transactional
@Repository
public class DayRepositoryImpl implements DayRepository{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Day findById(Long id) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		return session.find(Day.class,id);
	}

	@Override
	public void save(Day day) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.save(day);
	}

	@Override
	public void update(Day day) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.update(day);
	}

	@Override
	public void delete(Day day) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(day);
	}

	@Override
	public List<Day> findAll() {
		// TODO Auto-generated method stub
		String query = "select d from Day d";
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<Day> query_info = (Query<Day>) session.createQuery(query);
		return query_info.list();
	}

	@Override
	public Day findByDay(String day) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<Day> query_info = (Query<Day>) session.createQuery("select d from Day d where d.day=:day");
		query_info.setParameter("day", day);
		List<Day> list = query_info.getResultList();
		if(list.isEmpty()) return null;
		else
		return query_info.getResultList().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Day> findDayAfter(String day) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<Day> query_info = (Query<Day>) session.createQuery("select d from Day d where d.day>:day");
		query_info.setParameter("day", day);
		return query_info.getResultList();
	}

	@Override
	public List<Day> findLimit(int i, int n) {
		String query = "select d from Day d order by d.day DESC";
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<Day> query_info = (Query<Day>) session.createQuery(query);
		query_info.setFirstResult(i*n);
		query_info.setMaxResults(n);
		return query_info.getResultList();
	}

	@Override
	public long getTotalPage() {
		String query = "select count(*) from Day d order by d.id DESC";
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<Long> query_info = (Query<Long>) session.createQuery(query);
		return query_info.list().get(0);
	}

	@Override
	public List<Day> getFilterPage(String field, String value) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<Day> query = (Query<Day>) session.createQuery("select d from Day d where d."+field+" LIKE :value order by d.id DESC");
		query.setParameter("value", "%"+value+"%");
		return query.getResultList();
	}

	@Override
	public List<Day> getFilterCasePage(String field, int value) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<Day> query = (Query<Day>) session.createQuery("select d from Day d where d."+field+" = :value order by d.id DESC");
		query.setParameter("value",value);
		return query.getResultList();
		
	}

}
