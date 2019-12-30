/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package com.ca.apiml.enable.register;

import com.ca.apiml.enable.EnableApiDiscovery;
import com.ca.apiml.enable.config.ApiMediationServiceConfigBean;
import com.ca.apiml.enable.config.SslConfigBean;
import com.ca.mfaas.eurekaservice.client.ApiMediationClient;
import com.ca.mfaas.exception.ServiceDefinitionException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
@TestPropertySource(locations = "/application.yml")
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@EnableApiDiscovery
public class ApimlEnabledRegisterToApiLayerTest {

    private RegisterToApiLayer registerToApiLayer;

    @Autowired
    private ApiMediationServiceConfigBean apiMediationServiceConfigBean;

    @Autowired
    private SslConfigBean sslConfigBean;

    @MockBean
    private ApiMediationClient apiMediationClient;

    @Before
    public void setup() {
        registerToApiLayer = new RegisterToApiLayer(
            apiMediationServiceConfigBean, sslConfigBean, apiMediationClient, null);
    }


    @Test
    public void testOnContextRefreshedEventEvent() throws ServiceDefinitionException {
        registerToApiLayer.onContextRefreshedEventEvent();

        assertNotNull("ApiMediationServiceConfigBean is null", apiMediationServiceConfigBean);
        assertEquals("Service id is not equal", "discoverableclient2", apiMediationServiceConfigBean.getServiceId());

        assertNotNull("SslConfigBean is null", sslConfigBean);
        assertEquals("keystore is not equal", "keystore", sslConfigBean.getKeyStore());
        assertEquals("truststore id is not equal", "truststore", sslConfigBean.getTrustStore());

        verify(apiMediationClient).register(apiMediationServiceConfigBean);
    }
}
