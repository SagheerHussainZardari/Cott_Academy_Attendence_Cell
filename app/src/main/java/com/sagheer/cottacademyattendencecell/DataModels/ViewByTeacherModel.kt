package com.sagheer.cottacademyattendencecell.DataModels

data class ViewByTeacherModel(
    var stdName: String,
    var stdPresent: String,
    var stdAbsent: String,
    var stdTotalClasses: String,
    var stdPercentage: String
)