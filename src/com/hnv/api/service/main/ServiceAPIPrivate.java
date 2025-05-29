package com.hnv.api.service.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hnv.api.interf.IServiceCallback;
import com.hnv.api.main.API;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.def.DefAPIExt;

/**
 * NVu.Hoang - Rin
 */

@RestController
@CrossOrigin()
@RequestMapping(value = DefAPIExt.URL_API_PRIVATE )
public class ServiceAPIPrivate extends HttpServlet implements IServiceCallback{
	private static final long serialVersionUID = 1L;

	static {
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		try {
			API.doService (API.SV_TYPE_PRIVATE, request, response, this);
		} catch (Exception e) {
			ToolLogServer.doLogErr(e);
		}
	}

	@Override
	public void doCallBack(HttpServletRequest arg0, HttpServletResponse arg1, Object... arg2) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
