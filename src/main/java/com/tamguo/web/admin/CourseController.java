package com.tamguo.web.admin;

import com.github.pagehelper.Page;
import com.tamguo.model.CourseEntity;
import com.tamguo.model.SubjectEntity;
import com.tamguo.service.ICourseService;
import com.tamguo.service.ISubjectService;
import com.tamguo.util.ExceptionSupport;
import com.tamguo.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题库类型管理
 */
@Controller(value = "adminCourseController")
@RequestMapping("admin/course")
public class CourseController {
    @Autowired
    private ICourseService iCourseService;


    /**
     * 所有的学科
     */
    @RequestMapping("/all")
    @RequiresPermissions("tiku:course:all")
    public @ResponseBody
    Result list() {
        List<CourseEntity> list = iCourseService.findAllCourse();
        return Result.successResult(list);

    }

}
