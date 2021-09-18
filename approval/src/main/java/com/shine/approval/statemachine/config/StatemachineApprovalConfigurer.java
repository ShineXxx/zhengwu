package com.shine.approval.statemachine.config;

import com.shine.approval.statemachine.approval.ApprovalEvents;
import com.shine.approval.statemachine.approval.ApprovalState;
import com.shine.approval.statemachine.persist.ApprovalStateMachinePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;
import java.util.Map;

/**
 * @author: zhj
 * @Date: 2020/9/23 10:48
 * @Description:
 */
@Configuration
@EnableStateMachineFactory(name = "approvalStateMachineFactory")
public class StatemachineApprovalConfigurer extends EnumStateMachineConfigurerAdapter<ApprovalState, ApprovalEvents> {

    public static final String APPROVAL_STATEMACHINE_ID = "approval_state_machine";

    @Autowired
    private ApprovalStateMachinePersist approvalStateMachinePersist;

    @Bean
    @Scope("prototype")
    public StateMachinePersister<ApprovalState, ApprovalEvents, Map<String, Object>> genApprovalStateMachinePersist() {
        return new DefaultStateMachinePersister<>(approvalStateMachinePersist);
    }

    @Override
    public void configure(StateMachineStateConfigurer<ApprovalState, ApprovalEvents> states)
            throws Exception {
        states
                .withStates()
                // 初始状态
                .initial(ApprovalState.RECEIVED)
                .states(EnumSet.allOf(ApprovalState.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ApprovalState, ApprovalEvents> transitions)
            throws Exception {
        // 正常流程 - 全流程
        transitions
                .withExternal()
                .source(ApprovalState.RECEIVED).target(ApprovalState.RECEIVED)
                .event(ApprovalEvents.INIT)
                .and()
                .withExternal()
                .source(ApprovalState.RECEIVED).target(ApprovalState.PROCESSING)
                .event(ApprovalEvents.RECEIVE)
                .and()
                .withExternal()
                .source(ApprovalState.FINISHED).target(ApprovalState.PROCESSING)
                .event(ApprovalEvents.RECEIVE)
                .and()
                .withExternal()
                .source(ApprovalState.ERROR).target(ApprovalState.PROCESSING)
                .event(ApprovalEvents.RECEIVE)
                .and()
                .withExternal()
                .source(ApprovalState.PROCESSING).target(ApprovalState.PROCESSING)
                .event(ApprovalEvents.RECEIVE)
                .and()
                .withExternal()
                .source(ApprovalState.PROCESSING).target(ApprovalState.RECEIVE_FROM_JAVA)
                .event(ApprovalEvents.RECEIVE_FROM_JAVA)
                .and()
                .withExternal()
                .source(ApprovalState.RECEIVE_FROM_JAVA).target(ApprovalState.CLASSIFYING_DOCUMENTS)
                .event(ApprovalEvents.DOCUMENT_CLASSIFICATION)
                .and()
                .withExternal()
                .source(ApprovalState.CLASSIFYING_DOCUMENTS).target(ApprovalState.EXTRACTING_INFORMATION)
                .event(ApprovalEvents.INFORMATION_EXTRACTION)
                .and()
                .withExternal()
                .source(ApprovalState.EXTRACTING_INFORMATION).target(ApprovalState.RULE_APPROVAL)
                .event(ApprovalEvents.RULE_CALL)
                .and()
                .withExternal()
                .source(ApprovalState.RULE_APPROVAL).target(ApprovalState.FINISHED)
                .event(ApprovalEvents.PROCESS_SUCCESS_MSG);
        // 异常流程 - 全流程
        transitions
                .withExternal()
                .source(ApprovalState.RECEIVE_FROM_JAVA).target(ApprovalState.ERROR)
                .event(ApprovalEvents.PROCESS_ERROR_MSG)
                .and()
                .withExternal()
                .source(ApprovalState.RECEIVED).target(ApprovalState.ERROR)
                .event(ApprovalEvents.PROCESS_ERROR_MSG)
                .and()
                .withExternal()
                .source(ApprovalState.PROCESSING).target(ApprovalState.ERROR)
                .event(ApprovalEvents.PROCESS_ERROR_MSG)
                .and()
                .withExternal()
                .source(ApprovalState.CLASSIFYING_DOCUMENTS).target(ApprovalState.ERROR)
                .event(ApprovalEvents.PROCESS_ERROR_MSG)
                .and()
                .withExternal()
                .source(ApprovalState.EXTRACTING_INFORMATION).target(ApprovalState.ERROR)
                .event(ApprovalEvents.PROCESS_ERROR_MSG)
                .and()
                .withExternal()
                .source(ApprovalState.RULE_APPROVAL).target(ApprovalState.ERROR)
                .event(ApprovalEvents.PROCESS_ERROR_MSG);
        // 处理成功直接跳转结果
        transitions
                .withExternal()
                .source(ApprovalState.PROCESSING).target(ApprovalState.FINISHED)
                .event(ApprovalEvents.PROCESS_SUCCESS_MSG)
                .and()
                .withExternal()
                .source(ApprovalState.RECEIVE_FROM_JAVA).target(ApprovalState.FINISHED)
                .event(ApprovalEvents.PROCESS_SUCCESS_MSG)
                .and()
                .withExternal()
                .source(ApprovalState.CLASSIFYING_DOCUMENTS).target(ApprovalState.FINISHED)
                .event(ApprovalEvents.PROCESS_SUCCESS_MSG)
                .and()
                .withExternal()
                .source(ApprovalState.EXTRACTING_INFORMATION).target(ApprovalState.FINISHED)
                .event(ApprovalEvents.PROCESS_SUCCESS_MSG)
                .and()
                .withExternal()
                .source(ApprovalState.RULE_APPROVAL).target(ApprovalState.FINISHED)
                .event(ApprovalEvents.PROCESS_SUCCESS_MSG);
        // 接收转正在处理
        transitions
                .withExternal()
                .source(ApprovalState.RECEIVE_FROM_JAVA).target(ApprovalState.PROCESSING)
                .event(ApprovalEvents.RECEIVE)
                .and()
                .withExternal()
                .source(ApprovalState.CLASSIFYING_DOCUMENTS).target(ApprovalState.PROCESSING)
                .event(ApprovalEvents.RECEIVE)
                .and()
                .withExternal()
                .source(ApprovalState.RULE_APPROVAL).target(ApprovalState.PROCESSING)
                .event(ApprovalEvents.RECEIVE)
                .and()
                .withExternal()
                .source(ApprovalState.EXTRACTING_INFORMATION).target(ApprovalState.PROCESSING)
                .event(ApprovalEvents.RECEIVE);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<ApprovalState, ApprovalEvents> config)
            throws Exception {
        config.withConfiguration()
                .machineId(APPROVAL_STATEMACHINE_ID);
    }
}
