package com.JWT.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * jwt 存储的 内容
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo<T> implements Serializable {
    private T t;
    private String id;

    public UserInfo(T t) {
        this.t = t;
    }
}
