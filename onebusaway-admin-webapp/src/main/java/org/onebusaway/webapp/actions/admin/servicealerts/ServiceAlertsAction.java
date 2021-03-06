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
package org.onebusaway.webapp.actions.admin.servicealerts;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.onebusaway.transit_data.model.AgencyWithCoverageBean;
import org.onebusaway.transit_data.model.ListBean;
import org.onebusaway.transit_data.model.service_alerts.ServiceAlertBean;
import org.onebusaway.transit_data.services.TransitDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

@Results({@Result(type = "redirectAction", name = "redirect", params = {
    "actionName", "service-alerts!agency", "agencyId", "${agencyId}", "parse",
    "true"})})
public class ServiceAlertsAction extends ActionSupport {

  private static final long serialVersionUID = 1L;
  private static Logger _log = LoggerFactory.getLogger(ServiceAlertsAction.class);

  private TransitDataService _transitDataService;

  private String _agencyId;

  private List<AgencyWithCoverageBean> _agencies;

  private List<ServiceAlertBean> _situations;

  @Autowired
  public void setTransitDataService(TransitDataService transitDataService) {
    _transitDataService = transitDataService;
  }

  public void setAgencyId(String agencyId) {
    _agencyId = agencyId;
  }

  public String getAgencyId() {
    return _agencyId;
  }

  public List<AgencyWithCoverageBean> getAgencies() {
    return _agencies;
  }

  public List<ServiceAlertBean> getSituations() {
    return _situations;
  }

  @SkipValidation
  @Override
  public String execute() {
    try {
      _agencies = _transitDataService.getAgenciesWithCoverage();
    } catch (Throwable t) {
      _log.error("unable to retrieve agencies with coverage", t);
      _log.error("issue connecting to TDS -- check your configuration in data-sources.xml");
      throw new RuntimeException("Check your onebusaway-nyc-transit-data-federation-webapp configuration", t);
    }
    return SUCCESS;
  }

  @Validations(requiredStrings = {@RequiredStringValidator(fieldName = "agencyId", message = "missing required agencyId field")})
  public String agency() {
    ListBean<ServiceAlertBean> result = _transitDataService.getAllServiceAlertsForAgencyId(_agencyId);
    _situations = result.getList();
    return SUCCESS;
  }

  @Validations(requiredStrings = {@RequiredStringValidator(fieldName = "agencyId", message = "missing required agencyId field")})
  public String removeAllForAgency() {
    try {
      _transitDataService.removeAllServiceAlertsForAgencyId(_agencyId);
    } catch (RuntimeException e) {
      _log.error("Unable to remove all service alerts for agency", e);
      throw e;
    }
    return "redirect";
  }
  
}
