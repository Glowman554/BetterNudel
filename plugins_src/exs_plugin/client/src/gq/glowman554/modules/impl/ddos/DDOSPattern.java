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

import net.shadew.json.JsonNode;

import java.io.Serializable;

/**
 * This class is basically just the pattern for the whole DDOS process.
 * The protocol, the host, the port etc. is all saved here.
 * It´s basically a logical illustration of the GUI, or the model for the GUI.
 * 
 * @author Tobias Schmidradler
 * 
 * Last changed: 13.05.2015
 */
public class DDOSPattern implements Serializable {
    
    /**
     * The name of the protocol (tcp, udp etc.)
     */
    private String protocol;
    
    /**
     * The target host.
     * This host is the victim.
     */
    private String host;
    
    /**
     * Target port.
     */
    private int port;
    
    /**
     * The number of generated attackers.
     */
    private int threads;
    
    /**
     * The message to send to the victim.
     * Over the specified protocol.
     */
    private String message;
    
    /**
     * The duration in hours.
     * Must be greater equal 0.
     */
    private int hours;
    
    /**
     * The duration in minutes.
     * Must be greater equal 0.
     */
    private int minutes;
    
    /**
     * The duration in seconds.
     * Must be greater equal 0.
     */
    private int seconds;
    
    /**
     * The timeout between an attack.
     * This attribute specifies the
     * break between every attack in milliseconds.
     */
    private int timeout;
    
    /**
     * The socket timeout.
     * The timeout how long a socket should wait 
     * until the connection aborts in milliseconds.
     */
    private int socketTimeout;
    
    /**
     * Standard constructor.
     * This creates a default DDOS pattern.
     */
    public DDOSPattern() {
        this("TCP", "0.0.0.0", 0, 1, "Hello", 0, 5, 0, 5000, 5000);
    }
    
    /**
     * Constructor.
     * This creates a DDOS pattern with the specified values.
     * @param protocol
     * @param host
     * @param port
     * @param threads
     * @param message
     * @param hours
     * @param minutes
     * @param seconds
     * @param timeout
     * @param socketTimeout 
     */
    public DDOSPattern(String protocol, String host, int port, int threads, String message, int hours, int minutes, int seconds, int timeout, int socketTimeout) {
        this.protocol = protocol;
        this.port = port;
        this.threads = threads;
        this.message = message;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.timeout = timeout;
        this.socketTimeout = socketTimeout;
    }

    /**
     * Get the procol name.
     * @return the protocolname (e.g tcp, udp ...)
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Set the protocol name.
     * @param protocol the name of the protocol (tcp, udp ...).
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Get the target host (victim).
     * @return 
     */
    public String getHost() {
        return host;
    }

    /**
     * Set the target host (victim).
     * @param host 
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Get the target port.
     * @return 
     */
    public int getPort() {
        return port;
    }

    /**
     * Set the target port.
     * @param port 
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Get the number of attacker threads.
     * @return 
     */
    public int getThreads() {
        return threads;
    }

    /**
     * Set the number of attacker threads.
     * @param threads 
     */
    public void setThreads(int threads) {
        this.threads = threads;
    }
    
    /**
     * Get the message to send to the victim.
     * @return message to send to the victim
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message to send to the victim.
     * @param message 
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * The duration time in hours.
     * NOTE that this is not the whole duration
     * time converted in hours
     * @return 
     */
    public int getHours() {
        return hours;
    }
    
    /**
     * The duration time in hours.
     * NOTE that this is not the whole duration
     * time converted in hours.
     * @param hours 
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * The duration time in minutes.
     * NOTE that this is not the whole duration
     * time converted in minutes
     * @return 
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Get the duration time in minutes.
     * NOTE that this is not the whole duration
     * time converted in minutes
     * @param minutes 
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Duration time in seconds.
     * NOTE that this is not the whole duration
     * time converted in seconds
     * @return duration time in seconds
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Set the duration time in seconds.
     * NOTE that this is not the whole duration
     * time converted in seconds
     * @param seconds 
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Get the timeout between every attack.
     * @return the time between every attack in milliseconds
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Set the timeout between every attack.
     * @param timeout time between every attack in milliseconds
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Get the timeout until the socket times out.
     * @return timeout until the socket times out in milliseconds
     */
    public int getSocketTimeout() {
        return socketTimeout;
    }

    /**
     * Set the timeout until the socket times out.
     * @param socketTimeout timeout until the socket times out in milliseconds
     */
    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    @Override
    public String toString() {
        JsonNode root = JsonNode.object();
        root.set("protocol", protocol);
        root.set("host", host);
        root.set("port", port);
        root.set("threads", threads);
        root.set("hours", hours);
        root.set("minutes", minutes);
        root.set("seconds", seconds);

        return root.toString();
    }
}
