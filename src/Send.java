import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
public class Send {
	private static String s;
	public Send() throws HttpException, IOException
	{
		s=Signup.gets3();//获取到的手机号
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
		//PostMethod post = new PostMethod("http://sms.webchinese.cn/web_api/"); 
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
		NameValuePair[] data ={
				new NameValuePair("Uid", "RobertLau"),
				new NameValuePair("Key", "5df586b0cd28a9d55702"),
				new NameValuePair("smsMob",s),//"17863013860"
				new NameValuePair("smsText","您的企业管理系统验证码是：236703")};
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		//System.out.println("statusCode:"+statusCode);
		//for(Header h : headers)
		//	System.out.println("---"+h.toString());
		String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
		//System.out.println(result);
		switch(result)
		{
		case"-1":Signup._l3.setText("没有该用户账户");break;
		case"-4":Signup._l3.setText("手机号格式不正确");break;
		case"-11":Signup._l3.setText("该用户被禁用");break;
		case"-41":Signup._l3.setText("手机号码为空");break;
		default:Signup._l3.setText("√");break;
		}

		post.releaseConnection();
	}
	/*public static void main(String[] args)throws Exception
	{
		Send s=new Send();
	}*/
}

