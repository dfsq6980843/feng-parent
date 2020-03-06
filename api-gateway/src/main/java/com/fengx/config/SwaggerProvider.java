package com.fengx.config;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.List;

/**
 * @author fengXiong
 * @date 2019/12/5 10:33 上午
 */
@Component
@Primary
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

    /**
     * swagger2默认的url后缀
     */
    public static final String API_URI = "/v2/api-docs";

    /**
     * 网关路由
     */
    private final RouteLocator routeLocator;

    /**
     * 网关应用名称
     */
    private final GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> swaggerResources = Lists.newArrayList();
        List<String> routes = Lists.newArrayList();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        gatewayProperties.getRoutes()
                .stream()
                .filter(routeDefinition -> routes.contains(routeDefinition.getId()))
                .forEach(routeDefinition -> routeDefinition.getPredicates()
                        .stream()
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        .forEach(predicateDefinition -> swaggerResources.add(swaggerResource(routeDefinition.getId(),
                                predicateDefinition.getArgs()
                                        .get(NameUtils.GENERATED_NAME_PREFIX + "0")
                                        .replace("/**", API_URI)))));
        return swaggerResources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("1.0");
        return swaggerResource;
    }

}
