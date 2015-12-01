/*
 * Copyright 2015 Dirk Franssen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ditavision.messengerengine;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MediaType;

/**
 * Response filter to make sure that there is a header {@code 'Content-Type'='application/xml'}.
 * This is required for JAXB mapping. (Currently the Mobistar MMP server is returning {@code 'text/plain'} but has a xml payload)
 * @author Dirk Franssen
 */
public class MediaTypeCorrectionResponseFilter implements ClientResponseFilter{

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        responseContext.getHeaders().remove("Content-Type");
        responseContext.getHeaders().add("Content-Type", MediaType.APPLICATION_XML);
    }
}