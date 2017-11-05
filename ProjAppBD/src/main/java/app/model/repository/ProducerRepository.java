package app.model.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import app.model.Producer;

@Transactional
@Repository
public interface ProducerRepository extends PagingAndSortingRepository<Producer, Integer> {

	public Producer findByProducerId(Integer producerId);

}
