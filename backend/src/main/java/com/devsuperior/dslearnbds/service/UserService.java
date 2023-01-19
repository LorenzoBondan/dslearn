package com.devsuperior.dslearnbds.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dslearnbds.dto.UserDTO;
import com.devsuperior.dslearnbds.entities.User;
import com.devsuperior.dslearnbds.repositories.UserRepository;
import com.devsuperior.dslearnbds.services.exceptions.ResourceNotFoundException;

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
	
	//METODO DE BUSCAR USUARIO POR ID
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) 
	{
		Optional<User> obj = repository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
		return new UserDTO(entity); // AQUI MUDAMOS, OLHAR O CONSTRUTOR DO DTO
	}
}
