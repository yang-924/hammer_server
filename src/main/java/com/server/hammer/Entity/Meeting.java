package com.server.hammer.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;



@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meetings")
public class Meeting {
    @Id
    private Integer id;
    @Column(name = "mid")
    private String mid;
    @Column(name = "weekday")
    private Integer weekday;
    @Column(name = "time")
    private Time time;

    @Column(name = "tid")
    private String tid;
    @Column(name = "name")
    private String name;
}
