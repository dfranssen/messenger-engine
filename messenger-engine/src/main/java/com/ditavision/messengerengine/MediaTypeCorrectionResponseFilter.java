/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ditavision.messengerengine;

import java.io.IOException;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dfranssen
 */
public class MediaTypeCorrectionResponseFilter implements ClientResponseFilter{

    @Override
    public void filter(ClientRequestContext requestContext, ClientResponseContext responseContext) throws IOException {
        responseContext.getHeaders().remove("Content-Type");
        responseContext.getHeaders().add("Content-Type", MediaType.APPLICATION_XML);
    }
    
}
