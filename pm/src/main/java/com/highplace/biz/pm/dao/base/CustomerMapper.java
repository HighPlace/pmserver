package com.highplace.biz.pm.dao.base;

import com.highplace.biz.pm.domain.base.Customer;
import com.highplace.biz.pm.domain.base.CustomerExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface CustomerMapper {

    // ----- mybatis generator外新增的方法------ //
    @SelectProvider(type=CustomerSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
            @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
            @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
            @Result(column="customer_name", property="customerName", jdbcType=JdbcType.VARCHAR),
            @Result(column="identity_type", property="identityType", jdbcType=JdbcType.INTEGER),
            @Result(column="identity_no", property="identityNo", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
            @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
            @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
            @Result(column="wechat", property="wechat", jdbcType=JdbcType.VARCHAR),
            @Result(column="lang", property="lang", jdbcType=JdbcType.VARCHAR),
            @Result(column="nation", property="nation", jdbcType=JdbcType.VARCHAR),
            @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
            @Result(column="birth", property="birth", jdbcType=JdbcType.DATE),
            @Result(column="ident_start_date", property="identStartDate", jdbcType=JdbcType.DATE),
            @Result(column="ident_end_date", property="identEndDate", jdbcType=JdbcType.DATE),
            @Result(column="ident_pic", property="identPic", jdbcType=JdbcType.VARCHAR),
            @Result(column="backup_phone_1", property="backupPhone1", jdbcType=JdbcType.VARCHAR),
            @Result(column="backup_phone_2", property="backupPhone2", jdbcType=JdbcType.VARCHAR),
            @Result(column="emergency_contact_name", property="emergencyContactName", jdbcType=JdbcType.VARCHAR),
            @Result(column="emergency_contact_phone", property="emergencyContactPhone", jdbcType=JdbcType.VARCHAR),
            @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
            @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR),
            @Result(column="customer_id", property="relationList", many=@Many(select="com.highplace.biz.pm.dao.base.RelationMapper.selectByCustomerIdWithCar")),
    })
    List<Customer> selectByExampleWithRelationAndCarWithBLOBs(CustomerExample example);

    @Select("select product_inst_id, customer_name, phone from t_customer")
    @Results({
            @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
            @Result(column="customer_name", property="customerName", jdbcType=JdbcType.VARCHAR),
            @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR)
    })
    List<Customer> selectAllProductInstIdAndNameAndPhone();

    // ----- end ------ //

    @SelectProvider(type=CustomerSqlProvider.class, method="countByExample")
    long countByExample(CustomerExample example);

    @DeleteProvider(type=CustomerSqlProvider.class, method="deleteByExample")
    int deleteByExample(CustomerExample example);

    @Delete({
        "delete from t_customer",
        "where customer_id = #{customerId,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long customerId);

    @Insert({
        "insert into t_customer (product_inst_id, customer_name, ",
        "identity_type, identity_no, ",
        "phone, alias_name, ",
        "email, wechat, lang, ",
        "nation, gender, ",
        "birth, ident_start_date, ",
        "ident_end_date, ident_pic, ",
        "backup_phone_1, backup_phone_2, ",
        "emergency_contact_name, emergency_contact_phone, ",
        "create_time, modify_time, ",
        "remark)",
        "values (#{productInstId,jdbcType=VARCHAR}, #{customerName,jdbcType=VARCHAR}, ",
        "#{identityType,jdbcType=INTEGER}, #{identityNo,jdbcType=VARCHAR}, ",
        "#{phone,jdbcType=VARCHAR}, #{aliasName,jdbcType=VARCHAR}, ",
        "#{email,jdbcType=VARCHAR}, #{wechat,jdbcType=VARCHAR}, #{lang,jdbcType=VARCHAR}, ",
        "#{nation,jdbcType=VARCHAR}, #{gender,jdbcType=VARCHAR}, ",
        "#{birth,jdbcType=DATE}, #{identStartDate,jdbcType=DATE}, ",
        "#{identEndDate,jdbcType=DATE}, #{identPic,jdbcType=VARCHAR}, ",
        "#{backupPhone1,jdbcType=VARCHAR}, #{backupPhone2,jdbcType=VARCHAR}, ",
        "#{emergencyContactName,jdbcType=VARCHAR}, #{emergencyContactPhone,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{modifyTime,jdbcType=TIMESTAMP}, ",
        "#{remark,jdbcType=LONGVARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="customerId", before=false, resultType=Long.class)
    int insert(Customer record);

    @InsertProvider(type=CustomerSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="customerId", before=false, resultType=Long.class)
    int insertSelective(Customer record);

    @SelectProvider(type=CustomerSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="customer_name", property="customerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="identity_type", property="identityType", jdbcType=JdbcType.INTEGER),
        @Result(column="identity_no", property="identityNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="wechat", property="wechat", jdbcType=JdbcType.VARCHAR),
        @Result(column="lang", property="lang", jdbcType=JdbcType.VARCHAR),
        @Result(column="nation", property="nation", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
        @Result(column="birth", property="birth", jdbcType=JdbcType.DATE),
        @Result(column="ident_start_date", property="identStartDate", jdbcType=JdbcType.DATE),
        @Result(column="ident_end_date", property="identEndDate", jdbcType=JdbcType.DATE),
        @Result(column="ident_pic", property="identPic", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_1", property="backupPhone1", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_2", property="backupPhone2", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_name", property="emergencyContactName", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_phone", property="emergencyContactPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Customer> selectByExampleWithBLOBs(CustomerExample example);

    @SelectProvider(type=CustomerSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="customer_name", property="customerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="identity_type", property="identityType", jdbcType=JdbcType.INTEGER),
        @Result(column="identity_no", property="identityNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="wechat", property="wechat", jdbcType=JdbcType.VARCHAR),
        @Result(column="lang", property="lang", jdbcType=JdbcType.VARCHAR),
        @Result(column="nation", property="nation", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
        @Result(column="birth", property="birth", jdbcType=JdbcType.DATE),
        @Result(column="ident_start_date", property="identStartDate", jdbcType=JdbcType.DATE),
        @Result(column="ident_end_date", property="identEndDate", jdbcType=JdbcType.DATE),
        @Result(column="ident_pic", property="identPic", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_1", property="backupPhone1", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_2", property="backupPhone2", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_name", property="emergencyContactName", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_phone", property="emergencyContactPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Customer> selectByExample(CustomerExample example);

    @Select({
        "select",
        "customer_id, product_inst_id, customer_name, identity_type, identity_no, phone, ",
        "alias_name, email, wechat, lang, nation, gender, birth, ident_start_date, ident_end_date, ",
        "ident_pic, backup_phone_1, backup_phone_2, emergency_contact_name, emergency_contact_phone, ",
        "create_time, modify_time, remark",
        "from t_customer",
        "where customer_id = #{customerId,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="customer_id", property="customerId", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="product_inst_id", property="productInstId", jdbcType=JdbcType.VARCHAR),
        @Result(column="customer_name", property="customerName", jdbcType=JdbcType.VARCHAR),
        @Result(column="identity_type", property="identityType", jdbcType=JdbcType.INTEGER),
        @Result(column="identity_no", property="identityNo", jdbcType=JdbcType.VARCHAR),
        @Result(column="phone", property="phone", jdbcType=JdbcType.VARCHAR),
        @Result(column="alias_name", property="aliasName", jdbcType=JdbcType.VARCHAR),
        @Result(column="email", property="email", jdbcType=JdbcType.VARCHAR),
        @Result(column="wechat", property="wechat", jdbcType=JdbcType.VARCHAR),
        @Result(column="lang", property="lang", jdbcType=JdbcType.VARCHAR),
        @Result(column="nation", property="nation", jdbcType=JdbcType.VARCHAR),
        @Result(column="gender", property="gender", jdbcType=JdbcType.VARCHAR),
        @Result(column="birth", property="birth", jdbcType=JdbcType.DATE),
        @Result(column="ident_start_date", property="identStartDate", jdbcType=JdbcType.DATE),
        @Result(column="ident_end_date", property="identEndDate", jdbcType=JdbcType.DATE),
        @Result(column="ident_pic", property="identPic", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_1", property="backupPhone1", jdbcType=JdbcType.VARCHAR),
        @Result(column="backup_phone_2", property="backupPhone2", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_name", property="emergencyContactName", jdbcType=JdbcType.VARCHAR),
        @Result(column="emergency_contact_phone", property="emergencyContactPhone", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="modify_time", property="modifyTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="remark", property="remark", jdbcType=JdbcType.LONGVARCHAR)
    })
    Customer selectByPrimaryKey(Long customerId);

    @UpdateProvider(type=CustomerSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Customer record, @Param("example") CustomerExample example);

    @UpdateProvider(type=CustomerSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Customer record, @Param("example") CustomerExample example);

    @UpdateProvider(type=CustomerSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Customer record, @Param("example") CustomerExample example);

    @UpdateProvider(type=CustomerSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Customer record);

    @Update({
        "update t_customer",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "customer_name = #{customerName,jdbcType=VARCHAR},",
          "identity_type = #{identityType,jdbcType=INTEGER},",
          "identity_no = #{identityNo,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "alias_name = #{aliasName,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "wechat = #{wechat,jdbcType=VARCHAR},",
          "lang = #{lang,jdbcType=VARCHAR},",
          "nation = #{nation,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=VARCHAR},",
          "birth = #{birth,jdbcType=DATE},",
          "ident_start_date = #{identStartDate,jdbcType=DATE},",
          "ident_end_date = #{identEndDate,jdbcType=DATE},",
          "ident_pic = #{identPic,jdbcType=VARCHAR},",
          "backup_phone_1 = #{backupPhone1,jdbcType=VARCHAR},",
          "backup_phone_2 = #{backupPhone2,jdbcType=VARCHAR},",
          "emergency_contact_name = #{emergencyContactName,jdbcType=VARCHAR},",
          "emergency_contact_phone = #{emergencyContactPhone,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP},",
          "remark = #{remark,jdbcType=LONGVARCHAR}",
        "where customer_id = #{customerId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Customer record);

    @Update({
        "update t_customer",
        "set product_inst_id = #{productInstId,jdbcType=VARCHAR},",
          "customer_name = #{customerName,jdbcType=VARCHAR},",
          "identity_type = #{identityType,jdbcType=INTEGER},",
          "identity_no = #{identityNo,jdbcType=VARCHAR},",
          "phone = #{phone,jdbcType=VARCHAR},",
          "alias_name = #{aliasName,jdbcType=VARCHAR},",
          "email = #{email,jdbcType=VARCHAR},",
          "wechat = #{wechat,jdbcType=VARCHAR},",
          "lang = #{lang,jdbcType=VARCHAR},",
          "nation = #{nation,jdbcType=VARCHAR},",
          "gender = #{gender,jdbcType=VARCHAR},",
          "birth = #{birth,jdbcType=DATE},",
          "ident_start_date = #{identStartDate,jdbcType=DATE},",
          "ident_end_date = #{identEndDate,jdbcType=DATE},",
          "ident_pic = #{identPic,jdbcType=VARCHAR},",
          "backup_phone_1 = #{backupPhone1,jdbcType=VARCHAR},",
          "backup_phone_2 = #{backupPhone2,jdbcType=VARCHAR},",
          "emergency_contact_name = #{emergencyContactName,jdbcType=VARCHAR},",
          "emergency_contact_phone = #{emergencyContactPhone,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "modify_time = #{modifyTime,jdbcType=TIMESTAMP}",
        "where customer_id = #{customerId,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Customer record);
}