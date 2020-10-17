package com.dicp.sse.endpoint;

import com.dicp.sse.payload.NotifyInfo;
import com.nari.dev.platform.exception.model.ErrorCode;
import com.nari.dev.platform.exception.model.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.dicp.sse.config.ApplicationConstant.PUB_SUB_SSE_CHANNEL;

/**
 * 通知用户有消息变更的API
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1/notify")
public class NotifyEndpoint {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostMapping
    public ResponseEntity<ErrorInfo> messageNotify(@RequestBody NotifyInfo notifyInfo) {
        if (Objects.isNull(notifyInfo)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(ErrorCode.invalid_request.name(), "消息不能为空"));
        }
        redisTemplate.convertAndSend(PUB_SUB_SSE_CHANNEL, notifyInfo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
