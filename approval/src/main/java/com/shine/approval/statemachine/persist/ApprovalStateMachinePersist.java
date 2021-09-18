package com.shine.approval.statemachine.persist;

import com.shine.approval.dao.entity.RecordStatus;
import com.shine.approval.dao.service.IRecordStatusDaoService;
import com.shine.approval.statemachine.approval.ApprovalEvents;
import com.shine.approval.statemachine.approval.ApprovalState;
import com.shine.approval.statemachine.config.StatemachineApprovalConfigurer;
import com.shine.common.exceptions.BaseException;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;

/**
 * @author: zhj
 * @Date: 2020/9/24 16:37
 * @Description: 状态机 - 持久层操作类
 */
@Component
public class ApprovalStateMachinePersist implements StateMachinePersist<ApprovalState, ApprovalEvents, Map<String, Object>> {

    @Resource
    IRecordStatusDaoService recordStatusDaoService;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void write(StateMachineContext<ApprovalState, ApprovalEvents> ctx, Map<String, Object> map) {
        String recordId = (String) map.get("recordId");
        ApprovalEvents event = (ApprovalEvents) map.get("event");
        // 更新现在的状态 - 作为以后查询的当前状态
        recordStatusDaoService.updateCurrentState(Long.valueOf(recordId), ctx.getState().name());
    }

    @Override
    public StateMachineContext<ApprovalState, ApprovalEvents> read(Map<String, Object> map) {
        Long recordId = Long.parseLong(String.valueOf(map.get("recordId")));
        ApprovalEvents event = (ApprovalEvents) map.get("event");
        String stateMachineId = StatemachineApprovalConfigurer.APPROVAL_STATEMACHINE_ID;
        // 数据库查询当前状态
        String dbState = recordStatusDaoService.getCurrentStateString(recordId);
        // 没有状态记录时，判断是否时初始化事件
        if (dbState == null) {
            // 是初始化事件则初始化记录状态
            if (event.name().equalsIgnoreCase(ApprovalEvents.INIT.name())) {
                ApprovalState initState = ApprovalState.RECEIVED;
                RecordStatus state = new RecordStatus();
                state.setRecordId(recordId);
                state.setUpdateTime(new Date());
                state.setRecordStatus(initState.name());
                state.setStatusMessage(initState.getDesc());
                recordStatusDaoService.save(state);
                return new DefaultStateMachineContext<>(initState, null, null, null, null, stateMachineId);
            } else {
                throw new BaseException("404", "该记录不存在");
            }
        }
        ApprovalState state = EnumSet.allOf(ApprovalState.class).stream().filter(s -> s.name().equalsIgnoreCase(dbState)).findFirst().orElse(null);
        if (state != null) {
            return new DefaultStateMachineContext<>(state, null, null, null, null, stateMachineId);
        } else {
            throw new BaseException("400", "数据库记录的状态异常:" + dbState);
        }
    }
}
