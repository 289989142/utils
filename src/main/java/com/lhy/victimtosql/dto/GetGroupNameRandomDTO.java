package com.lhy.victimtosql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName getGroupNameRandomDTO
 * @Description 数据传输对象
 * @Author lihengyu
 * @Date 2020/11/24 11:41
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetGroupNameRandomDTO {

    Integer zbzk;

    Integer limit;
}
