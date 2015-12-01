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

import org.junit.Test;
import org.junit.Before;

/**
 *
 * @author Dirk Franssen
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
