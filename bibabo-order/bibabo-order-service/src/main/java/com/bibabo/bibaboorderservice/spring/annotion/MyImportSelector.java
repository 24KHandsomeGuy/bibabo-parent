package com.bibabo.bibaboorderservice.spring.annotion;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author: Damon Fu
 * @Date: 2022/3/26 19:23
 * @Description: 常见是通过@Import()注解或者@Configuratiron注解把该类注册到spring容器后执行批量导入bean功能
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[0];
    }
}
