package com.czf.common;


import cn.hutool.http.HttpStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * 操作消息提醒
 * 
 * @author ruoyi
 */
@Data
public class AjaxResult<T> implements Serializable
{


//    /** 状态码 */
//    public static final String CODE_TAG = "code";
//
//    /** 返回内容 */
//    public static final String MSG_TAG = "msg";
//
//    /** 数据对象 */
//    public static final String DATA_TAG = "data";

    private int code;

    private String msg;

    private T data;

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * 
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code, String msg)
    {
//        super.put(CODE_TAG, code);
//        super.put(MSG_TAG, msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, T data)
    {
        this.code=code;
        this.msg=msg;
        this.data=data;
    }

    /**
     * 返回成功消息
     * 
     * @return 成功消息
     */
    public static <T> AjaxResult<T> success()
    {
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     * 
     * @return 成功消息
     */
    public static <T> AjaxResult<T> success(T data)
    {
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @return 成功消息
     */
    public static <T> AjaxResult<T> success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <T> AjaxResult<T> success(String msg, T data)
    {
        return new AjaxResult<>(HttpStatus.HTTP_OK, msg, data);
    }




    /**
     * 返回错误消息
     * 
     * @return 错误消息
     */
    public static<T> AjaxResult<T> error()
    {
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @return 错误消息
     */
    public static<T> AjaxResult<T> error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 错误消息
     */
    public static <T> AjaxResult<T> error(String msg, T data)
    {
        return new AjaxResult<>(800, msg, data);
    }

    /**
     * 返回错误消息
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @return 错误消息
     */
    public static <T> AjaxResult<T> error(int code, String msg)
    {
        return new AjaxResult<>(code, msg, null);
    }

    /**
     * 是否为成功消息
     *
     * @return 结果
     */
    public boolean isSuccess()
    {
        return Objects.equals(HttpStatus.HTTP_OK, this.code);
    }

    /**
     * 是否为错误消息
     *
     * @return 结果
     */
    public boolean isError()
    {
        return !isSuccess();
    }
//
//    /**
//     * 方便链式调用
//     *
//     * @param key
//     * @param value
//     * @return
//     */
//    @Override
//    public AjaxResult put(String key, Object value)
//    {
//        super.put(key, value);
//        return this;
//    }

}
