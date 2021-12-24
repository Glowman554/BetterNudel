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
package gq.glowman554.modules.impl.ddos;

import gq.glowman554.modules.impl.ddos.protocols.implementations.TCPDDos;
import gq.glowman554.modules.impl.ddos.protocols.implementations.UDPDDos;
import gq.glowman554.modules.impl.ddos.protocols.interfaces.DDOS;

/**
 * This is just a simple factory class for creating
 * the corresponding DDOS subclass.
 * 
 * @author Tobias Schmidradler
 * 
 * Last changed: 13.05.2015
 */
public class DdosFactory {
    
    /**
     * Factory method which is responsible for 
     * creating the corresponding DDOS subclass.
     * @param ddosPattern
     * @return a subclass of DDOS
     */
    public static DDOS createDDOS(DDOSPattern ddosPattern) {
        switch(ddosPattern.getProtocol().toLowerCase()) {
            case "tcp":
                return new TCPDDos(ddosPattern);
            case "udp":
                return new UDPDDos(ddosPattern);
        }
        return new TCPDDos(ddosPattern);
    }
}
