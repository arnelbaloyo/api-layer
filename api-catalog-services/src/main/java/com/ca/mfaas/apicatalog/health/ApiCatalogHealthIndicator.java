/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package com.ca.mfaas.apicatalog.health;

import com.ca.mfaas.product.registry.EurekaClientWrapper;
import com.ca.mfaas.product.constants.CoreService;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

/**
 * Api Catalog health information (/application/health)
 */
@Component
@RequiredArgsConstructor
public class ApiCatalogHealthIndicator extends AbstractHealthIndicator {

    private final EurekaClientWrapper eurekaClientWrapper;

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        String gatewayServiceId = CoreService.GATEWAY.getServiceId();

        EurekaClient eurekaClient = getEurekaClient();
        boolean gatewayUp = (eurekaClient == null) ? false : !eurekaClient.getInstancesById(gatewayServiceId).isEmpty();
        Status healthStatus = gatewayUp ? Status.UP : Status.DOWN;

        builder
            .status(healthStatus)
            .withDetail(gatewayServiceId, healthStatus.getCode());
    }

    private EurekaClient getEurekaClient() {
        return eurekaClientWrapper.getEurekaClient();
    }
}
