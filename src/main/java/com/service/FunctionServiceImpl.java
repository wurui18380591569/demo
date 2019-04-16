package com.service;

import com.dao.FunctionDao;
import com.dao.entity.Function;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("functionService")
public class FunctionServiceImpl implements FunctionService {

    @Resource
    private FunctionDao functionDao;

    @Override
    public Function findById(Integer id) {
        if(id != null){
            List<Integer> ids = new ArrayList<>();
            ids.add(id);
            List<Function> functionList=functionDao.findByIdList(ids);
            if(functionList != null && functionList.size()>0){
                return functionList.get(0);
            }
        }
        return null;
    }

    @Override
    public List<Function> findByIdList(List<Integer> ids) {
        if(ids == null){
            ids = new ArrayList<>();
        }
        return functionDao.findByIdList(ids);
    }

    @Override
    public List<Function> getSubFunctionsByFunction(Integer id) {
        List<Map<String, Object>> dependResultList = functionDao.getSubFunctionsByFunction(id);
        if (dependResultList != null && dependResultList.size() > 0) {
            List<Integer> headNodeFunction=functionDao.getFunctionDepend(id);
            return generateFunctionTrees(dependResultList,headNodeFunction);
        }
        return null;
    }

    @Override
    public List<Function> generateFunctionTrees(List<Map<String, Object>> dependResultList, List<Integer> headNodeFunction) {
        //get parent-sub map and sub-parent map
        Map<Integer, List<Integer>> parentFunctionMap = new HashMap<>();
        Map<Integer, List<Integer>> subFunctionMap = new HashMap<>();
        for (int i = 0; i < dependResultList.size(); i++) {
            Map<String, Object> dependResult = dependResultList.get(i);
            Object functionNo = dependResult.get("function_no");
            Object subFunctionNo = dependResult.get("sub_function_no");
            Integer parentId = null;
            Integer subId = null;
            if (functionNo != null) {
                parentId = Integer.valueOf(dependResult.get("function_no").toString());
            }
            if (subFunctionNo != null) {
                subId = Integer.valueOf(dependResult.get("sub_function_no").toString());
            }
            if (subId != null) {
                //parent-sub
                List<Integer> subs = parentFunctionMap.get(parentId);
                if (subs == null) {
                    subs = new ArrayList<>();
                }
                subs.add(subId);
                parentFunctionMap.put(parentId, subs);
                //sub-parent
                List<Integer> parents = subFunctionMap.get(subId);
                if (parents == null) {
                    parents = new ArrayList<>();
                }
                parents.add(parentId);
                subFunctionMap.put(subId, parents);
            } else {
                parentFunctionMap.put(parentId, null);
            }
        }

        List<Integer> functionNoList = new ArrayList<>(parentFunctionMap.keySet());
        List<Function> functionList = findByIdList(functionNoList);
        Map<Integer, Function> funMap = new HashMap<>();
        if (functionList != null && functionList.size() > 0) {
            for (Function function : functionList) {
                funMap.put(function.getId(), function);
            }
        }

        // add subFunctions to its parentFunctions
        for (Map.Entry entry : subFunctionMap.entrySet()) {
            List<Integer> parents = (List<Integer>) entry.getValue();
            if (parents != null && parents.size() > 0) {
                Function subFunction = funMap.get(entry.getKey());
                for (Integer parentid : parents) {
                    Function parentFunction = funMap.get(parentid);
                    List<Function> subFunctionList = parentFunction.getSubFunctions();
                    if (subFunctionList == null) {
                        subFunctionList = new ArrayList<>();
                    }
                    subFunctionList.add(subFunction);
                    parentFunction.setSubFunctions(subFunctionList);
                    funMap.put(parentid, parentFunction);
                }
            }
        }
        List<Function> functions=new ArrayList<>();
        if(headNodeFunction != null && headNodeFunction.size()>0){
            for(Integer funId:headNodeFunction){
                functions.add(funMap.get(funId));
            }
        }
        return functions;
    }
}
