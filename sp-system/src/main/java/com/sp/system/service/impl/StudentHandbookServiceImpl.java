package com.sp.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sp.system.mapper.StudentHandbookMapper;
import com.sp.system.entity.StudentHandbook;
import com.sp.system.service.IStudentHandbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学生手册Service业务层处理
 *
 */
@Service
public class StudentHandbookServiceImpl extends ServiceImpl<StudentHandbookMapper, StudentHandbook> implements IStudentHandbookService {

    @Autowired
    private StudentHandbookMapper studentHandbookMapper;

    /**
     * 查询学生手册列表
     *
     * @param studentHandbook 学生手册信息
     * @return 学生手册集合
     */
    @Override
    public List<StudentHandbook> selectStudentHandbookList(StudentHandbook studentHandbook) {
        return studentHandbookMapper.selectStudentHandbookList(studentHandbook);
    }

    /**
     * 查询学生手册详细信息
     *
     * @param id 学生手册主键
     * @return 学生手册信息
     */
    @Override
    public StudentHandbook selectStudentHandbookById(Long id) {
        return studentHandbookMapper.selectById(id);
    }

    /**
     * 新增学生手册
     *
     * @param studentHandbook 学生手册信息
     * @return 结果
     */
    @Override
    public int insertStudentHandbook(StudentHandbook studentHandbook) {
        return studentHandbookMapper.insert(studentHandbook);
    }

    /**
     * 修改学生手册
     *
     * @param studentHandbook 学生手册信息
     * @return 结果
     */
    @Override
    public int updateStudentHandbook(StudentHandbook studentHandbook) {
        return studentHandbookMapper.updateById(studentHandbook);
    }

    /**
     * 删除学生手册
     *
     * @param id 学生手册主键
     * @return 结果
     */
    @Override
    public int deleteStudentHandbookById(Long id) {
        return studentHandbookMapper.deleteById(id);
    }

    /**
     * 批量删除学生手册
     *
     * @param ids 需要删除的学生手册主键集合
     * @return 结果
     */
    @Override
    public int deleteStudentHandbookByIds(List<Long> ids) {
        return studentHandbookMapper.deleteBatchIds(ids);
    }
}