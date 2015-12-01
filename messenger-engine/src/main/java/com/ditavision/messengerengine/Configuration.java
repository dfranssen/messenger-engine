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

/**
 * Utility class to get configuration settings by first searching for the 
 * defined keys as environment variables and if not found as system 
 * properties.
 * 
 * @author Dirk Franssen
 */
public class Configuration {
    public final static String URL_PROP_KEY = "com.ditavision.messenger.url";
    public final static String URL_ENV_KEY = "DTV_MESSENGER_URL";
    public final static String MSISDN_PROP_KEY = "com.ditavision.messenger.msisdn";
    public final static String MSISDN_ENV_KEY = "DTV_MESSENGER_MSISDN";
    public final static String PWD_PROP_KEY = "com.ditavision.messenger.password";
    public final static String PWD_ENV_KEY = "DTV_MESSENGER_PASSWORD";
    
    private final static String DEFAULT_MMP_URL = "https://mobistar.msgsend.com/mmp/cp3";
    private Logger LOGGER = Logger.getLogger(Configuration.class.getName());
    
    private final String url;
    private final String msisdn;
    private final String password;

    public Configuration() {
        this.url = getValueForKey(URL_ENV_KEY, URL_PROP_KEY, DEFAULT_MMP_URL);
        this.msisdn = getValueForKey(MSISDN_ENV_KEY, MSISDN_PROP_KEY, null);
        this.password = getValueForKey(PWD_ENV_KEY, PWD_PROP_KEY, null);
    }

    /**
     * The configured url of the MMP server. Configuration by either
     * <OL>
     *  <LI>environment variable {@value #URL_ENV_KEY}</LI>
     *  <LI>system property {@value #URL_PROP_KEY}</LI>
     * </OL>
     * @return the configured url or if not set the default {@value #DEFAULT_MMP_URL}
     */
    public String getUrl() {
        return url;
    }

    /**
     * The configured msisdn (phone number) of the registered user. Configuration by either
     * <OL>
     *  <LI>environment variable {@value #MSISDN_ENV_KEY}</LI>
     *  <LI>system property {@value #MSISDN_PROP_KEY}</LI>
     * </OL>
     * @return the configured msisdn or null if not set
     */
    public String getMsisdn() {
        return msisdn;
    }

    /**
     * The configured password of the registered user. Configuration by either
     * <OL>
     *  <LI>environment variable {@value #PWD_ENV_KEY}</LI>
     *  <LI>system property {@value #PWD_PROP_KEY}</LI>
     * </OL>
     * @return the configured password or null if not set
     */
    public String getPassword() {
        return password;
    }
    
    protected String getEnvironmentValue(String key) {
        String result = null;
        try {
            result = System.getenv(key);
        } catch(SecurityException ex) {
            LOGGER.log(Level.WARNING, "Problem accessing environment variable '{0}', details: {1}", new Object[] {key, ex.getMessage()});
        }
        return result;
    }
    
    protected String getSystemValue(String key) {
        String result = null;
        try {
            result = System.getProperty(key);
        } catch(SecurityException ex) {
            LOGGER.log(Level.WARNING, "Problem accessing system property '{0}', details: {1}", new Object[] {key, ex.getMessage()});
        }
        return result;
    }
    
    private String getValueForKey(String envKey, String propKey, String defaultValue) {
        String result = getEnvironmentValue(envKey);
        result = result != null ? result : getSystemValue(propKey);
        result = result != null ? result : defaultValue;
        return result;
    }
}