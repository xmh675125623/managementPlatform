package com.jiuzhou.firewall.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;

/**
 * @Description: TODO
 * @ClassName: Singleton
 * @author weisf
 * @date 2017年9月1日 上午10:05:08
 * @version V1.0
 * 
 */
public class Singleton {
	
	
	
	
	
    private static Singleton single = null;
    private DatagramSocket ds = null;
    DatagramPacket dp_send;
    DatagramPacket dp_receive;
    InetAddress loc;
	String str_send = "你在干什么抓紧回应";
	byte[] buf = new byte[1024];  
 	Socket socket=null; 
    private Singleton(Map maps) {
        try {
           
        	
            socket=new Socket(maps.get("mip").toString(),Integer.parseInt(maps.get("port").toString())); 
            socket.setSoLinger(true, 0); 
        } catch (  Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private Singleton() {
        try {
           
        	
            socket=new Socket("192.168.0.100",6860); 

        } catch (  Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // 静态工厂方法
    public static Singleton getInstance(Map maps) {
        if (single == null) {
            single = new Singleton(maps);
        }
        return single;
    }
    public static Singleton getInstance() {
        if (single == null) {
            single = new Singleton();
        }
        return single;
    }
    public DatagramSocket getDs() {
        return ds;
    }

    public void setDs(DatagramSocket ds) {
        this.ds = ds;
    }

	public DatagramPacket getDp_send() {
		return dp_send;
	}

	public void setDp_send(DatagramPacket dp_send) {
		this.dp_send = dp_send;
	}

	public DatagramPacket getDp_receive() {
		return dp_receive;
	}

	public void setDp_receive(DatagramPacket dp_receive) {
		this.dp_receive = dp_receive;
	}

	public InetAddress getLoc() {
		return loc;
	}

	public void setLoc(InetAddress loc) {
		this.loc = loc;
	}

	public String getStr_send() {
		return str_send;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setStr_send(String str_send) {
		this.str_send = str_send;
	}


}
