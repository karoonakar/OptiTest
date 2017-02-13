package com.sg.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sg.app.entities.User;

/**
 * @author Karoonakar
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>
{

	User findByEmail(String email);

	User findByEmailAndPassword(String email, String password);

}
