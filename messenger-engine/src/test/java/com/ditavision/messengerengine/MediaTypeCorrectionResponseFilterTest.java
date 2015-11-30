/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ditavision.messengerengine;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author dfranssen
 */
public class MediaTypeCorrectionResponseFilterTest {

    MediaTypeCorrectionResponseFilter cut;
    
    @Before
    public void init() {
        cut = new MediaTypeCorrectionResponseFilter();
    } 

    @Test
    public void filterEmpty() throws IOException {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        ClientResponseContext context = new MyContext(headers);
        cut.filter(null, context);
        assertThat(headers.getFirst("Content-Type"), is(MediaType.APPLICATION_XML));
    }  

    @Test
    public void filterOverride() throws IOException {
        MultivaluedMap<String, String> headers = new MultivaluedHashMap<>();
        headers.putSingle("Content-Type", MediaType.TEXT_PLAIN);
        ClientResponseContext context = new MyContext(headers);
        cut.filter(null, context);
        assertThat(headers.get("Content-Type").size(), is(1));
        assertThat(headers.getFirst("Content-Type"), is(MediaType.APPLICATION_XML));
    }  

    private static class MyContext implements ClientResponseContext {
        private final MultivaluedMap<String, String> headers;

        public MyContext(MultivaluedMap<String, String> headers) {
            this.headers = headers;
        }

        @Override
        public int getStatus() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setStatus(int code) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Response.StatusType getStatusInfo() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setStatusInfo(Response.StatusType statusInfo) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public MultivaluedMap<String, String> getHeaders() {
            return headers;
        }

        @Override
        public String getHeaderString(String name) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Set<String> getAllowedMethods() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Date getDate() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Locale getLanguage() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public int getLength() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public MediaType getMediaType() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Map<String, NewCookie> getCookies() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public EntityTag getEntityTag() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Date getLastModified() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public URI getLocation() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Set<Link> getLinks() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean hasLink(String relation) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Link getLink(String relation) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public Link.Builder getLinkBuilder(String relation) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public boolean hasEntity() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public InputStream getEntityStream() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public void setEntityStream(InputStream input) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}