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
    private Long uid;

    @Column(name = "name")
    private String name;

    @Column(name = "course_id")
    private Long courseId;


    public SubjectEntity() {
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}