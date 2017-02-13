package com.sg.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sg.app.entities.ProcessRequest;

/**
 * @author Karoonakar
 *
 */
public interface ProcessRequestRepository extends JpaRepository<ProcessRequest, Integer>
{
	
	List<ProcessRequest> findAll();
	
	ProcessRequest findById(Integer id);
	
	@Modifying
    @Query("UPDATE ProcessRequest p SET p.status = :reqStat, p.updatedOn = now() where p.id=:reqId")
    void updateStatus(@Param("reqId") Integer reqId, @Param("reqStat") String reqStat );

}
