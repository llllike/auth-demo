package com.hv.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;


@Data
public class User {
    @NotNull(message = "用户id不能为空")
    private Integer id;

    @NotEmpty(message = "用户名不能为空")
    @Length(max = 50, message = "用户名长度不能超过50")
    private String username;

    @Max(value = 80,message = "年龄最大为80")
    @Min(value = 18,message = "年龄最小为18")
    private int age;

    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;
}