package ${groupId}.message.output;

import ${groupId}.error.ErrorStatus;
import ${groupId}.error.ServerException;

import java.text.MessageFormat;

/**
 * 统一返回结果类
 * <p>
 * 使用方法：
 * 1. Result r = Result.createBuilder().build();
 * 2. Result r = Result.createBuilder()[.success(true).msg("msg").code(0).data(obj)].build();
 * 3. Result r = Result.ok().build();
 * 4. Result r = Result.ok("msg").build();
 * 5. Result r = Result.ok(data).build();
 * 6. Result r = Result.error().build();
 * 7. Result r = Result.error("msg").build();
 * 8. Result r = Result.error(data).build();
 * 9. Result r = Result.error(ErrorStatus.XXX).build();
 * 10. Result r = Result.error(ErrorStatus.XXX, "msg1", "msg2").build();
 *
 * @author zhangguodong
 */
public class Result {
    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = 1;
    public static final String SUCCESS_MSG = "success";
    public static final String FAIL_MSG = "fail";

    /**
     * 返回是否成功
     */
    private Boolean success;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回消息code
     */
    private Integer code;

    /**
     * 返回消息内容
     */
    private Object data;

    private Result(Builder builder) {
        this.success = builder.success;
        this.msg = builder.msg;
        this.code = builder.code;
        this.data = builder.data;
    }

    /**
     * Result的建造者
     */
    public static class Builder {

        private Boolean success;

        private String msg = SUCCESS_MSG;

        private Integer code = SUCCESS_CODE;

        private Object data;

        public Builder success(Boolean success) {
            this.success = success;
            return this;
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder data(Object data) {
            this.data = data;
            return this;
        }

        public Result build() {
            return new Result(this);
        }
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static Builder ok() {
        return createBuilder().success(Boolean.TRUE);
    }

    public static Builder ok(String msg) {
        return ok().msg(msg);
    }

    public static Builder ok(Object data) {
        return ok().data(data);
    }

    public static Builder error() {
        return createBuilder().success(Boolean.FALSE).code(ERROR_CODE).msg(FAIL_MSG);
    }

    public static Builder error(String msg) {
        return error().msg(msg);
    }

    public static Builder error(ServerException e) {
        return createBuilder().success(Boolean.FALSE).code(e.getErrno()).msg(e.getMessage());
    }

    public static Builder error(ErrorStatus status) {
        return error(status, "");
    }

    /**
     * 使用MessageFormat格式化返回消息
     *
     * @param status  异常枚举
     * @param msgArgs 要拼接的msg
     * @return Result.Builder
     */
    public static Builder error(ErrorStatus status, Object... msgArgs) {
        String msg = MessageFormat.format(status.message(), msgArgs);
        return error().code(status.code()).msg(msg);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
