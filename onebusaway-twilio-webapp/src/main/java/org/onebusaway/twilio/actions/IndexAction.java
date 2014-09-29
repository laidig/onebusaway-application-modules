package org.onebusaway.twilio.actions;

import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Results({
  @Result(name="registration", location="registration", type="redirectAction"),
  @Result(name="help", location="help", type="redirectAction"),
  @Result(name="stops-index", location="stops/index", type="redirectAction", params={"From", "${phoneNumber}"}),
  @Result(name="find-your-stop", location="find-your-stop", type="redirectAction", params={"From", "${phoneNumber}"}),
  @Result(name="bookmarks-index", location="bookmarks/index", type="redirectAction", params={"From", "${phoneNumber}"}),
  @Result(name="bookmarks-manage", location="bookmarks/manage", type="redirectAction", params={"From", "${phoneNumber}"}),
  @Result(name="search-index", location="search/index", type="redirectAction", params={"From", "${phoneNumber}"}),
  @Result(name="index", location="index", type="redirectAction", params={"From", "${phoneNumber}"})
})
public class IndexAction extends TwilioSupport implements SessionAware {

  private static final long serialVersionUID = 1L;
  private static Logger _log = LoggerFactory.getLogger(IndexAction.class);
  private String digits;
  private String from;
  
  private Map sessionMap;
  
  public void setDigits(String digits) {
	  this.digits = digits;
  }
  public void setFrom(String from) {
	  this.from = from;
  }
  
  public void setSession(Map map) {
    this.sessionMap = map;
  }
  
  @Override
  public String execute() throws Exception {
    _log.debug("in execute! with input=" + getInput());
    logUserInteraction();
    Integer navState = (Integer)sessionMap.get("navState");
		if (navState == null) {
			navState = DISPLAY_DATA;
		}
    
		if (navState == DISPLAY_DATA) {
      if( _currentUserService.hasPhoneNumberRegistration() ) {
        _log.debug("registration needed");
        return "registration";
      }
      _log.debug("no registration necessary");
			sessionMap.put("navState", DO_ROUTING);
			return SUCCESS;
    }  else {	// Process input and route to the appropriate action.    
			sessionMap.put("navState", DISPLAY_DATA);
			int key = 0;
			if (getInput().length() > 1 || !Character.isDigit(getInput().charAt(0))) {
				return "repeat";
			} else {
				key = Integer.parseInt(getInput());
			}
			_log.debug("key: " + key);
			switch(key) {  
				case 0: return "help";
				case 1: return "stops-index";
				case 2: return "find-your-stop";
				case 3: return "bookmarks-index";
				case 4: return "bookmarks-manage";
				case 6: return "search-index";
				default: return "index";
			}
		}
  }
}