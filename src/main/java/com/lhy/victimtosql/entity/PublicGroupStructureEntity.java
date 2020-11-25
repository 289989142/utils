package com.lhy.victimtosql.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName publicSroupStructure
 * @Description 实体类
 * @Author lihengyu
 * @Date 2020/11/23 19:34
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicGroupStructureEntity {

    String groupname;

    String childId;

    Integer childName;

    String childType;

    Long severType;

}
