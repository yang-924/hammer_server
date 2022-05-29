package com.server.hammer;

import org.springframework.data.jpa.repository.JpaRepository;
import com.server.hammer.State;
public interface StateRepository extends JpaRepository<State, Integer> {

    State findStateById(Integer Id);
}
