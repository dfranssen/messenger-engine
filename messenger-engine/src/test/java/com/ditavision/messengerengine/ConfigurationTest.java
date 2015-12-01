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

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author Dirk Franssen
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Configuration.class, Logger.class})
public class ConfigurationTest {

    @Test
    public void testGetEnvironmentValueSecurityException() {
        PowerMockito.mockStatic(System.class);
        when(System.getenv(eq(Configuration.PWD_ENV_KEY))).thenThrow( new SecurityException("test"));
        
        Logger mockLogger = mock(Logger.class);
        PowerMockito.mockStatic(Logger.class);
        when(Logger.getLogger(anyString())).thenReturn(mockLogger);
        Configuration cut = new Configuration();
        assertThat(cut.getPassword(), nullValue());
        ArgumentCaptor<Object[]> argument = ArgumentCaptor.forClass(Object[].class);
        verify(mockLogger).log(eq(Level.WARNING), anyString(), argument.capture());
        assertThat(argument.getValue()[0], is(Configuration.PWD_ENV_KEY));
        assertThat(argument.getValue()[1], is("test"));
    }
    
    @Test
    public void testGetSystemValueSecurityException() {
        PowerMockito.mockStatic(System.class);
        when(System.getenv(eq(Configuration.PWD_ENV_KEY))).thenReturn(null);
        when(System.getProperty(eq(Configuration.PWD_PROP_KEY))).thenThrow( new SecurityException("test"));
        
        Logger mockLogger = mock(Logger.class);
        PowerMockito.mockStatic(Logger.class);
        when(Logger.getLogger(anyString())).thenReturn(mockLogger);
        Configuration cut = new Configuration();
        assertThat(cut.getPassword(), nullValue());
        ArgumentCaptor<Object[]> argument = ArgumentCaptor.forClass(Object[].class);
        verify(mockLogger).log(eq(Level.WARNING), anyString(), argument.capture());
        assertThat(argument.getValue()[0], is(Configuration.PWD_PROP_KEY));
        assertThat(argument.getValue()[1], is("test"));
    }
    
    @Test
    public void testGettersWithNothingSet() {
        PowerMockito.mockStatic(System.class);
        when(System.getenv(anyString())).thenReturn(null);
        when(System.getProperty(anyString())).thenReturn(null);
        
        Configuration cut = new Configuration();
        assertThat(cut.getUrl(), startsWith("http"));
        assertThat(cut.getMsisdn(), nullValue());
        assertThat(cut.getPassword(), nullValue());
    }
    
    @Test
    public void testGettersWithEnvSet() {
        PowerMockito.mockStatic(System.class);
        when(System.getenv(eq(Configuration.URL_ENV_KEY))).thenReturn("url-env");
        when(System.getenv(eq(Configuration.MSISDN_ENV_KEY))).thenReturn("msisdn-env");
        when(System.getenv(eq(Configuration.PWD_ENV_KEY))).thenReturn("pwd-env");
        when(System.getProperty(anyString())).thenReturn(null);
        
        Configuration cut = new Configuration();
        assertThat(cut.getUrl(), is("url-env"));
        assertThat(cut.getMsisdn(), is("msisdn-env"));
        assertThat(cut.getPassword(), is("pwd-env"));
    }
    
    @Test
    public void testGettersWithPropsSet() {
        PowerMockito.mockStatic(System.class);
        when(System.getProperty(eq(Configuration.URL_PROP_KEY))).thenReturn("url-prop");
        when(System.getProperty(eq(Configuration.MSISDN_PROP_KEY))).thenReturn("msisdn-prop");
        when(System.getProperty(eq(Configuration.PWD_PROP_KEY))).thenReturn("pwd-prop");
        when(System.getenv(anyString())).thenReturn(null);
        
        Configuration cut = new Configuration();
        assertThat(cut.getUrl(), is("url-prop"));
        assertThat(cut.getMsisdn(), is("msisdn-prop"));
        assertThat(cut.getPassword(), is("pwd-prop"));
    }
}