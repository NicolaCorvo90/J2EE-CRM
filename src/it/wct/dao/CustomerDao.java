package it.wct.dao;

import java.util.List;

import it.wct.entity.Customer;

public interface CustomerDao {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer customer);

	public Customer getCustomer(Integer id);

	public void deleteCustomer(Integer id);

	public List<Customer> searchCustomers(String searchName);
}
