package com.controller.util;

import com.controller.vo.TreeVo;
import com.dao.entity.Function;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    public static TreeVo generateFunctionTree(TreeVo treeVo,List<Function> functionList){
        Integer id=treeVo.getId();
        if(functionList != null && functionList.size()>0){
            List<TreeVo> children=new ArrayList<>();
            for(Function fun:functionList){
                TreeVo treeVo1=generateTree(++id,fun);
                children.add(treeVo1);
            }
            treeVo.setChildren(children);
        }
        return treeVo;
    }

    private static TreeVo generateTree(Integer id, Function function){
        TreeVo treeVo=new TreeVo();
        treeVo.setId(id);
        treeVo.setText(function.getName()+"(id:"+function.getId()+")");
        List<Function> functionList=function.getSubFunctions();
        if(functionList != null && functionList.size()>0){
            List<TreeVo> children=new ArrayList<>();
            for(Function fun:functionList){
                TreeVo treeVo1=generateTree(++id,fun);
                children.add(treeVo1);
            }
            treeVo.setChildren(children);
        }
        return treeVo;
    }
}
