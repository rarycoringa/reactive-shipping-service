package br.edu.ufrn.shipping.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufrn.shipping.model.Shipping;

@Repository
public interface ShippingRepository extends ReactiveMongoRepository<Shipping, String> {
    
}
