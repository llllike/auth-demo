package com.JWT.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {
    private static final long serialVersionUID = -8482946147572784305L;
    /**
     * token
     */
    private String token;
    /**
     * 有效时间：单位：秒
     */
    private Integer expire;

}
