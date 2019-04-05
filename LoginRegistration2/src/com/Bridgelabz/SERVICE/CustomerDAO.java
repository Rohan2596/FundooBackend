package com.Bridgelabz.SERVICE;
import com.Bridgelabz.MODEL.*;
public interface CustomerDAO {
	public int insertCustomer(Customer c) ;
	public Customer getCustomer(String userId,String password);
}
