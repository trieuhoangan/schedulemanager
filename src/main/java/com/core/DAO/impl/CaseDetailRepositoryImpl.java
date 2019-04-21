package com.core.DAO.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.core.DAO.CaseDetailRepository;
import com.core.Model.CaseDetail;
@Transactional
@Repository
public class CaseDetailRepositoryImpl implements CaseDetailRepository{


	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void save(CaseDetail caseDetail) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(caseDetail);
	}

	@Override
	public CaseDetail findById(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return session.get(CaseDetail.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseDetail> findAll() {
		String query = "select c from CaseDetail c";
		Session session = this.sessionFactory.getCurrentSession();
		Query<CaseDetail> Query_info= (Query<CaseDetail>)  session.createQuery(query);
		List<CaseDetail> result = Query_info.list();
		return result;
	}

	@Override
	public CaseDetail findByFormId(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		Query<CaseDetail> query =(Query<CaseDetail>)  session.createQuery("from CaseDetail where form_id=:formid");
		query.setParameter("formid", id);
		CaseDetail caseDetail = new CaseDetail();
		if(!query.list().isEmpty()) caseDetail = query.getSingleResult();
		return caseDetail;
	}

	@Override
	public void update(CaseDetail caseDetail) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();
		session.update(caseDetail);
		
	}
		
}
