package com.tamguo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the tiku_chapter database table.
 */
@Entity
@Table(name = "tiku_subject")
@NamedQuery(name = "SubjectEntity.findAll", query = "SELECT c FROM SubjectEntity c")
public class SubjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Integer uid;

    @Column(name = "name")
    private String name;

    @Column(name = "course_id")
    private String courseId;


    public SubjectEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}