package com.devsuperior.dslearnbds.service;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.dslearnbds.entities.User;
import com.devsuperior.dslearnbds.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	//MACETE PRA LANÇAR MENSAGEM NO CONSOLE
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class); // É TIPO UMA MESSAGEBOX

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// METODO DO USERREPOSITORY QUE CRIAMOS, FINDBYEMAIL
		User user = repository.findByEmail(username);
		
		// SE NÃO EXISTIR, LANÇAR UMA UsernameNotFoundException
		if(user == null) {
			// MACETE PRA LANÇAR NO CONSOLE UMA MENSAGEM (LOGGER FOI INSTANCIADO ACIMA NA CLASSE)
			logger.error("User not found: " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		
		logger.info("User found: " + username);
		return user;
	}
}
