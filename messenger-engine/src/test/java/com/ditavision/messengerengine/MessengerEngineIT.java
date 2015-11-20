/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ditavision.messengerengine;

import com.ditavision.messengerengine.MessengerEngine;
import com.ditavision.messengerengine.Configuration;
import com.ditavision.messengerengine.MessengerEngineException;
import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author dfranssen
 */
public class MessengerEngineIT {
    
    Configuration config = new Configuration();
    MessengerEngine cut;
    
    @Before
    public void init() {
        cut = new MessengerEngine();
    }

    @Test(expected = MessengerEngineException.class)
    public void testRegister() {
        cut.startRegistration(config.getUrl(), "123", "test@test123.com");
    }
    
    @Test(expected = MessengerEngineException.class)
    public void testVerification() {
        cut.verifyRegistration(config.getUrl(), "123", "ABCD");
    }
}
