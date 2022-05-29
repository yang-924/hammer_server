package com.server.hammer.Entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")

public class UserInRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "room_id")
    private String roomId;

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }
    public String getRoomID(){
        return this.roomId;
    }
}
