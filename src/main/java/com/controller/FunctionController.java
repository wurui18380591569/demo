package com.controller;

import com.controller.util.TreeUtil;
import com.controller.vo.TreeVo;
import com.dao.entity.Function;
import com.service.FunctionService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping(value="/function",method = RequestMethod.GET)
public class FunctionController {

    @Resource
    private FunctionService functionService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String toTestPage(){
        return "hahhaha";
    }


    @RequestMapping("getFunction")
    public TreeVo getModel(@RequestParam("functionId") Integer functionId){
        Function function = functionService.findById(functionId);
        List<Function> functionList=functionService.getSubFunctionsByFunction(functionId);
        Integer id=1;
        TreeVo treeVo=new TreeVo();
        treeVo.setId(id);
        treeVo.setText(function.getName());
        treeVo= TreeUtil.generateFunctionTree(treeVo,functionList);
        return treeVo;
    }

}
