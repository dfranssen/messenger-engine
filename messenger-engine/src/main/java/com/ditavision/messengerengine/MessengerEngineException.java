/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ditavision.messengerengine;

/**
 *
 * @author Dirk Franssen
 */
public class MessengerEngineException extends RuntimeException {
    private final String code;

    public MessengerEngineException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}