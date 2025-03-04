package org.lr0.server.core;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

/**
 * @author Qnxy
 */
@RequiredArgsConstructor
@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RespStatus {

    SUCCESS("C00000", "操作成功"),
    FAILURE("C00001", "系统异常"),

    PARAMETER_VERIFICATION_FAILED("C00002", "参数验证失败"),
    ACCESS_RESOURCE_DOES_NOT_EXIST("C00003", "访问资源不存在: {0}"),
    UNSUPPORTED_REQUEST_METHOD("C00004", "不支持的请求方式: {0}"),
    CURRENT_PARAMETERS_CANNOT_BE_READ("C0005", "当前参数信息无法读取, 请检查是否存在类型错误!"),

    ;
    private final String code;
    private final String message;

    public String messageFormat(Object... args) {
        if (args == null || args.length == 0) {
            return message;
        }
        return MessageFormat.format(message, args);
    }

}
