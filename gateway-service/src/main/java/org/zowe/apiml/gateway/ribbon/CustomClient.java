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

import com.netflix.appinfo.InstanceInfo;
import com.netflix.zuul.context.RequestContext;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class CustomClient extends CloseableHttpClient {

    private final CloseableHttpClient client;

    CustomClient(CloseableHttpClient client) {
        this.client = client;
    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context) throws IOException, ClientProtocolException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        Object instance = requestContext.get("zoweRoutedInstanceInfo");
        InstanceInfo info;
        if (instance instanceof InstanceInfo) {
            info = (InstanceInfo) instance;
        } else {
            throw new RuntimeException("Something is not right");
        }
        System.out.println(info);
        //custom logic based on contents of requestContext here:
        //TODO It can be done here, in case the service instances have mixed mode, the instance needs to be resolved against the instance infos
        return client.execute(target, request, context);
    }

    @Override
    public void close() throws IOException {
        client.close();
    }

    @Override
    public HttpParams getParams() {
        return client.getParams();
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return client.getConnectionManager();
    }
}
