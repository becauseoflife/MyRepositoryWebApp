package com.service;

import com.mapper.Admin;

public interface AdminAccountService {
	
	public Admin findByName(String username);
	
}
