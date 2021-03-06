package org.onebusaway.twilio.actions.stops;

import java.util.List;

import org.onebusaway.presentation.services.text.TextModification;
import org.onebusaway.transit_data.model.StopBean;
/**
 * Copyright (C) 2011 Brian Ferris <bdferris@onebusaway.org>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
      
      String destination = _destinationPronunciation.modify(stop.getName());
      destination = destination.replaceAll("\\&", "and");      
      addText(destination);
      addText(", ");
      
      addMessage(Messages.PLEASE_PRESS);
      
      String key = Integer.toString(index++);
      addText(key);
      addText(". ");
   }

    addMessage(Messages.HOW_TO_GO_BACK);
    //addAction("\\*", "/back");

    addMessage(Messages.TO_REPEAT);
    
    return SUCCESS;
  }}
