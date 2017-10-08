package it.wct.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import it.wct.entity.Customer;

@Repository
public class CustomerDaoImpl implements CustomerDao {


	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public List<Customer> getCustomers() {
		
		Session currentSession = sessionFactory.getCurrentSession();
		
		List<Customer> customers = currentSession.createQuery("from Customer order by lastName", Customer.class).getResultList();
		
		return customers;
	}

	@Override
	public void saveCustomer(Customer customer) {
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(customer);
	}

	@Override
	public Customer getCustomer(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		
		Customer customer = session.get(Customer.class, id);
		
		return customer;
	}

	@Override
	public void deleteCustomer(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		
		session.createQuery("delete from Customer where id=:id").setParameter("id", id).executeUpdate();
	}

	@Override
	public List<Customer> searchCustomers(String searchName) {
		Session session = sessionFactory.getCurrentSession();

		Query query = null;

		if (searchName != null && searchName.trim().length() > 0) {
			query = session.createQuery("from Customer where lower(firstName) like :name or lower(lastName) like :name", Customer.class);
			query.setParameter("name", "%" + searchName.toLowerCase() + "%");
		} else {
			query =session.createQuery("from Customer", Customer.class);            
		}

		List<Customer> customers = query.getResultList();
		
		return customers;
	}

}
