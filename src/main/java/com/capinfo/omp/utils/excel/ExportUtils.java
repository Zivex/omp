package com.capinfo.omp.utils.excel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.NativeWebRequest;

import com.capinfo.framework.util.LogUtils;

public class ExportUtils {

	private static final Log LOG = LogFactory.getLog(ExportUtils.class);
	public static String getExportFileName(NativeWebRequest request, String name) {
		
		String userAgent = request.getHeader("user-agent").toLowerCase();
        	try {
				if(userAgent.indexOf("firefox") != -1) { 
					
					name = new String(name.getBytes("UTF-8"), "ISO8859-1");
				} else{
					
					name = URLEncoder.encode((name + ".xls"), "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				LogUtils.debugException(LOG, e);
//				e.printStackTrace();
			}
        return name;
	}
}
