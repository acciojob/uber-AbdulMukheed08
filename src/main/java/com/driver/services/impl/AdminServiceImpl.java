package com.driver.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository1;

	@Autowired
	DriverRepository driverRepository1;

	@Autowired
	CustomerRepository customerRepository1;

	private List<Driver> listOfDrivers = new ArrayList<>();
	private List<Customer> listOfCustomers = new ArrayList<>();

	@Override
	public void adminRegister(Admin admin) {
		//Save the admin in the database
		adminRepository1.save(admin);
	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		//Update the password of admin with given id
		Admin admin = adminRepository1.findById(adminId).get();

		admin.setPassword(password);

		adminRepository1.save(admin);

		return admin;
	}

	@Override
	public void deleteAdmin(int adminId){
		// Delete admin without using deleteById function
		Admin admin = adminRepository1.findById(adminId).get();
		adminRepository1.delete(admin);
	}

	@Override
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers
		return listOfDrivers = driverRepository1.findAll();
	}
	/*public void addDriver(Driver driver){
		listOfDrivers.add(driver);
	}
	/*public void deleteDriver(Driver driver){
		for(int i=0;i<listOfDrivers.size();i++){
			Driver currDriver = listOfDrivers.get(i);
			if(currDriver.equals(driver)){
				listOfDrivers.remove(i);
				break;
			}
		}
	}*/

	@Override
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers
		return listOfCustomers = customerRepository1.findAll();;
	}

	public void addCustomer(Customer customer){
		listOfCustomers.add(customer);
	}

	public void deleteCustomer(Customer customer){
		/*for(int i=0;i<listOfCustomers.size();i++){
			Customer currCustomer = listOfCustomers.get(i);
			if(currCustomer.equals(customer)){
				listOfCustomers.remove(i);
				break;
			}
		}*/
		//listOfCustomers = customerRepository1.findAll();
	}

}
