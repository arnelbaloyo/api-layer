/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package com.ca.mfaas.product.registry;

import com.netflix.discovery.EurekaClient;

//@Component
public class EurekaClientWrapper {
    private EurekaClient eurekaClient;

    public EurekaClientWrapper() {
    }

    public EurekaClientWrapper(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public EurekaClient getEurekaClient() {
        return eurekaClient;
    }

    public void setEurekaClient(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }
}
