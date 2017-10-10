package com.highplace.service.oauth.dao;

import com.highplace.service.oauth.domain.Country;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CountryDao {

    @Select("select * from country order by countryname desc")
    List<Country> findAll();

}