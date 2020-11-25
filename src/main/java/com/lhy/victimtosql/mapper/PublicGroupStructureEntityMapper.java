package com.lhy.victimtosql.mapper;

import com.lhy.victimtosql.dto.GetGroupNameRandomDTO;
import com.lhy.victimtosql.entity.PublicGroupStructureEntity;

import java.util.List;

/**
 * @ClassName PublicGroupStructureEntityMapper
 * @Description mapper
 * @Author lihengyu
 * @Date 2020/11/23 19:37
 * @Version 1.0
 */
public interface PublicGroupStructureEntityMapper {

    List<PublicGroupStructureEntity> getAll ();

    int insert(PublicGroupStructureEntity entity);

    int insertBatch(List<PublicGroupStructureEntity> list);

    List<String> getGroupNameRandom(GetGroupNameRandomDTO dto);
}
