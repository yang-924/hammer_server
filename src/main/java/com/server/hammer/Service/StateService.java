package com.server.hammer.Service;

import com.server.hammer.Entity.State;

import java.util.List;

public interface StateService {

 /**
  * 新增
  *
  * @return String
  */
 String create(String id, String news);

 /**
  * 更新
  *
  * @return String
  */
 String update();

 /**
  * 删除
  *
  * @return String
  */
 String delete();

 /**
  * 查询
  *
  * @return String
  */
 List<State> select();

 State findById();

}
