package in.ineuron.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ineuron.dao.ITouristRepo;
import in.ineuron.exception.TouristNotFoundException;
import in.ineuron.model.Tourist;

@Service
public class TouristMgmtServiceImpl implements ITouristMgmtService {

	@Autowired
	private ITouristRepo repo;
	
	@Override
	public String registerTourist(Tourist tourist) {

		Integer tid = repo.save(tourist).getTid();
		return "Tourist is required having the ticket id :: " +tid;
	}

	@Override
	public List<Tourist> fetchAllTourists() {
		List<Tourist> list = repo.findAll();
		list.sort((t1,t2)->t1.getTid().compareTo(t2.getTid()));
		return list;
	}

	@Override
	public Tourist fetchTouristById(Integer id) {
//		Optional<Tourist> optional = repo.findById(id);
//		if (optional.isPresent()) {
//			return optional.get();
//		} else {
//			throw new TouristNotFoundException("Tourist with id "+id+" Not Found");
//		}
		
		return repo.findById(id).orElseThrow(()-> new TouristNotFoundException("Tourist with id "+id+" Not Found"));
	}

	@Override
	public String updateToursitByDetailse(Tourist tourist) {
		
		Optional<Tourist> optional = repo.findById(tourist.getTid());
		if (optional.isPresent()) {
			repo.save(tourist);//save() performs voth insert and update depends on id value
			return "Tourist with the id:: "+tourist.getTid()+" updated";
		} else {
			throw new TouristNotFoundException("tourist with id:: "+tourist.getTid()+ " not available for updation");
		}
	}

	@Override
	public String updateTouristById(Integer id, Float hikePercent) {
		
		Optional<Tourist> optional = repo.findById(id);
		if(optional.isPresent()) {
			Tourist tourist = optional.get();
			tourist.setBudget(tourist.getBudget() + (tourist.getBudget() * (hikePercent / 100)));
			repo.save(tourist);
			return "Tourist budget is updated for the id:: " + tourist.getTid();
		}else {
			throw new TouristNotFoundException("Tourst not found for the id:: " + id);
		}
		
	}

	@Override
	public String deleteTouristById(Integer id) {
		Optional<Tourist> optional = repo.findById(id);
		if (optional.isPresent()) {
			repo.delete(optional.get());
			return "Tourist with the id:: "+id+" deleted...";			
		} else {
			throw new TouristNotFoundException("Tourist not found for the id " + id);

		}
		
	}
	
	

}
