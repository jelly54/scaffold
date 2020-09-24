package com.jelly.boot.request;


import javax.validation.constraints.NotNull;

/**
 * @author ：zhang guo dong
 */
public class DemoRequest {
    @NotNull(message = "id 不能为 null")
    private Integer id;
    private String name;
    private String deptName;

    public DemoRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
