// added by andre
package org.me4se.proxy;

import java.net.*;
import java.io.*;
import java.util.*;
import javax.microedition.io.*;


public class HttpProxy
{
	static public void main( String args[] )
	{	
		try
		{	
			int port = Integer.parseInt( args[0] );			
			System.out.println( "Starting http proxy on port " + port );
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
			StringBuffer sb = new StringBuffer();
			try
			{
				is = inSocket.getInputStream();
				String hostPort = null;
				String host = null;
				int port = -1;
				String currentLine = readLine( is );
				while( ( currentLine != null ) && ( currentLine.equals( "" ) ) )
				{					
					sb.append( currentLine );
					sb.append( "\r\n" );			
				}
				StringTokenizer st = new StringTokenizer( currentLine );
				String method = st.nextToken();
				String url = st.nextToken();
				String protocol = st.nextToken();
				if( url.startsWith( "http://" ) )
				{
					int index = url.indexOf( "/" , 7 );
					url = url.substring( index );
				}
				sb.append( method );
				sb.append( " " );
				sb.append( url );
				sb.append( " " );
				sb.append( protocol );
				sb.append( "\r\n" );			
				
				while( ( currentLine != null ) && ( ! currentLine.equals( "" ) ) )
				{
					int index1 = currentLine.indexOf( ':' );
					if( index1 >= 0 )
					{
						String headerName = currentLine.substring( 0 , index1 );
						if( headerName == null )
							continue;
						if( headerName.trim().equalsIgnoreCase( "host" ) )
						{ 
							hostPort = currentLine.substring( index1 + 1 ).trim();
							int index2 = hostPort.indexOf( ':' );
							if( index2 >= 0 )
							{
								host = hostPort.substring( 0 , index2 );
								port = Integer.parseInt( hostPort.substring( index2 + 1 ) ); 
								break;
							}
							else
							{
								host = hostPort;
								port = 80;
								break;
							}	
																				
						}
					}
					currentLine = readLine( is );					
					sb.append( currentLine );
					sb.append( "\r\n" );
				}
				
				outSocket = new Socket( host , port );		

				System.out.println( "relaying from " + inSocket.getInetAddress().getHostAddress()  + ":" + inSocket.getLocalPort() + " " + inSocket.getInetAddress().getHostName() + " " + " to " + host + ":" + port );
				
				ServerToClientThread t = new ServerToClientThread( inSocket , outSocket );
				t.start();					
				os = outSocket.getOutputStream();
				byte data[] = sb.toString().getBytes();
				for( int i=0 ; i<data.length ; i++ )
				{
					//System.out.print( (char) data[i] );
					os.write( data[i] );
				}
				
				while( true  )
				{
					int b = is.read();
					if( b < 0 )
						break;					
					//System.out.print( (char) b );
					os.write( b );
				}	
				os.flush();
			}
			catch( Exception e )
			{
			}
			finally
			{
				try{ Thread.sleep( 2000 ); }catch( Exception e ){}
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
					//System.out.print( (char) b );
					os.write( b );
				}	
				os.flush();
			}
			catch( Exception e )
			{
			}
			finally
			{
				try{ Thread.sleep( 2000 ); }catch( Exception e ){}
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

	private static String readLine( InputStream is )
	throws IOException
	{
		boolean firstTime = true;
		char lastRead = (char) -1;
		int read = -1;		
		char charRead = (char) -1;
		StringBuffer sb = new StringBuffer();
		while( true )
		{
			read = is.read();
			if( read < 0 )
				throw new IOException( "Cannot read from the input stream" );
			charRead = (char) read;
			if( (!firstTime) && ( lastRead == '\r' ) && ( charRead == '\n' ) )
				break;
			if( !firstTime )
				sb.append( lastRead );
			else
				firstTime = false;
			lastRead = charRead; 
		}
		return sb.toString();
	}

}
// end added by andre
