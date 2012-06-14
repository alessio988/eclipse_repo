package com.kikudjiro.android.net;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

import android.os.Handler;
import android.os.Message;

/**
 * Implementation of the TCP/IP layer for android application as a client.
 * 
 * @author Alexander Alexeychuk
 * @email kikudjiro@gmail.com
 */
public class NetClient implements INetClient {
	private final int RECEIVE_MESSAGE = 1;
	private final int DISCONNECT = 2;
	private final int CONNECT = 3;
	private final int ERROR = 4;
	private final int CONNECT_TIMEOUT = 5;

	private final int MAX_PACKET_SIZE = 4096;
	
	public enum State
	{
	    OFFLINE, CONNECT, ONLINE;
	}

	public NetClient(INetClientDelegate delegate) {
		_delegate = delegate;
	
		_state = State.OFFLINE;
		
		_handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case CONNECT:
					_delegate.onConnect();
					break;
				case CONNECT_TIMEOUT:
					_delegate.onConnectTimeout();
					break;
				case RECEIVE_MESSAGE:
					_delegate.onReceive((ByteBuffer)msg.obj);
					break;
				case DISCONNECT:
					_socketChannel = null;
					_threadSocketReader = null;
					_delegate.onDisconnect();
					break;
				case ERROR:
					_delegate.onError((String)msg.obj);
					break;
				}
				super.handleMessage(msg);
			}
		};
	}

    public State getState() {
    	return _state;
    }
	
	public boolean connect(String host, int port) {
		if (_state != State.OFFLINE)
			return false;
		
		_state = State.CONNECT;
		
    	try {
			SocketAddress sockaddr = new InetSocketAddress(InetAddress.getByName(host), port);
			_socketChannel = SocketChannel.open();
			_socketChannel.configureBlocking(false);
			_socketChannel.connect(sockaddr);

			_threadSocketReader = new Thread(new _SocketReader());
			_threadSocketReader.start();
		} catch (ConnectException e) {
			_socketChannel = null;
			_delegate.onError("Can't connect");
			return false;
		} catch (UnknownHostException e) {
			_socketChannel = null;
			_delegate.onError("Unknown host");
			return false;
		} catch (Exception e) {
			_socketChannel = null;
			_delegate.onError("Exception");
			return false;
		}
    	return true;
	}

	public void disconnect() {
    	if (null == _threadSocketReader)
    		return;
		try {
			// to avoid receiving packets from server
			// so, socket reader thread will finish
			_socketChannel.socket().shutdownInput();
			
			_socketChannel.close();
		} catch (IOException e) {
			_delegate.onError("Problem with socket close");
		}
	}

	public void send(byte[] data) {
		if (null == _socketChannel)
			return;
		try {
			int n = data.length;
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 + n);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			buffer.putInt(n);
			buffer.put(data);
			buffer.flip();
			_socketChannel.write(buffer);
		} catch (IOException e) {
    	    throw new RuntimeException(e);
		}
	}

    private class _SocketReader implements Runnable
    {
    	public void run() {
    		Message msgError;
    		
    		int connectCounter = 0;
        	while (State.CONNECT == _state)
        	{
        		try {
					if (_socketChannel.finishConnect())
					{
						break;
					} else {
						connectCounter++;
			        	if (connectCounter >= 5000 / 100)
			        	{
			        		_state = State.OFFLINE;
			        		_socketChannel.close();
			        		_socketChannel = null;

			        		// inform UI about error
			    	    	msgError = NetClient.this._handler.obtainMessage();
			    	    	msgError.what = CONNECT_TIMEOUT;
			    	        NetClient.this._handler.sendMessage(msgError);
			    	        
			        		return;
			        	}
					}
				} catch (IOException e) {
	        		_state = State.OFFLINE;
					_socketChannel = null;

					// inform UI about error
	    	    	msgError = NetClient.this._handler.obtainMessage();
	    	    	msgError.what = ERROR;
	    	    	msgError.obj = "Can't connect: IOException";
	    	        NetClient.this._handler.sendMessage(msgError);
	    	        
					return;
				}
        		try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
        	}
    		
        	if (State.CONNECT != _state)
        		return;
        	
    		try {
				_socketChannel.configureBlocking(true);
			} catch (IOException e1) {
			}

    		_state = State.ONLINE;

        	ByteBuffer _buffer;
    		_buffer = ByteBuffer.allocateDirect(MAX_PACKET_SIZE + 4);
    		_buffer.order(ByteOrder.LITTLE_ENDIAN);

	    	// inform UI about connect
	    	Message msgConnect = NetClient.this._handler.obtainMessage();
	    	msgConnect.what = CONNECT;
	        NetClient.this._handler.sendMessage(msgConnect);
    		
    		while(true)
    		{
	        	try {
	        	    int numBytesRead = _socketChannel.read(_buffer);

	        	    if (numBytesRead == -1) {
	        	        // No more bytes can be read from the channel
	        	    	
	        	    	_state = State.OFFLINE;
	        	    	
	        	    	// inform UI about disconnect
	        	    	Message msgDisconnect = NetClient.this._handler.obtainMessage();
	        	    	msgDisconnect.what = DISCONNECT;
	        	        NetClient.this._handler.sendMessage(msgDisconnect);
	        	    	
	        	        _socketChannel.close();
	        	        break;
	        	    } else {
	        	        // To read the bytes, flip the buffer
	        	    	_buffer.flip();
	        	    	
	        	    	while (_buffer.remaining() > 4)
	        	    	{
	        	    		// save the head of the packet
	        	    		_buffer.mark();
	        	    		
		        	    	// Read the number of bytes in the packet
		        	    	int packetSize = _buffer.getInt();
		        	    	
		        	    	if (packetSize > MAX_PACKET_SIZE)
		        	    	{
		    	        		_state = State.OFFLINE;
		    	        		_socketChannel.close();
		    					_socketChannel = null;

		    					// inform UI about error
		    	    	    	msgError = NetClient.this._handler.obtainMessage();
		    	    	    	msgError.what = ERROR;
		    	    	    	msgError.obj = "Packet size more than " + MAX_PACKET_SIZE;
		    	    	        NetClient.this._handler.sendMessage(msgError);
		        	    	}
		        	    	
		        	    	// if we have all bytes of the packet
		        	    	if (_buffer.remaining() >= packetSize)
		        	    	{
		        	    		// save buffer params
		        	    		int limit = _buffer.limit();
		        	    		int packetEnd = _buffer.position() + packetSize;
		        	    		
		        	    		// set the size of packet to read
		        	    		_buffer.limit(packetEnd);

		        	    		// read the packet body from the buffer
			        	        byte[] packetData = new byte[packetSize];
			        	        _buffer.get(packetData);
		        	    		
			        	    	// inform UI about incoming message
			        	    	Message msg = NetClient.this._handler.obtainMessage();
			        	        msg.what = RECEIVE_MESSAGE;
			        	        msg.obj = ByteBuffer.wrap(packetData);
			        	        NetClient.this._handler.sendMessage(msg);
		        	    		
			        	        // restore and update buffer params
		        	    		_buffer.limit(limit);
		        	    		_buffer.position(packetEnd);
		        	    	} else {
		        	    		// there are not all bytes for the packet
		        	    		
		        	    		// restore the head of the packet
		        	    		_buffer.reset();
		        	    		
		        	    		break;
		        	    	}
	        	    	}

	        	    	_buffer.compact();
	        	    }
	        	} catch (Exception e) {
					// inform UI about error
	    	    	msgError = NetClient.this._handler.obtainMessage();
	    	    	msgError.what = ERROR;
	    	    	msgError.obj = e.toString();
	    	        NetClient.this._handler.sendMessage(msgError);
	        	}    	
    		}
    		
    	}
    }
    
    private State _state;
    
	private INetClientDelegate _delegate;
	private SocketChannel _socketChannel;
	private Thread _threadSocketReader;
	private Handler _handler;
	
}
