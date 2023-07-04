package com.czf.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　 ┗┻┛　┗┻┛
 * 人类
 *
 * @author 1916
 * @create 2023-06-24 12:53
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
@EqualsAndHashCode
public class Person {
    @ExcelProperty(value = "编号",index = 0)
    private Integer id;

    @ExcelProperty(value = "名字",index = 1)
    private String name;

    @ExcelProperty(value = "年龄",index = 2)
    private Integer age;
}
