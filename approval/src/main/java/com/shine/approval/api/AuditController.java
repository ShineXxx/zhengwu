package com.shine.approval.api;

import com.shine.approval.dao.service.IRecordStatusDaoService;
import com.shine.approval.module.vo.CommitVo;
import com.shine.approval.service.IApprovalService;
import com.shine.approval.service.ISearchService;
import com.shine.approval.statemachine.approval.ApprovalState;
import com.shine.common.Result;
import com.shine.common.ResultFactory;
import com.shine.common.exceptions.BaseException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 审批模块相关 API
 *
 * @author zhaoyao
 */
@Slf4j
@RestController
@RequestMapping("/audit")
@Api(value = "audit", tags = "预审")
public class AuditController {
    @Resource
    IApprovalService approvalService;
    @Resource
    ISearchService searchService;
    @Resource
    IRecordStatusDaoService recordStatusDaoService;

    @ApiOperation(value = "进入审批", httpMethod = "GET")
    @GetMapping("/init")
    public Result<?> init(@RequestParam String materialName) {
        String recordId = approvalService.init(materialName);
        return ResultFactory.wrapper(recordId);
    }

    @ApiOperation("提交任务")
    @PostMapping("/commit")
    public Result<?> commit(@Validated @RequestBody CommitVo vo) {
        approvalService.commit(vo);
        return ResultFactory.wrapper();
    }

    @ApiOperation("获取任务状态")
    @GetMapping("/status")
    public Result<?> getRecordStatus(@RequestParam("recordId") Long recordId) {
        String currState = recordStatusDaoService.getCurrentStateString(recordId);
        if (currState == null) {
            throw new BaseException("404", "record not exist");
        }
        int percent = ApprovalState.valueOf(currState).getPercent();
        return ResultFactory.wrapper(percent);
    }


    @ApiOperation("查询审批结果")
    @GetMapping("/search")
    public Result<?> getResultList(@RequestParam("recordId") String recordId) {

        List<Object> resultList = searchService.search(recordId);

        return ResultFactory.wrapper(resultList);
    }


}
