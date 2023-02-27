package com.driver.services.impl;

import com.driver.model.*;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Autowired
	AdminServiceImpl adminServiceImpl;

	@Autowired
	DriverServiceImpl driverServiceImpl;

	@Autowired
	AdminServiceImpl getAdminServiceImpl;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
		//adminServiceImpl.addCustomer(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer = customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);
		//adminServiceImpl.deleteCustomer(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver> listOfDrivers = driverRepository2.findAll();

		for(int i=0;i<listOfDrivers.size();i++){
			Driver driver = listOfDrivers.get(i);
			Cab cab = driver.getCab();

			if(cab.getAvailable()){
				List<TripBooking> tripBookingList = new ArrayList<>();

				Customer customer = customerRepository2.findById(customerId).get();

				tripBookingList = customer.getTripBookingList();

				TripBooking bookedTrip = new TripBooking();

				bookedTrip.setFromLocation(fromLocation);
				bookedTrip.setToLocation(toLocation);
				bookedTrip.setDistanceInKm(distanceInKm);
				bookedTrip.setStatus(TripStatus.CONFIRMED);
				bookedTrip.setCustomer(customer);
				bookedTrip.setDriver(driver);

				tripBookingRepository2.save(bookedTrip);
				driverServiceImpl.updateStatus(driver.getDriverId());


				return bookedTrip;
			}
		}

		return null;


	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking cancelledTrip = tripBookingRepository2.findById(tripId).get();

		cancelledTrip.setStatus(TripStatus.CANCELED);
		tripBookingRepository2.save(cancelledTrip);
		/* Customer customer = cancelledTrip.getCustomer();

		List<TripBooking> tripBookingList = customer.getTripBookingList();

		for(int i=0;i< tripBookingList.size();i++){
			if(tripId == tripBookingList.get(i).getTripBookingId()){
				cancelledTrip = tripBookingList.get(i);

				cancelledTrip.setStatus(TripStatus.CANCELED);
				tripBookingList.set(i,cancelledTrip);
			}
		}

		tripBookingRepository2.deleteById(tripId);*/
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		TripBooking completedTrip = tripBookingRepository2.findById(tripId).get();


		/*Customer customer = completedTrip.getCustomer();

		List<TripBooking> tripBookingList = customer.getTripBookingList();

		for(int i=0;i< tripBookingList.size();i++){
			if(tripId == tripBookingList.get(i).getTripBookingId()){
				completedTrip = tripBookingList.get(i);

				completedTrip.setStatus(TripStatus.COMPLETED);
				tripBookingList.set(i,completedTrip);
			}
		}

		tripBookingRepository2.deleteById(tripId);*/

		completedTrip.setStatus(TripStatus.COMPLETED);
		tripBookingRepository2.save(completedTrip);
	}
}
