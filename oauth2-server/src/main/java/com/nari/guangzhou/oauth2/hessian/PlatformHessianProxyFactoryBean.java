package com.nari.guangzhou.oauth2.hessian;

import com.caucho.hessian.HessianException;
import com.caucho.hessian.client.HessianConnectionException;
import com.caucho.hessian.client.HessianProxyFactory;
import com.caucho.hessian.client.HessianRuntimeException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.remoting.RemoteLookupFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 重写HessianProxyFactoryBean，实现serviceUrl的动态读取
 *
 * @author Zongwei
 * @date 2019/9/29 10:54
 */
@Slf4j
public class PlatformHessianProxyFactoryBean extends HessianProxyFactoryBean {

    private HessianProxyFactory proxyFactory = new HessianProxyFactory();

    private static Map<String, Object> hessianProxyMap = new HashMap<>();

    @Setter
    private String serviceName;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object hessianProxy;
        String serviceUrl = null;
        try {
            String serviceInterfaceName = invocation.getMethod().getDeclaringClass().getName();
            serviceUrl = this.getServiceUrl(serviceInterfaceName);
            if (Objects.isNull(hessianProxyMap.get(serviceUrl))) {
                hessianProxy = this.proxyFactory.create(getServiceInterface(), serviceUrl, getBeanClassLoader());
                hessianProxyMap.put(serviceUrl, hessianProxy);
            } else {
                hessianProxy = hessianProxyMap.get(serviceUrl);
            }
        } catch (MalformedURLException e) {
            throw new RemoteLookupFailureException("Service URL [" + serviceUrl + "] is invalid", e);
        }

        if (Objects.isNull(hessianProxy)) {
            throw new IllegalStateException("HessianProxy init fail, service url : " + serviceUrl);
        }

        ClassLoader originalClassLoader = this.overrideThreadContextClassLoader();

        Object invokeResult;
        try {
            invokeResult = invocation.getMethod().invoke(hessianProxy, invocation.getArguments());
        } catch (InvocationTargetException e) {
            Throwable targetEx = e.getTargetException();
            if (targetEx instanceof InvocationTargetException) {
                targetEx = ((InvocationTargetException) targetEx).getTargetException();
            }

            if (targetEx instanceof HessianConnectionException) {
                throw this.convertHessianAccessException(targetEx);
            }

            if (!(targetEx instanceof HessianException) && !(targetEx instanceof HessianRuntimeException)) {
                if (targetEx instanceof UndeclaredThrowableException) {
                    UndeclaredThrowableException utex = (UndeclaredThrowableException) targetEx;
                    throw this.convertHessianAccessException(utex.getUndeclaredThrowable());
                }

                throw targetEx;
            }

            Throwable cause = targetEx.getCause();
            throw this.convertHessianAccessException(cause != null ? cause : targetEx);
        } catch (Throwable e) {
            throw new RemoteProxyFailureException("Failed to invoke Hessian proxy for remote service", e);
        } finally {
            this.resetThreadContextClassLoader(originalClassLoader);
        }

        return invokeResult;
    }

    private String getServiceUrl(String serviceInterfaceName) {
        ServiceInstance serviceInstance = loadBalancerClient.choose(this.serviceName);
        if (serviceInstance == null) {
            throw new IllegalArgumentException("No valid serviceUrl find from RegServer by serviceName: " + serviceName);
        }

        return serviceInstance.getMetadata().get("hessian") + serviceInterfaceName;
    }
}
