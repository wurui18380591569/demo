package com.controller;

import com.controller.util.TreeUtil;
import com.controller.vo.TreeVo;
import com.dao.entity.Function;
import com.dao.entity.Model;
import com.service.ModelService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping(value="/model",method = RequestMethod.GET)
public class ModelController {
    @Resource
    private ModelService modelService;

    @RequestMapping("getModelList")
    public TreeVo getModelList(){
        List<Model> models=modelService.getAllModel();
        TreeVo treeVo=new TreeVo();
        treeVo.setId(1);
        treeVo.setText("Model List");
        List<TreeVo> treeVos=new ArrayList<>();
        if (models!=null && models.size()>0){
            int index=1;
            for(Model model:models){
                index++;
                TreeVo treeVo_=new TreeVo();
                treeVo_.setId(index);
                treeVo_.setText(model.getName()+"(id:"+model.getModelId()+")");
                treeVos.add(treeVo_);
            }
            treeVo.setChildren(treeVos);
        }
        return treeVo;
    }

    @RequestMapping("getModel")
    public TreeVo getModel(@RequestParam("modelId") Integer modelId){
        Model model=modelService.findById(modelId);
        List<Function> functionList=modelService.getSubFunctionsByModel(modelId);
        Integer id=1;
        TreeVo treeVo=new TreeVo();
        treeVo.setId(id);
        treeVo.setText(model.getName());
        treeVo= TreeUtil.generateFunctionTree(treeVo,functionList);
        return treeVo;
    }

}
