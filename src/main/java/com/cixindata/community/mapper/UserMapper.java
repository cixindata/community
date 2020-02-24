package com.cixindata.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.cixindata.community.model.User;

@Mapper
public interface UserMapper {
	
	@Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModfied})")
	void insert(User user);
	

}
