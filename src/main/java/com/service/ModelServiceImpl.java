package com.service;

import com.dao.ModelDao;
import com.dao.entity.Function;
import com.dao.FunctionDao;
import com.dao.entity.Model;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("modelService")
public class ModelServiceImpl implements ModelService {

    @Resource
    private ModelDao modelDao;
    @Resource
    private FunctionService functionService;


    @Override
    public List<Model> getAllModel() {
        return modelDao.getAllModel();
    }

    @Override
    public Model findById(Integer id) {
        return modelDao.findById(id);
    }

    @Override
    public List<Function> getSubFunctionsByModel(Integer id) {
        List<Map<String, Object>> dependResultList = modelDao.getModelDetail(id);
        if (dependResultList != null && dependResultList.size() > 0) {
            List<Integer> headNodeFunction=modelDao.getModelFunctionDepend(id);
            return functionService.generateFunctionTrees(dependResultList,headNodeFunction);
        }
        return null;

    }

}
