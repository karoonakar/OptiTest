package com.sg.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sg.app.entities.CodchnConfig;
import com.sg.app.repositories.CodchnConfigRepository;
import com.sg.app.rest.model.PropertyDTO;
/**
 * @author Karoonakar
 *
 */


@Service
@Transactional
public class CodchnConfigService {
	
	@Autowired CodchnConfigRepository codchnConfigRepository;
	
	public List<PropertyDTO> findByCodchn(String codchn) {
		
		List<CodchnConfig> codchnConfigList = codchnConfigRepository.findByCodchn(codchn);
		List<PropertyDTO> propertyList = new ArrayList<PropertyDTO>();
		
		for(CodchnConfig codchnConfig : codchnConfigList){
			propertyList.add(new PropertyDTO(codchnConfig.getProperty()));
		}
		
		return  propertyList;
	}
	
	public List<CodchnConfig> findAll() {
		return codchnConfigRepository.findAll();
	}
	
	public List<String> findAllCodchn() {
		return codchnConfigRepository.findAllCodchn();
	}
		
	
}
