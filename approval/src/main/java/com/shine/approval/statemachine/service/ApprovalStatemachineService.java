package com.shine.approval.statemachine.service;

import com.shine.approval.statemachine.approval.ApprovalEvents;
import com.shine.approval.statemachine.approval.ApprovalState;
import com.shine.approval.statemachine.config.StatemachineApprovalConfigurer;
import com.shine.common.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zhj
 * @Date: 2020/9/24 16:35
 * @Description: 状态机服务类
 */
@Slf4j
@Service
public class ApprovalStatemachineService {

    @Autowired
    private StateMachinePersister<ApprovalState, ApprovalEvents, Map<String, Object>> approvalStateMachinePersist;
    @Resource
    private StateMachineFactory<ApprovalState, ApprovalEvents> approvalStateMachineFactory;

    public boolean execute(String recordId, ApprovalEvents event) {
        boolean success;
        HashMap<String, Object> data = new HashMap<>(2);
        data.put("recordId", recordId);
        data.put("event", event);
        StateMachine<ApprovalState, ApprovalEvents> stateMachine = approvalStateMachineFactory.getStateMachine(StatemachineApprovalConfigurer.APPROVAL_STATEMACHINE_ID);
        stateMachine.start();
        try {
            approvalStateMachinePersist.restore(stateMachine, data);
            // 发送事件，返回是否执行成功
            success = stateMachine.sendEvent(event);
            if (success) {
                approvalStateMachinePersist.persist(stateMachine, data);
            } else {
                log.error("无效操作");
            }
        } catch (Exception e) {
            throw new BaseException("404", e.getMessage());
        } finally {
            stateMachine.stop();
        }
        return success;
    }

}
