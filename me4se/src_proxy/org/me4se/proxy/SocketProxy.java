// added by andre
package org.me4se.proxy;

import java.net.*;
import java.io.*;
import javax.microedition.io.*;


public class SocketProxy
{
	static public void main( String args[] )
	{	
		try
		{	
			int port = Integer.parseInt( args[0] );
			System.out.println( "Starting socket proxy on port " + port );
			ServerSocket serverSocket = new ServerSocket( port );
			while( true )
			{
				Socket socket = serverSocket.accept();
				ClientToServerThread t = new ClientToServerThread( socket );
				t.start();
			}	
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}		
	}
	
	static public class ClientToServerThread extends Thread
	{
		Socket inSocket;
		
		public ClientToServerThread( Socket inSocket )
		{
			this.inSocket = inSocket;
		}
		
		public void run()
		{
			Socket outSocket = null;
			InputStream is = null;
			OutputStream os = null;
			try
			{
				is = inSocket.getInputStream();
				DataInputStream dis = new DataInputStream( is );
				String host = dis.readUTF();
				int port = dis.readInt();
				outSocket = new Socket( host , port );		

				System.out.println( "relaying from " + inSocket.getInetAddress().getHostAddress()  + ":" + inSocket.getLocalPort() + " " + inSocket.getInetAddress().getHostName() + " " + " to " + host + ":" + port );
				
				ServerToClientThread t = new ServerToClientThread( inSocket , outSocket );
				t.start();					
				os = outSocket.getOutputStream();
				while( true  )
				{
					int b = is.read();
					if( b < 0 )
						break;					
					os.write( b );
				}	
				os.flush();
			}
			catch( Exception e )
			{
			}
			finally
			{
				if( is != null )
					try{ is.close(); }catch( Exception e){}
				if( os != null )
					try{ os.close(); }catch( Exception e){}
				if( inSocket != null )
					try{ inSocket.close(); }catch( Exception e){}
				if( outSocket != null )
					try{ outSocket.close(); }catch( Exception e){}					 
			}										
		}		
	}		

	static public class ServerToClientThread extends Thread
	{
		Socket inSocket;
		Socket outSocket;
		
		public ServerToClientThread( Socket inSocket , Socket outSocket )
		{
			this.inSocket = inSocket;
			this.outSocket = outSocket;
		}
		
		public void run()
		{
			InputStream is = null;
			OutputStream os = null;
			try
			{
				is = outSocket.getInputStream();
				os = inSocket.getOutputStream();
				while( true  )
				{
					int b = is.read();
					if( b < 0 )
						break;					
					os.write( b );
				}	
				os.flush();
			}
			catch( Exception e )
			{
			}
			finally
			{
				if( is != null )
					try{ is.close(); }catch( Exception e){}
				if( os != null )
					try{ os.close(); }catch( Exception e){}
				if( inSocket != null )
					try{ inSocket.close(); }catch( Exception e){}
				if( outSocket != null )
					try{ outSocket.close(); }catch( Exception e){}					 
			}										
		}		
	}		

}
// end added by andre
