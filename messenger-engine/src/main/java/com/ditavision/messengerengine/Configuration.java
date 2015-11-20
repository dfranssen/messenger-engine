/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ditavision.messengerengine;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dfranssen
 */
public class Configuration {
    public final static String URL_PROP_KEY = "com.ditavision.messenger.url";
    public final static String URL_ENV_KEY = "DTV_MESSENGER_URL";
    public final static String MSISDN_PROP_KEY = "com.ditavision.messenger.msisdn";
    public final static String MSISDN_ENV_KEY = "DTV_MESSENGER_MSISDN";
    public final static String PWD_PROP_KEY = "com.ditavision.messenger.password";
    public final static String PWD_ENV_KEY = "DTV_MESSENGER_PASSWORD";
    
    private final static String DEFAULT_MMP_URL = "https://mobistar.msgsend.com/mmp/cp3";
    private final static Logger LOGGER = Logger.getLogger(Configuration.class.getName());
    
    private final String url;
    private final String msisdn;
    private final String password;

    public Configuration() {
        this.url = getValueForKey(URL_ENV_KEY, URL_PROP_KEY, DEFAULT_MMP_URL);
        this.msisdn = getValueForKey(MSISDN_ENV_KEY, MSISDN_PROP_KEY, null);
        this.password = getValueForKey(PWD_ENV_KEY, PWD_PROP_KEY, null);
    }

    public String getUrl() {
        return url;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public String getPassword() {
        return password;
    }
    
    protected String getEnvironmentValue(String key) {
        String result = null;
        try {
            result = System.getenv(key);
        } catch(SecurityException|NullPointerException ex) {
            LOGGER.log(Level.WARNING, "Problem accessing environment variable '{0}', details: {1}", new Object[] {key, ex.getMessage()});
        }
        return result;
    }
    
    protected String getSystemValue(String key) {
        String result = null;
        try {
            result = System.getProperty(key);
        } catch(SecurityException|NullPointerException ex) {
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
