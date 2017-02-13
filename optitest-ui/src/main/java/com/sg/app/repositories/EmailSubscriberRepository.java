package com.sg.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sg.app.entities.EmailSubscriber;


/**
 * @author Karoonakar
 *
 */
public interface EmailSubscriberRepository extends JpaRepository<EmailSubscriber, Integer>
{

	EmailSubscriber findByEmail(String email);

}
