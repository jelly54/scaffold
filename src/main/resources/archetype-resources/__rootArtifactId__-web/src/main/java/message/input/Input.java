package com.jelly.boot.message.input;

/**
 * 统一入参格式
 *
 * @author ：zhang guo dong
 */
public class Input<T> {

    private T params;

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "Input{" +
                "params=" + params +
                '}';
    }
}
