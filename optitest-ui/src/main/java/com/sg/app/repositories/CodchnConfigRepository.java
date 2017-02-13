package com.sg.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sg.app.entities.CodchnConfig;

/**
 * @author Karoonakar
 *
 */
public interface CodchnConfigRepository extends JpaRepository<CodchnConfig, Integer>{

	List<CodchnConfig> findByCodchn(String codchn);
	
	List<CodchnConfig> findAll();

	@Query("select DISTINCT(c.codchn) from CodchnConfig c")
	List<String> findAllCodchn();
}
