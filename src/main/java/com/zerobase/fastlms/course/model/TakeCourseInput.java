package com.zerobase.fastlms.course.model;

import lombok.Data;

@Data
public class TakeCourseInput {
    String userId;
    long courseId;

    long takeCourseId; // take-course-id
}
