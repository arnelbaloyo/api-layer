/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package com.ca.apiml.security.client.config;

import com.ca.mfaas.product.gateway.GatewayClient;
import com.ca.mfaas.product.gateway.GatewayInstanceInitializer;
import com.ca.mfaas.product.instance.lookup.InstanceLookupExecutor;
import com.ca.mfaas.product.registry.EurekaClientWrapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * General configuration of security client
 */
@Configuration
@ComponentScan({"com.ca.apiml.security", "com.ca.mfaas.product"}) /*.gateway*/
public class SecurityServiceConfiguration {

    @Bean
    public GatewayInstanceInitializer gatewayInstanceInitializer(
        EurekaClientWrapper eurekaClientWrapper,
        ApplicationEventPublisher applicationEventPublisher,
        GatewayClient gatewayClient) {


        return new GatewayInstanceInitializer(
            new InstanceLookupExecutor(eurekaClientWrapper),
            applicationEventPublisher,
            gatewayClient);
    }
}
