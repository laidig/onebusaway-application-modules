package org.onebusaway.twilio.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.onebusaway.twilio.services.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwilioDispatchFilter implements Filter {

  private static final long serialVersionUID = 1L;
  
  private static Logger _log = LoggerFactory.getLogger(TwilioDispatchFilter.class);
  
  private SessionManager _sessionManager = null;
  private static final String PHONE_NUMBER_KEY = "From";
  private static final String NEXT_ACTION = "twilio.nextAction";
  private static final String INDEX_ACTION = "index";

  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
    String key = request.getParameter(PHONE_NUMBER_KEY);
    
    char c = key.charAt(0);
    if (!Character.isDigit(c)) {
    	String tempKey = "";
    	for (int i=0; i<key.length(); ++i) {
    		c = key.charAt(0);
    		if (!Character.isDigit(c)) {
    			if (tempKey.length() > 0) break;
    			continue;
    		}
    		tempKey += c;
    	}
    	key = tempKey;
    }
    _log.debug("key: " + key);
    
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    
    // if we've seen this user before
    if (_sessionManager.hasContext(key)) {
      _log.debug("found session=" + _sessionManager.getContext(key));
      // redirect to last known location
      Map<String, Object> context = _sessionManager.getContext(key);
      String nextAction = (String) context.get(NEXT_ACTION);
      if (nextAction != null) {
        _log.debug("forwarding to " + nextAction);
        httpResponse.sendRedirect(format(httpRequest, nextAction));
        return;
      } 
    }

    _log.debug("fall through forwarding to " + INDEX_ACTION);
    // redirect to welcome page
    httpResponse.sendRedirect(format(httpRequest, INDEX_ACTION));
  }

  private String format(HttpServletRequest request, String nextAction) {
    
    String path = nextAction + "?_foo=" + System.currentTimeMillis();

    // copy the path to the forwarded request
    for (Object key : request.getParameterMap().keySet()) {
      path = path + "&" + key + "=" + request.getParameter((String)key);
    }
    return path;
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
    _sessionManager = (SessionManager) config.getServletContext().getAttribute("twilioSessionManager");
  }

  @Override
  public void destroy() {
    // nothing to do
  }
   

}