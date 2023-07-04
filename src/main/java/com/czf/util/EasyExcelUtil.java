package com.czf.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

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
 * EasyExcel工具类
 *
 * @author 1916
 * @create 2023-06-29 19:51
 */
@Slf4j
public class EasyExcelUtil {


    /**
     * 获取模版文件路径
     * @param fileName
     * @return
     */
    private static InputStream getResourceAsStream(String fileName){
      return EasyExcelUtil.class.getClassLoader().getResourceAsStream(fileName);
    }

    private static void setHeader(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        //设置响应头内容，以vnd.ms-excel方式打开数据
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        //告诉所有浏览器不要缓存
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        //设置时间值属性的响应头信息，不要缓存
        response.setDateHeader("Expires", -1);
        //(设置输出的编码格式)指定输出到客户端的时候，这些文字使用UTF-8编码
        response.setCharacterEncoding("UTF-8");
    }

    /**
     *
     * @param list 数据列表
     * @param pojoClass 导出对象的Class类型
     * @param fileName 导出文件名
     * @param templateFileName 模版文件的名称（有路径的带上路径）
     * @param response
     */
    public static void readTemplateExportExcel(List<?> list, Class<?> pojoClass, String fileName, String templateFileName,
                                               HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        try {
            //设置请求头
            setHeader(fileName,response);
            //读取模版
            InputStream resourceAsStream = getResourceAsStream(templateFileName);
            excelWriter = EasyExcel.write(response.getOutputStream(), pojoClass)
                    .withTemplate(resourceAsStream)
                    .needHead(false)//不需要pojoClass字段映射生成表头
                    .build();
            WriteSheet sheet = EasyExcel.writerSheet().build();
            //多行数据填充
            excelWriter.write(list,sheet);
        } catch (IOException e) {
           log.error("EasyExcelUtil文件到处异常：",e);
        }finally {
            //关闭流
            excelWriter.finish();
        }
    }


    public static void exportExcel(List<?> list, Class<?> pojoClass, String fileName,Boolean isNeedHead, String sheetName,
                                               HttpServletResponse response) {
        ExcelWriter excelWriter = null;
        try {
            //设置请求头
            setHeader(fileName,response);
            excelWriter = EasyExcel.write(response.getOutputStream(), pojoClass)
                    .needHead(isNeedHead)//不需要pojoClass字段映射生成表头
                    .build();
            WriteSheet sheet = EasyExcel.writerSheet(sheetName).build();
            //多行数据填充
            excelWriter.write(list,sheet);
        } catch (IOException e) {
            log.error("EasyExcelUtil文件到处异常：",e);
        }finally {
            //关闭流
            excelWriter.finish();
        }
    }


}
