package com.example.scanner;

import com.example.annotation.ApiDesc;
import com.example.client.SystemClient;
import com.example.dto.ApiInfoDTO;
import com.example.holder.UserContextHolder;
import com.example.utils.CollectionUtils;
import com.example.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiScanner {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final SystemClient systemClient;

    @PostConstruct
    public void scanAllApi() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        List<ApiInfoDTO> apiInfos = new ArrayList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {

            RequestMappingInfo requestMappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();

            PathPatternsRequestCondition pathPatternsCondition = requestMappingInfo.getPathPatternsCondition();
            if (pathPatternsCondition == null) {
                continue;
            }
            ApiInfoDTO apiInfo = new ApiInfoDTO();
            apiInfo.setApiUrl(pathPatternsCondition.getFirstPattern().getPatternString());

            Set<RequestMethod> methods = entry.getKey().getMethodsCondition().getMethods();

            if (CollectionUtils.isNotEmpty(methods)) {
                apiInfo.setApiMethod(methods.iterator().next().name());
            }

            ApiDesc classApiDesc = handlerMethod.getBeanType().getAnnotation(ApiDesc.class);
            if (classApiDesc == null) {
                continue;

            }
            apiInfo.setClassApiDesc(classApiDesc.value());
            ApiDesc interfaceApiDesc = handlerMethod.getMethodAnnotation(ApiDesc.class);
            if (interfaceApiDesc != null) {
                apiInfo.setInterfaceApiDesc(interfaceApiDesc.value());
            }
            String apiDesc = (apiInfo.getClassApiDesc() == null ? "" : apiInfo.getClassApiDesc()) + (apiInfo.getInterfaceApiDesc() == null ? "" : apiInfo.getInterfaceApiDesc());
            if (StringUtils.isNotBlank(apiDesc)) {
                apiInfo.setApiDesc(apiDesc);
            }
            apiInfos.add(apiInfo);
        }

        if (CollectionUtils.isEmpty(apiInfos)) {
            return;
        }
        try {
            UserContextHolder.buildAdminUser();
            systemClient.addList(apiInfos);
        } catch (Exception ignored) {
            log.warn("上报接口信息到系统服务失败，请检查系统服务是否运行");
        }
    }

}