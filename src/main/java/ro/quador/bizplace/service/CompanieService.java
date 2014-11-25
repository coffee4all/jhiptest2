package ro.quador.bizplace.service;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.quador.bizplace.domain.Activitate;
import ro.quador.bizplace.domain.Companie;
import ro.quador.bizplace.domain.Judet;
import ro.quador.bizplace.domain.User;
import ro.quador.bizplace.repository.CompanieRepository;
import ro.quador.bizplace.web.rest.dto.CompanieDTO;

@Service
@Transactional
public class CompanieService {

    private final Logger log = LoggerFactory.getLogger(CompanieService.class);
    
    @Inject
    private CompanieRepository companieRepository;
    
    
    public Companie createCompanie(CompanieDTO companieDTO, String userLogin){
    	
    	Companie companie = new Companie();
    	companie.setCui(companieDTO.getCui());
    	companie.setNume(companieDTO.getNume());
    	companie.setTelefon(companieDTO.getTelefon());
    	companie.setEmail(companieDTO.getEmail());
    	companie.setWebsite(companieDTO.getWebsite());
    	companie.setAdresa(companieDTO.getAdresa());
    	companie.setJudet(new Judet(companieDTO.getJudet()));
    	companie.setActivitate(new Activitate(companieDTO.getActivitate()));
    	companie.setUser(new User(userLogin));
    	companie.setCreatala(new LocalDate()); //TODO ??? e corect fara timezone ??
    	
    	
    	companieRepository.save(companie);
    	log.debug("Created new Companie {}", companie);
        return companie;
    	
    }
	

    
}
