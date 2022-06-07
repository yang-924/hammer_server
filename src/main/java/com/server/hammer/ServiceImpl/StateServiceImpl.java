package com.server.hammer.ServiceImpl;

import com.server.hammer.Repository.StateRepository;
import com.server.hammer.Service.StateService;
import com.server.hammer.Entity.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增
     *
     * @return String
     */
    @Override
    public String create(String id,String  news) {
        //第一种方式，直接继承xxxRepository接口
        State state = new State(id,news);
        stateRepository.save(state);
        log.info("第一种方式新增成功，state：" + state);
        //mongoTemplate.insert(state);
        log.info(state.toString());

        log.info("【状态接口】新增成功");
        return "新增成功";
    }

    /**
     * 更新
     *
     * @return String
     */
    @Override
    public String update() {
        //第一种方式，直接继承xxxRepository接口
       // State state = State.builder().id("1").state("666").build();
       // stateRepository.save(state);
       // log.info(state.toString());
        log.info("【状态接口】更新成功");
        return "更新成功";
    }

    /**
     * 删除
     *
     * @return String
     */
    @Override
    public String delete() {
        //第一种方式，直接继承xxxRepository接口
        //stateRepository.deleteById("6");

        log.info("【状态接口】删除成功");
        return "删除成功";
    }

    /**
     * 查询
     *
     * @return String
     */
    @Override
    public List<State> select() {
        //第一种方式，直接继承xxxRepository接口
        List<State> stateList = stateRepository.findAll();
        //System.out.println("第一种方式，employeeList：" + stateList);




        log.info("【员工接口】查询成功");
        return stateList;
    }

    @Override
    public State findById(){
        State state =stateRepository.findStateByState("11234");

        log.info(state.getId().toString());
        return state;
    }
}

