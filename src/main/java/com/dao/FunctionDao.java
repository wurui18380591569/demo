package com.dao;

import com.dao.entity.Function;

import java.util.List;
import java.util.Map;

public interface FunctionDao {

    List<Function> findByIdList(List<Integer> idList);

    List<Map<String,Object>> getSubFunctionsByFunction(Integer id);

    List<Integer> getFunctionDepend(Integer id);
}
