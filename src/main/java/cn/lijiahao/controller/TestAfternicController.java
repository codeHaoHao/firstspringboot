package cn.lijiahao.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lijiahao.utils.HttpClient;

@Controller
public class TestAfternicController {
	private static final String requestUrl = "https://purchase.api.ote-afternic.com/v4/domains/aftermarket/listings/franky.com/hold";
    //requestUrl = "http://aftermarket-purchase-swagger-ote.us-east-2.elasticbeanstalk.com/v4/domains/aftermarket/listings/"+domain+"/hold";
	
	
	  
	  
	  @RequestMapping("/testAfternic")
	  @ResponseBody
	  public String testRequest(HttpServletRequest request,HttpServletResponse response) {
		  String responseResult = null;
//		  response.addHeader(KEY_HEADER, KEY_VALUE);
//		  response.addHeader(SECRET_HEADER, SECRET_VALUE);
//		  response.addHeader("Location", requestUrl);
//		  try {
//			response.sendRedirect(requestUrl);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		  HttpHeaders headers = new HttpHeaders();
		  headers.add(KEY_HEADER, KEY_VALUE);
		  headers.add(SECRET_HEADER, SECRET_VALUE);
		  responseResult = HttpClient.sendGetRequest(requestUrl, null, headers);
		  return responseResult;
	  }
}
