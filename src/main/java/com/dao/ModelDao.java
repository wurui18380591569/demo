package com.dao;

import com.dao.entity.Model;

import java.util.List;
import java.util.Map;

public interface ModelDao {

    List<Model> getAllModel();

    Model findById(Integer id);

    List<Map<String,Object>> getModelDetail(Integer id);

    List<Integer> getModelFunctionDepend(Integer id);

}
