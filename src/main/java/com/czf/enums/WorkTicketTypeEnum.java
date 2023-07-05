package com.czf.enums;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 工作票类型枚举
 * 工作票类型：变1票-11；变2票-12；变3票-13；线路第一种工作票-21；线路第二种工作票-22；配电第一种工作票-41；配电第二种工作票-42；带电作业工作票-43；低压配网工作单票-44；紧急抢修工作票-51';
 */
@Getter
@AllArgsConstructor
public enum WorkTicketTypeEnum {

    TICKET_CHANGE1(11,"变1票"),

    TICKET_CHANGE2(12,"变2票"),

    TICKET_CHANGE3(13,"变3票"),

    LINE_WORK_TICKET1(21,"线路第一种工作票"),

    LINE_WORK_TICKET2(22,"线路第二种工作票"),

    CIRCUIT_WORK_TICKET1(41,"配电第一种工作票"),

    CIRCUIT_WORK_TICKET2(42,"配电第二种工作票"),

    LIVE_WORK_TICKET(43,"带电作业工作票"),

    LOW_PRESSURE_WORK_TICKET(44,"低压配网工作单票"),

    EMERGENCY_REPAIR_WORK_TICKET(51,"紧急抢修工作票");


    public  Integer code;
    public String desc;

    public static String getDescByCode(Integer code) {
        return Stream.of(WorkTicketTypeEnum.values()).filter(en -> en.code.equals(code)).map(s->s.desc).findFirst().orElse(null);
    }

    public static String getDescByCode(String strCode) {
        if(StrUtil.isBlank(strCode))
            return "";
        Integer code = Integer.valueOf(strCode);
        return Stream.of(WorkTicketTypeEnum.values()).filter(en -> en.code.equals(code)).map(s -> s.desc).findFirst().orElse(null);
    }



}
