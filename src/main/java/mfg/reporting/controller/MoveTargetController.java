package mfg.reporting.controller;


import lombok.extern.slf4j.Slf4j;
import mfg.reporting.CommonUtil.JwtUtils;
import mfg.reporting.Exception.ResponseResult;
import mfg.reporting.pojo.MoveTargetPO;
import mfg.reporting.service.MoveTargetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/moveTarget")
@Slf4j
public class MoveTargetController {
    @Resource
    public MoveTargetService moveTarget;

    @Resource
    public JwtUtils jwtUtils;



    @GetMapping("/getMoveTargetList")
    public ResponseResult<List<MoveTargetPO>> getMoveTargetList(
            @RequestParam(value="fab") String fab
    ){
        try{
            List<MoveTargetPO> list = moveTarget.getMoveTargetList(fab);
            return ResponseResult.success("查询成功", list);
        }catch (Exception e){
            return  ResponseResult.fail("查询失败"+e.getMessage());
        }
    }



}
