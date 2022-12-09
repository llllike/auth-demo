package com.trans.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Sender {
    // 收件人
    private String[] to;
    //  主题
    private String subject;
    // 内容
    private String content;
}
