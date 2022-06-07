package com.server.hammer.Repository;

import com.server.hammer.Entity.State;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends MongoRepository<State, Long> {
    State findStateByState(String s);

}
