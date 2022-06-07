package com.server.hammer.Controller;


import com.server.hammer.Entity.State;
import com.server.hammer.Service.StateService;
import com.server.hammer.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/state")
public class StateController {
    @Autowired
    private StateService stateService;

    /**
     * 新增接口
     */
    @GetMapping("/create")

    public Response create(@RequestBody State state) {
        stateService.create(state.getUid(),state.getState());

        return Response.success(stateService.create(state.getUid(),state.getState()));
    }

    /**
     * 更新接口
     */
    @GetMapping("/update")

    public Response update() {
        return Response.success(stateService.update());
    }

    /**
     * 删除接口
     */
    @GetMapping("/delete")

    public Response delete() {
        return Response.success(stateService.delete());
    }

    /**
     * 查询接口
     */
    @GetMapping("/selectById")

    public Response select(@RequestParam String uid) {


        return Response.success(stateService.select());
    }

    @GetMapping("/find")

    public Response find() {
        return Response.success(stateService.findById());
    }

}
