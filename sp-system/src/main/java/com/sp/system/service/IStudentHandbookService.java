package com.sp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sp.system.entity.StudentHandbook;

import java.util.List;

/**
 * 学生手册Service接口
 *
 */
public interface IStudentHandbookService extends IService<StudentHandbook> {
    /**
     * 查询学生手册列表
     *
     * @param studentHandbook 学生手册信息
     * @return 学生手册集合
     */
    List<StudentHandbook> selectStudentHandbookList(StudentHandbook studentHandbook);

    /**
     * 查询学生手册详细信息
     *
     * @param id 学生手册主键
     * @return 学生手册信息
     */
    StudentHandbook selectStudentHandbookById(Long id);

    /**
     * 新增学生手册
     *
     * @param studentHandbook 学生手册信息
     * @return 结果
     */
    int insertStudentHandbook(StudentHandbook studentHandbook);

    /**
     * 修改学生手册
     *
     * @param studentHandbook 学生手册信息
     * @return 结果
     */
    int updateStudentHandbook(StudentHandbook studentHandbook);

    /**
     * 删除学生手册
     *
     * @param id 学生手册主键
     * @return 结果
     */
    int deleteStudentHandbookById(Long id);

    /**
     * 批量删除学生手册
     *
     * @param ids 需要删除的学生手册主键集合
     * @return 结果
     */
    int deleteStudentHandbookByIds(List<Long> ids);
}