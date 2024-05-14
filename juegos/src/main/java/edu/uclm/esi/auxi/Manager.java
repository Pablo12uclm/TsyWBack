package edu.uclm.esi.auxi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import edu.uclm.esi.juegos.dao.*;

@Component
public class Manager {
	
	@Autowired
	private UserDAO userdao;
	
	private Manager() {}
	
	private static class ManagerHolder {
		static Manager singleton = new Manager();
	}
	
	@Bean
	public static Manager get() {
		return ManagerHolder.singleton;
	}

}
