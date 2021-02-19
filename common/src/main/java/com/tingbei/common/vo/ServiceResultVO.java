package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceResultVO<T> implements Serializable {
    private static final long serialVersionUID = -8020565132638141310L;
    private boolean isSuccess;
    private String code;
    private String message;
    private T data;
}

