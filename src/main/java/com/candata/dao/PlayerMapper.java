package com.candata.dao;

import com.candata.coremodel.Player;

public interface PlayerMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Player record);

    int insertSelective(Player record);

    Player selectByPrimaryKey(Integer pid);

    int updateByPrimaryKeySelective(Player record);

    int updateByPrimaryKey(Player record);
}