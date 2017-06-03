package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import hibernate.util.HibernateUtil;
import model.NotebookBean;

public class NotebookDAO implements model.NotebookDAO {
	
	public static void main(String[] args) {
		NotebookBean bean = new NotebookBean();
		bean.setNote_id(2);
		bean.setMember_id("test");
		bean.setIngre_name("test");
		bean.setDate(java.sql.Date.valueOf("2005-01-01"));
		bean.setPrice(20.2);
		bean.setQuantity(1);
		bean.setWeight_kilo(10.0);
		bean.setPrice_each(20.0);
		bean.setPrice_perkilo(20.0);
		bean.setExpire_date(java.sql.Date.valueOf("2005-01-01"));
		bean.setComment("YAYAYA");
		new NotebookDAO().insert(bean);
		
		
		/*測試delete()方法*/
//		new NotebookDAO().delete(1);
		
		/*測試findByPrimaryKey()方法*/
//		System.out.println(new NotebookDAO().findByPrimaryKey(1));
		
		/*測試getAll()方法*/
//		System.out.println(new NotebookDAO().getAll());
	}
	
	private static final String GET_ALL_STMT = "from NotebookBean order by note_id";
	
	@Override
	public void insert(NotebookBean bean) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if(bean != null){
			try {
				session.beginTransaction();
				session.saveOrUpdate(bean);
				session.getTransaction().commit();
			} catch (RuntimeException e) {
				session.getTransaction().rollback();
				throw e;
			}
		}
	}

	@Override
	public void update(NotebookBean bean) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		if(bean != null){
			try {
				session.beginTransaction();
				session.saveOrUpdate(bean);
				session.getTransaction().commit();
			} catch (RuntimeException e) {
				session.getTransaction().rollback();
				throw e;
			}
		}
	}

	@Override
	public void delete(Integer note_id) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			try {
				session.beginTransaction();
				NotebookBean notebookBean = session.get(NotebookBean.class, note_id);
				session.delete(notebookBean);
				session.getTransaction().commit();
			} catch (RuntimeException e) {
				session.getTransaction().rollback();
				throw e;
			}
	}

	@Override
	public NotebookBean findByPrimaryKey(Integer note_id) {
		NotebookBean notebookBean = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			notebookBean = session.get(NotebookBean.class, note_id);
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return notebookBean;
	}

	@Override
	public List<NotebookBean> getAll() {
		List<NotebookBean> list = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery(GET_ALL_STMT);
			list = query.list();
			session.getTransaction().commit();
		} catch (RuntimeException ex) {
			session.getTransaction().rollback();
			throw ex;
		}
		return list;
	}
}//end of NotebookDAO
