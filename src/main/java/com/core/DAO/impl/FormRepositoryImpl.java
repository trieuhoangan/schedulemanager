package com.core.DAO.impl;

import java.util.List;



import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.core.DAO.FormRepository;
import com.core.Model.Form;
import com.core.Model.User;
@Transactional
@Repository
public class FormRepositoryImpl implements FormRepository{
	
//	@PersistenceContext
//    private EntityManager entityManager;
	
	@Autowired
	private SessionFactory sessionFactory;


	@Override
	public void save(Form form) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(form);
	}
	@Override
	public Form findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(Form.class, id);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Form> findAll() {
		// TODO Auto-generated method stub
		String query = "select f from Form f";
		Session session = this.sessionFactory.getCurrentSession();
		Query<Form> query_info = (Query<Form>) session.createQuery(query);
		return query_info.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Form> findLimit(int i, int n) {
		String query = "select * from form f order by f.id desc limit "+(i-1)*n+","+n;
		Session session = this.sessionFactory.getCurrentSession();
		Query<Form> query_info = (Query<Form>) session.createNativeQuery(query);
		return query_info.list();

	}
	@SuppressWarnings("unchecked")
	@Override
	public Long getTotalPage() {
		String query = "select count(*) from Form f";
		Session session = this.sessionFactory.getCurrentSession();
		Query<Long> query_info = (Query<Long>) session.createQuery(query);
		return query_info.list().get(0);
	}
	@Override
	public void update(Form form) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.update(form);
	}
	@Override
	public Form getByCode(String code) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		Query<Form> query = (Query<Form>) session.createQuery("select f from Form f where f.code=:code");
		query.setParameter("code", code);
		List<Form> results = query.list();
		if(results.isEmpty())
			return null;
		else return query.getSingleResult();
		
	}

}
