package com.server.hammer.Entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")

public class UserInMeeting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "room_id")
    private String meetingId;


}
