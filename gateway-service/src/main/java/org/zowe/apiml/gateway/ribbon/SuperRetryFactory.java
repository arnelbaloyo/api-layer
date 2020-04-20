/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */

package org.zowe.apiml.gateway.ribbon;

import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryPolicy;
import org.springframework.cloud.client.loadbalancer.ServiceInstanceChooser;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.BackOffPolicy;
import org.zowe.apiml.gateway.ribbon.http.HttpInterceptException;

public class SuperRetryFactory extends RibbonLoadBalancedRetryFactory {

    public SuperRetryFactory(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public RetryListener[] createRetryListeners(String service) {

        RetryListener retryBreakingListener = new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {

            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                if (throwable instanceof HttpInterceptException) {
                    throw new RuntimeException("dam gurl");
                }
            }
        };


        return new RetryListener[] {retryBreakingListener};
    }

    @Override
    public BackOffPolicy createBackOffPolicy(String service) {
        return super.createBackOffPolicy(service);
    }

    @Override
    public LoadBalancedRetryPolicy createRetryPolicy(String service, ServiceInstanceChooser serviceInstanceChooser) {
        return super.createRetryPolicy(service, serviceInstanceChooser);
    }
}
