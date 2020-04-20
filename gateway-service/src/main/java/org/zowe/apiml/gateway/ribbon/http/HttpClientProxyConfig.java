/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */

package org.zowe.apiml.gateway.ribbon.http;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.zuul.context.RequestContext;
import lombok.RequiredArgsConstructor;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zowe.apiml.gateway.security.service.AuthenticationService;
import org.zowe.apiml.gateway.security.service.ServiceAuthenticationServiceImpl;
import org.zowe.apiml.gateway.security.service.schema.AuthenticationCommand;
import org.zowe.apiml.security.common.auth.Authentication;

import java.util.ArrayList;
import java.util.List;

import static org.zowe.apiml.gateway.ribbon.ApimlZoneAwareLoadBalancer.LOADBALANCED_INSTANCE_INFO_KEY;
import static org.zowe.apiml.gateway.security.service.ServiceAuthenticationServiceImpl.AUTHENTICATION_COMMAND_KEY;

@RequiredArgsConstructor
@Configuration
public class HttpClientProxyConfig {

    private final HttpClientChooser clientChooser;
    private final ServiceAuthenticationServiceImpl serviceAuthenticationService;
    private final AuthenticationService authenticationService;

    @Bean
    @Qualifier("HttpClientProxy")
    public CloseableHttpClient httpClientProxy() {
        Enhancer e = new Enhancer();
        e.setSuperclass(CloseableHttpClient.class);
        e.setCallback(
            (MethodInterceptor) (o, method, objects, methodProxy) -> {
                if (method.getName().equals("execute")) {

                    //throw new HttpInterceptException("test");

                    if(objects.length>0 && objects[0] instanceof HttpRequest) {

                        RequestContext context = RequestContext.getCurrentContext();
                        InstanceInfo info = (InstanceInfo) context.get(LOADBALANCED_INSTANCE_INFO_KEY);
                        if (context.get(AUTHENTICATION_COMMAND_KEY) != null && context.get(AUTHENTICATION_COMMAND_KEY) instanceof AuthenticationCommand) {
                            // AuthenticationCommand cmd = (AuthenticationCommand) context.get(AUTHENTICATION_COMMAND_KEY);


                            Authentication authentication = serviceAuthenticationService.getAuthentication(info);

                            // this null when basic auth
                            String jwtToken = authenticationService.getJwtTokenFromRequest(context.getRequest()).orElse(null);
                            AuthenticationCommand cmd2 = serviceAuthenticationService.getAuthenticationCommand(authentication, jwtToken);

                            cmd2.smapply((HttpRequest)objects[0]);

                            System.out.println(cmd2);
                        }



                        // when this block throws, it triggers retry

                        HttpRequest request = (HttpRequest) objects[0];
                        List<Header> headers = new ArrayList<>();

                        for(int i=0; i < request.getAllHeaders().length; i++) {
                            headers.add(request.getAllHeaders()[i]);
                        }
                        // headers.add(new BasicHeader("X-DAVID-HEADER", "Jandalf"));



                        request.setHeaders(headers.toArray(new Header[] {}));
                    } else {
                        throw new RuntimeException("Unexpected error");
                        // TODO figure out how to stop retry
                    }
                }

                return method.invoke(clientChooser.chooseClient(), objects);
            }
        );
        return (CloseableHttpClient) e.create();
    }
}
