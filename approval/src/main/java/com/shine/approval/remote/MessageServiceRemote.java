package com.shine.approval.remote;

import com.shine.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhaoyao
 * @date 2019/8/29 17:12
 */
@FeignClient(url = "http://baidu.com", name = "message")
public interface MessageServiceRemote {

    /**
     * 提交材料
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    Result recordSubmit(@RequestBody Object request);

}
