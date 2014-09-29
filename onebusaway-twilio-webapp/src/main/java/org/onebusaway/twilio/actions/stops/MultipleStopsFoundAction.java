package org.onebusaway.twilio.actions.stops;

import java.util.List;

import org.onebusaway.presentation.services.text.TextModification;
import org.onebusaway.transit_data.model.StopBean;
import org.onebusaway.twilio.actions.Messages;
import org.onebusaway.twilio.actions.TwilioSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class MultipleStopsFoundAction extends TwilioSupport {

  private TextModification _destinationPronunciation;

  @Autowired
  public void setDestinationPronunciation(
      @Qualifier("destinationPronunciation") TextModification destinationPronunciation) {
    _destinationPronunciation = destinationPronunciation;
  }


  @Override
  public String execute() throws Exception {

	ActionContext context = ActionContext.getContext();
    ValueStack vs = context.getValueStack();
    List<StopBean> stops = (List<StopBean>) vs.findValue("stops");
    
    int index = 1;
    
    addMessage(Messages.MULTIPLE_STOPS_WERE_FOUND);
    
    for( StopBean stop : stops) {
      
      addMessage(Messages.FOR);
      
      addText(_destinationPronunciation.modify(stop.getName()));
      
      addMessage(Messages.PLEASE_PRESS);
      
      String key = Integer.toString(index++);
      addText(key);
      //AgiActionName action = addAction(key,"/stop/arrivalsAndDeparturesForStopId");
      //action.putParam("stopIds",Arrays.asList(stop.getId()));
    }

    addMessage(Messages.HOW_TO_GO_BACK);
    //addAction("\\*", "/back");

    addMessage(Messages.TO_REPEAT);
    
    return SUCCESS;
  }}