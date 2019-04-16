package com.service;

import com.dao.entity.Function;
import com.dao.entity.Model;

import java.util.List;

public interface ModelService {

    List<Model> getAllModel();

    Model findById(Integer id);

    List<Function> getSubFunctionsByModel(Integer id);
}
