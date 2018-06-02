package cn.zzuli.cloud.commons.webservice;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.zzuli.cloud.commons.exception.EnnException;

import javax.xml.soap.*;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.net.*;
import java.util.Iterator;


/**
 * SOAP工具类
 *
 * @author fredlau
 * @date 2017/12/15/015
 */
public class SOAPServiceUtils {
	private static SOAPServiceUtils serviceTool = null;
	
	/**
	 * 获取单例
	 *
	 * @return
	 */
	public static SOAPServiceUtils getInstance ( ) {
		if ( serviceTool == null ) {
			serviceTool = new SOAPServiceUtils ( );
		}
		return serviceTool;
	}
	
	/**
	 * 发送请求
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @param sendurl
	 * @param request
	 * @param uri
	 * @param params   请求参数
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public JSONObject sendUrl (String username , String password , String sendurl , String request , String uri , Object params , String arrayField ) throws Exception {
		try {
			if ( ! username.isEmpty ( ) && ! password.isEmpty ( ) ) {
				Authenticator.setDefault ( new MyAuthenticator ( username , password ) );
			}
			
			// 创建连接
			// ==================================================
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance ( );
			SOAPConnection connection = soapConnFactory.createConnection ( );
			
			//  创建消息对象
			// ===========================================
			MessageFactory messageFactory = MessageFactory.newInstance ( );
			SOAPMessage message = messageFactory.createMessage ( );
			
			// 创建soap消息主体==========================================
			// 创建soap部分
			SOAPPart soapPart = message.getSOAPPart ( );
			SOAPEnvelope envelope = soapPart.getEnvelope ( );
			SOAPBody body = envelope.getBody ( );
			//  根据要传给mule的参数，创建消息body内容。具体参数的配置可以参照应用集成接口技术规范1.1版本
			// =====================================
			SOAPElement bodyElement = body.addChildElement ( envelope.createName ( request , "Request" , uri ) );
			
			if ( params instanceof JSONArray) {
				JSONArray param = (JSONArray) params;
				createBodyElement ( bodyElement , param , arrayField );
			} else if ( params instanceof JSONObject) {
				JSONObject param = (JSONObject) params;
				createBodyElement ( bodyElement , param , arrayField );
			}
			
			// Save the message
			message.saveChanges ( );
			// 打印客户端发出的soap报文，做验证测试
			message.writeTo ( System.out );
			/*
			 * 实际的消息是使用 call()方法发送的，该方法接收消息本身和目的地作为参数，并返回第二个 SOAPMessage 作为响应。
             * call方法的message对象为发送的soap报文，url为mule配置的inbound端口地址。 
             */
			
			//设置超时时间
			URLStreamHandler stream = new URLStream ( );
			URL url = new URL ( new URL ( sendurl ) , "" , stream );
			
			// 响应消息
			// ===========================================================================
			SOAPMessage reply = connection.call ( message , url );
			// 打印服务端返回的soap报文供测试
			// ==================创建soap消息转换对象
			TransformerFactory transformerFactory = TransformerFactory.newInstance ( );
			Transformer transformer = transformerFactory.newTransformer ( );
			// Extract the content of the reply======================提取消息内容
			Source sourceContent = reply.getSOAPPart ( ).getContent ( );
			// Set the output for the transformation
			StreamResult result = new StreamResult ( System.out );
			transformer.transform ( sourceContent , result );
			// Close the connection 关闭连接 ==============
			connection.close ( );
			/*
			 * 模拟客户端A，异常处理测试
             */
			JSONObject object = new JSONObject( );
			SOAPBody ycBody = reply.getSOAPBody ( );
			Node ycResp = ycBody.getFirstChild ( );
			NodeList nodeList = ycResp.getChildNodes ( );
			for ( int i = 0 ; i < nodeList.getLength ( ) ; i++ ) {
				String nodeName = nodeList.item ( i ).getNodeName ( );
				//返回结果解析
				if ( nodeList.item ( i ).hasChildNodes ( ) && ! "#text".equalsIgnoreCase ( nodeList.item ( i ).getChildNodes ( ).item ( 0 ).getNodeName ( ) ) ) {
					JSONArray array = new JSONArray( );
					NodeList childList = nodeList.item ( i ).getChildNodes ( );
					for ( int j = 0 ; j < childList.getLength ( ) ; j++ ) {
						JSONObject joj = new JSONObject( );
						String namej = childList.item ( j ).getNodeName ( );
						if ( childList.item ( j ).hasChildNodes ( ) && ! "#text".equalsIgnoreCase ( childList.item ( j ).getChildNodes ( ).item ( 0 ).getNodeName ( ) ) ) {
							NodeList kcList = childList.item ( j ).getChildNodes ( );
							JSONObject jok = new JSONObject( );
							for ( int k = 0 ; k < kcList.getLength ( ) ; k++ ) {
								String namek = kcList.item ( k ).getNodeName ( );
								String testk = kcList.item ( k ).getTextContent ( );
								jok.put ( namek , testk );
							}
							joj.put ( namej , jok );
						} else {
							String textj = childList.item ( j ).getTextContent ( );
							joj.put ( namej , textj );
						}
						array.put ( joj );
					}
					object.put ( nodeName , array );
				} else {
					String nodeText = nodeList.item ( i ).getTextContent ( );
					object.put ( nodeName , nodeText );
				}
				
			}
			
			return object;
		} catch ( Exception e ) {
			throw new EnnException( e );
		}
	}
	
	private void createBodyElement (SOAPElement bodyElement , JSONObject params , String arrayField ) throws Exception {
		Iterator < ? > iterator = params.keys ( );
		while ( iterator.hasNext ( ) ) {
			String key = ( String ) iterator.next ( );
			Object val = params.get ( key );
			if ( val instanceof JSONArray) {
				SOAPElement topElement = bodyElement.addChildElement ( key );
				
				JSONArray array = params.getJSONArray ( key );
				this.createBodyElement ( topElement , array , arrayField );
			} else if ( val instanceof JSONObject) {
				SOAPElement topElement = bodyElement.addChildElement ( key );
				JSONObject obj = params.getJSONObject ( key );
				Iterator < ? > iteratorTmp = obj.keys ( );
				while ( iteratorTmp.hasNext ( ) ) {
					String keyTmp = ( String ) iteratorTmp.next ( );
					Object valTmp = obj.get ( keyTmp );
					if ( valTmp instanceof JSONObject) {
						SOAPElement topElement1 = topElement.addChildElement ( keyTmp , "tem" );
						JSONObject obj1 = obj.getJSONObject ( keyTmp );
						Iterator < ? > iteratorTmp1 = obj1.keys ( );
						while ( iteratorTmp1.hasNext ( ) ) {
							String keyTmp1 = ( String ) iteratorTmp1.next ( );
							Object valTmp1 = obj1.get ( keyTmp1 );
							if ( valTmp1 instanceof JSONArray) {
								SOAPElement topElement2 = topElement1.addChildElement ( keyTmp1 , "map" );
								JSONArray list = obj1.getJSONArray ( keyTmp1 );
								for ( int i = 0 ; i < list.length ( ) ; i++ ) {
									JSONObject jo = list.getJSONObject ( i );
									SOAPElement ele = topElement2.addChildElement ( "ApplyFileInfo" , "map" );
									for ( Iterator < ? > ikey = jo.keys ( ) ; ikey.hasNext ( ) ; ) {
										String keyV = ( String ) ikey.next ( );
										String valV = jo.getString ( keyV );
										ele.addChildElement ( keyV , "map" ).addTextNode ( valV );
									}
								}
								
							} else {
								String ttv = ( String ) valTmp1;
								topElement1.addChildElement ( keyTmp1 , "map" ).addTextNode ( ttv );
							}
							
						}
					} else if ( valTmp instanceof String ) {
						String value = obj.getString ( keyTmp ) == null ? "" : obj.getString ( keyTmp );
						topElement.addChildElement ( keyTmp ).addTextNode ( value );
					}
				}
			} else {
				String value = params.getString ( key ) == null ? "" : params.getString ( key );
				bodyElement.addChildElement ( key ).addTextNode ( value );
			}
			
		}
	}
	
	private void createBodyElement (SOAPElement bodyElement , JSONArray params , String arrayField ) throws Exception {
		for ( int i = 0 ; i < params.length ( ) ; i++ ) {
			SOAPElement item = SOAPFactory.newInstance ( ).createElement ( arrayField );
			JSONObject obj = params.getJSONObject ( i );
			Iterator < String > iteratorTmp = obj.keys ( );
			while ( iteratorTmp.hasNext ( ) ) {
				String keyTmp = iteratorTmp.next ( );
				String valTmp = obj.getString ( keyTmp );
				item.addChildElement ( keyTmp ).addTextNode ( valTmp );
			}
			bodyElement.addChildElement ( item );
		}
	}
	
	class MyAuthenticator extends Authenticator {
		private String username = "";
		private String password = "";
		
		public MyAuthenticator ( String username , String password ) {
			this.username = username;
			this.password = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication ( ) {
			return new PasswordAuthentication ( username , password.toCharArray ( ) );
		}
	}
	
	
	class URLStream extends URLStreamHandler {
		
		@Override
		protected URLConnection openConnection ( URL u ) throws IOException {
			URL target = new URL ( u.toString ( ) );
			URLConnection connection = target.openConnection ( );
			// Connection settings
			connection.setConnectTimeout ( 180 * 1000 );
			connection.setReadTimeout ( 180 * 1000 );
			return ( connection );
		}
	}
}
