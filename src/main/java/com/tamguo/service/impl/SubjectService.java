package com.tamguo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tamguo.dao.SubjectMapper;
import com.tamguo.model.SubjectEntity;
import com.tamguo.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

@Service
public class SubjectService implements ISubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public Page<SubjectEntity> queryList(Map<String, Object> map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<SubjectEntity> pageList = (Page<SubjectEntity>) subjectMapper.queryList(map);
        return pageList;
    }

    @Override
    public void save(SubjectEntity subjectEntity) {
        subjectMapper.insert(subjectEntity);
    }

    @Override
    public SubjectEntity queryObject(Long subjectId) {
        return subjectMapper.select(String.valueOf(subjectId));
    }

    @Override
    @Transactional
    public void update(SubjectEntity subject) {
        subjectMapper.update(subject);
    }

    @Override
    @Transactional
    public void deleteBatch(String[] subjectIds) {
        subjectMapper.deleteByIds(Arrays.asList(subjectIds));
    }
}
