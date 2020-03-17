package com.gxuwz.medical.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


public class EncodingFilter implements Filter {
	private FilterConfig filterConfig = null;
    private String encode = null;
    private boolean isNotEncode = true;
    
    public void init(FilterConfig filterConfig) throws ServletException {
 
        this.filterConfig = filterConfig;
        encode = filterConfig.getInitParameter("encode") == null ?"utf-8":filterConfig.getInitParameter("encode");
    }
 
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
 
	    servletResponse.setContentType("text/html;charset=" + encode);
        filterChain.doFilter(new MyHttpRequest((HttpServletRequest) servletRequest),servletResponse);
    }
 
    public void destroy() {
 
    }
    
    class MyHttpRequest extends HttpServletRequestWrapper {
    	 
        private HttpServletRequest httpServletRequest = null;
 
        public MyHttpRequest(HttpServletRequest request) {
            super(request);
            this.httpServletRequest = request;
        }
 
        public Map getParameterMap() {
 
            if (httpServletRequest.getMethod().equalsIgnoreCase("POST")) {
                try {
                    httpServletRequest.setCharacterEncoding(encode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return super.getParameterMap();
            } else if (httpServletRequest.getMethod().equalsIgnoreCase("GET")) {
 
                Map<String, String[]> map = httpServletRequest.getParameterMap();
                if(isNotEncode) {
                    for (Map.Entry<String, String[]> m : map.entrySet()) {
                        String[] v = m.getValue();
                        for (int i = 0; i < v.length; i++) {
                            try {
                                v[i] = new String(v[i].getBytes("iso8859-1"), encode);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    isNotEncode = false;   //第二次是查询缓存 防止再次编码
                }
                return map;
            }
            else {
                return super.getParameterMap();
            }
 
        }
 
        public String[] getParameterValues(String name) {
 
            return (String[]) this.getParameterMap().get(name);
        }
 
        public String getParameter(String name) {
 
            return getParameterValues(name) == null ? null : getParameterValues(name)[0];
        }
 
    }
 
}

    
