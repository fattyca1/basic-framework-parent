package com.fattyca1.common.domain;

import lombok.Data;

/**
 * <br>基础错误信息</br>
 *
 * @author fattyca1
 */
@Data
public class BaseErrorDTO {
    protected Integer errCode;
    protected String errMsg;
}
