package com.service;

import com.dao.entity.Function;

import java.util.List;
import java.util.Map;

public interface FunctionService {

    Function findById(Integer id);

    List<Function> findByIdList(List<Integer> ids);

    List<Function> generateFunctionTrees(List<Map<String, Object>> dependResultList,List<Integer> headNodeFunction);

    List<Function> getSubFunctionsByFunction(Integer id);
}
