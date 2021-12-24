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
package gq.glowman554.modules.impl.ddos.protocols.interfaces;

import gq.glowman554.modules.impl.ddos.DDOSPattern;

import java.net.SocketAddress;

/**
 * Abstract super class of all different protocols.
 * Every protocol implementation must inherit from this abstract class.
 * 
 * @author Tobias Schmidradler
 * 
 * Last changed: 13.05.2015
 */
public abstract class DDOS implements Runnable {
    private DDOSPattern ddosPattern;
    private SocketAddress address;
    
    public DDOS() {
        
    }
    
    public DDOS(DDOSPattern ddosPattern) {
        this.ddosPattern = ddosPattern;
    }

    /**
     * This method writes something (Protocol dependent) to the socket.
     * @param message 
     */
    public abstract void writeLineToSocket(String message);
    
    /**
     * Creates the socket. (Protocol dependent)
     */
    protected abstract void createSocket();
    
    /**
     * Connect to the socket. (Protocol dependent)
     */
    protected abstract void connectToSocket();
    
    /**
     * Close the socket (Protocol dependent)
     */
    protected abstract void closeSocket();

    /**
     * Get the DDOS pattern.
     * @return the pattern of the current DDOS
     */
    public DDOSPattern getDdosPattern() {
        return ddosPattern;
    }

    /**
     * Set the DDOS pattern.
     * @param ddosPattern pattern of the current DDOS
     */
    public void setDdosPattern(DDOSPattern ddosPattern) {
        this.ddosPattern = ddosPattern;
    }

    /**
     * Get the address of the victim
     * @return 
     */
    public SocketAddress getAddress() {
        return address;
    }

    /**
     * Set the address of the victim
     * @param address 
     */
    public void setAddress(SocketAddress address) {
        this.address = address;
    }
}
