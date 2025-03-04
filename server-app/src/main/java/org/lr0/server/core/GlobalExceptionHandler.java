package org.lr0.server.core;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.lr0.server.data.R;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static org.lr0.server.core.RespStatus.*;

/**
 * @author Qnxy
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler implements InitializingBean {

    private static final Map<Class<? extends Exception>, Function<Exception, R<?>>> CLASS_EXCEPTION_PROCESSOR_MAP = new ConcurrentHashMap<>();

    /**
     * 添加异常处理映射关系
     *
     * @param exceptionType 异常类型
     * @param function      处理器
     * @param <E>           异常泛型
     */
    private static <E extends Exception> void addExceptionHandler(Class<E> exceptionType, Function<E, R<?>> function) {
        //noinspection unchecked
        CLASS_EXCEPTION_PROCESSOR_MAP.put(exceptionType, (Function<Exception, R<?>>) function);
    }


    /**
     * 构建异常处理关系
     */
    private static void buildExceptionHandlerMapping() {

        // 自定义异常处理
        addExceptionHandler(ProjectException.class, R::ofProjectEx);

        // 参数校验异常处理 @RequestBody
        addExceptionHandler(MethodArgumentNotValidException.class, e -> {
            List<String> errorMessageList = e.getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .filter(it -> it != null && !it.isEmpty())
                    .toList();

            return new R<>(errorMessageList, PARAMETER_VERIFICATION_FAILED);
        });


        // 参数列表校验发生异常
        addExceptionHandler(HandlerMethodValidationException.class, e -> {
            List<String> list = e.getAllErrors()
                    .stream()
                    .map(MessageSourceResolvable::getDefaultMessage)
                    .filter(it -> it != null && !it.isEmpty())
                    .toList();

            return new R<>(list, PARAMETER_VERIFICATION_FAILED);
        });

        // 资源不存在异常
        addExceptionHandler(NoResourceFoundException.class, e -> R.failure(ACCESS_RESOURCE_DOES_NOT_EXIST, e.getResourcePath()));

        // 请求方式不支持异常
        addExceptionHandler(HttpRequestMethodNotSupportedException.class, e -> R.failure(UNSUPPORTED_REQUEST_METHOD, e.getMethod()));

        // MyBatis 框架发出的异常
        addExceptionHandler(MyBatisSystemException.class, e -> {
            if (e.getRootCause() instanceof ProjectException bizException) {
                return R.ofProjectEx(bizException);
            }
            return R.failure(FAILURE);
        });

        // 处理 HttpMessageNotReadableException 异常
        addExceptionHandler(HttpMessageNotReadableException.class, e -> {
            if (e.getRootCause() instanceof ProjectException bizException) {
                return R.ofProjectEx(bizException);
            }

            return R.failure(CURRENT_PARAMETERS_CANNOT_BE_READ);
        });


    }


    @Override
    public void afterPropertiesSet() throws Exception {
        buildExceptionHandlerMapping();
    }

    /**
     * 异常处理
     * 接口统一返回http状态码 200
     * 接口错误根据接口返回状态进行判断, 接口全局统一返回类型 {@link R}
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.OK)
    public R<?> exception(Exception e, HttpServletRequest request) {

        // 打印异常日志信息
        printException(e, request);

        return Optional.ofNullable(CLASS_EXCEPTION_PROCESSOR_MAP.get(e.getClass()))
                .map(it -> it.apply(e))
                .orElseGet(() -> R.failure(FAILURE));
    }

    private void printException(Exception e, HttpServletRequest request) {
        final String requestURI = request.getRequestURI();

        final Throwable printException;
        final String msg;
        if (e instanceof ProjectException bizException) {
            printException = bizException.getCause();

            RespStatus status = bizException.getStatus();
            msg = status.getCode() + "/" + status.messageFormat(bizException.getArgs());
        } else {
            printException = e;
            msg = e.getMessage();
        }

        log.error("请求路径: {} --> 状态码/状态信息: [{}]", requestURI, msg, printException);
    }
}
