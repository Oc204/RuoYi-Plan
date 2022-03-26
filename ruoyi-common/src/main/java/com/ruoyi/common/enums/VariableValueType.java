package com.ruoyi.common.enums;

/**
 * 变量值状态类型
 * 
 * @author ruoyi
 */
public enum VariableValueType
{
    UNDEFINED("undefined", "undefined");

    private final String code;
    private final String info;

    VariableValueType(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
