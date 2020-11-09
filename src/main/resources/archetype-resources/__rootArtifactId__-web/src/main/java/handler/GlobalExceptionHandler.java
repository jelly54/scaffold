package ${groupId}.handler;

import ${groupId}.message.output.Result;
import ${groupId}.error.ErrorStatus;
import ${groupId}.error.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 全局异常拦截
 *
 * @author guodongzhang
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 拦截进入controller 参数绑定异常
     *
     * @param request request请求
     * @param e       参数绑定异常
     * @return spring http response entity
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public Result bindExceptionHandler(HttpServletRequest request, BindException e) {
        return filterErrorMsg(request, e.getBindingResult().getFieldErrors(), e);
    }

    /**
     * 拦截 方法参数异常异常
     *
     * @param request request请求
     * @param e       业务异常
     * @return spring http response entity
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        return filterErrorMsg(request, e.getBindingResult().getFieldErrors(), e);
    }

    /**
     * 拦截业务异常
     *
     * @param request request请求
     * @param e       业务异常
     * @return spring http response entity
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ServerException.class)
    public Result businessExceptionHandler(HttpServletRequest request, ServerException e) {
        log.warn("Capture a business exception. uri: {}, msg: {}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(e).build();
    }

    /**
     * 拦截业务异常
     *
     * @param request request请求
     * @param e       业务异常
     * @return spring http response entity
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(HttpServletRequest request, Exception e) {
        log.warn("Capture a global exception. uri: {}, msg: {}", request.getRequestURI(), e.getMessage(), e);
        return Result.error(ErrorStatus.INTERNAL_EXCEPTION).build();
    }

    private Result filterErrorMsg(HttpServletRequest request, List<FieldError> fieldErrors, Exception e) {
        log.warn("Capture a data check exception. uri: {}, msg: {}", request.getRequestURI(), e.getMessage(), e);
        List<String> errorMsgList = new ArrayList<>();
        for (FieldError fieldError : fieldErrors) {
            errorMsgList.add(fieldError.getDefaultMessage());
        }
        return Result.error(ErrorStatus.ILLEGAL_DATA, errorMsgList.toArray()).build();
    }
}