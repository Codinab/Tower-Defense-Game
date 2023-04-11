package com.example.towerdefense.utility


@Target(
    AnnotationTarget.EXPRESSION, AnnotationTarget.PROPERTY, AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION, AnnotationTarget.LOCAL_VARIABLE
)
@Retention(AnnotationRetention.SOURCE)
annotation class Temporary
