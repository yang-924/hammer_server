package com.server.hammer.Repository;

import com.server.hammer.Entity.UserInRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInRoomRepository extends JpaRepository<UserInRoom, Integer> {

    List<UserInRoom> findAllByRoomId(String roomId);
}
