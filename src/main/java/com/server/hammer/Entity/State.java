package com.server.hammer.Entity;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;
import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "state") //通过collection参数指定当前实体类对应的文档

public class State {
    @Id
    @Field("_id")
    private ObjectId id;

    private String uid;
    private String state;


    public State(String state){

        this.state=state;

    }


    public State(String uid, String state) {
        this.uid=uid;
        this.state=state;
    }
}
