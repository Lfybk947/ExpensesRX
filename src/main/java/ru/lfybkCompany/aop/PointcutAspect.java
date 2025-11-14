package ru.lfybkCompany.aop;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
public class PointcutAspect {

    @Pointcut("within(ru.lfybkCompany.service.entityService.*Service)")
    public void isServiceLayer() {}

    @Pointcut("within(ru.lfybkCompany.service.fileService.File*)")
    public void isUploadFileServiceLayer() {}

    @Pointcut("within(ru.lfybkCompany.service.fileService.*.*Impl)")
    public void isUploadExpensesServiceLayer() {}

    @Pointcut("within(ru.lfybkCompany.mapper.*Mapper)")
    public void isMapperLayer() {}

    @Pointcut("within(ru.lfybkCompany.mapper.*.*Mapper)")
    public void isFileMapperLayer() {}

    @Pointcut("within(ru.lfybkCompany.http.*.*Controller)")
    public void isControllerLayer() {}



}
