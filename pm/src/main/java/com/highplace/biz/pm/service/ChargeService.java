package com.highplace.biz.pm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.highplace.biz.pm.dao.charge.BillMapper;
import com.highplace.biz.pm.dao.charge.BillSubjectRelMapper;
import com.highplace.biz.pm.dao.charge.SubjectMapper;
import com.highplace.biz.pm.domain.charge.*;
import com.highplace.biz.pm.domain.ui.PageBean;
import com.highplace.biz.pm.service.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.orderbyhelper.OrderByHelper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChargeService {

    public static final Logger logger = LoggerFactory.getLogger(ChargeService.class);

    //写入redis的key前缀, 后面加上productInstId
    public static final String PREFIX_BILL_NAME_KEY = "CHARGE_BILL_NAME_KEY_";

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private BillSubjectRelMapper billSubjectRelMapper;

    //查询收费科目列表
    public Map<String, Object> querySubject(String productInstId, PageBean pageBean, boolean noPageSortFlag) {

        SubjectExample example = new SubjectExample();
        SubjectExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //如果noPageSortFlag 不为true
        if (!noPageSortFlag) {
            //设置分页参数
            if (pageBean.getPageNum() != null && pageBean.getPageSize() != null)
                PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());

            //设置排序字段,注意前端传入的是驼峰风格字段名,需要转换成数据库下划线风格字段名
            if (pageBean.getSortField() != null) {
                if (pageBean.getSortType() == null) {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(pageBean.getSortField()) + " asc"); //默认升序
                } else {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(pageBean.getSortField()) + " " + pageBean.getSortType());
                }
            }
        }

        //查询结果
        List<Subject> subjectList = subjectMapper.selectByExampleWithBLOBs(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && pageBean.getPageNum() != null && pageBean.getPageSize() != null) {
            totalCount = ((Page) subjectList).getTotal();
        } else {
            totalCount = subjectList.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", subjectList);
        return result;
    }

    //插入收费科目
    public int insertSubject(String productInstId, Subject subject) {

        //设置产品实例ID
        subject.setProductInstId(productInstId);
        int num = subjectMapper.insertSelective(subject);
        return num;
    }

    //删除收费科目
    public int deleteSubject(String productInstId, Long subjectId) {

        SubjectExample example = new SubjectExample();
        SubjectExample.Criteria criteria = example.createCriteria();
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andSubjectIdEqualTo(subjectId);
        return subjectMapper.deleteByExample(example);
    }

    //修改收费科目
    public int updateSubject(String productInstId, Subject subject) {

        SubjectExample example = new SubjectExample();
        SubjectExample.Criteria criteria = example.createCriteria();
        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);
        criteria.andSubjectIdEqualTo(subject.getSubjectId());
        int num = subjectMapper.updateByExampleSelective(subject, example);
        return num;
    }

    //查询账单类型列表
    public Map<String, Object> queryBillType(String productInstId, PageBean pageBean, boolean noPageSortFlag) {

        BillExample example = new BillExample();
        BillExample.Criteria criteria = example.createCriteria();

        //产品实例ID，必须填入
        criteria.andProductInstIdEqualTo(productInstId);

        //如果noPageSortFlag 不为true
        if (!noPageSortFlag) {
            //设置分页参数
            if (pageBean.getPageNum() != null && pageBean.getPageSize() != null)
                PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());

            //设置排序字段,注意前端传入的是驼峰风格字段名,需要转换成数据库下划线风格字段名
            if (pageBean.getSortField() != null) {
                if (pageBean.getSortType() == null) {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(pageBean.getSortField()) + " asc"); //默认升序
                } else {
                    OrderByHelper.orderBy(CommonUtils.underscoreString(pageBean.getSortField()) + " " + pageBean.getSortType());
                }
            }
        }

        //查询结果
        List<Bill> billList = billMapper.selectByExampleWithBLOBsWithRelation(example);

        //总记录数
        long totalCount;

        //判断是否有分页
        if (!noPageSortFlag && pageBean.getPageNum() != null && pageBean.getPageSize() != null) {
            totalCount = ((Page) billList).getTotal();
        } else {
            totalCount = billList.size();
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalCount", totalCount);
        result.put("data", billList);
        return result;
    }

    //插入账单类型信息
    //多表插入，需要增加事务
    @Transactional
    public int insertBillType(String productInstId, Bill bill) {

        //设置产品实例ID
        bill.setProductInstId(productInstId);
        int num = billMapper.insertSelective(bill);
        if (num == 1) {
            //批量插入账单类型和收费科目关系信息
            List<BillSubjectRel> billSubjectRelList = bill.getBillSubjectRelList();
            if (billSubjectRelList != null) {
                for (BillSubjectRel billSubjectRel : billSubjectRelList) {

                    billSubjectRel.setProductInstId(productInstId);
                    billSubjectRel.setBillId(bill.getBillId());
                    billSubjectRelMapper.insertSelective(billSubjectRel);
                }
            }
        }
        return num;
    }
}
