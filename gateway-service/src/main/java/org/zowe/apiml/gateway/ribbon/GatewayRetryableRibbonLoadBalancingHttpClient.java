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

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.netflix.ribbon.ServerIntrospector;
import org.springframework.cloud.netflix.ribbon.apache.RetryableRibbonLoadBalancingHttpClient;

import java.net.URI;
import java.net.URISyntaxException;

public class GatewayRetryableRibbonLoadBalancingHttpClient extends RetryableRibbonLoadBalancingHttpClient {

    public GatewayRetryableRibbonLoadBalancingHttpClient(CloseableHttpClient delegate, IClientConfig config, ServerIntrospector serverIntrospector, LoadBalancedRetryFactory loadBalancedRetryFactory) {
        super(delegate, config, serverIntrospector, loadBalancedRetryFactory);
    }

    @Override
    public URI reconstructURIWithServer(Server server, URI original) {
        URI fromSuper =  super.reconstructURIWithServer(server, original);

        try {
            URI newUri = new URI("https", null, fromSuper.getHost(), fromSuper.getPort(), fromSuper.getPath(), null, null);
            return newUri;
        } catch (URISyntaxException e) {
            //TODO Cringe
            e.printStackTrace();
            return fromSuper;
        }

    }
}
