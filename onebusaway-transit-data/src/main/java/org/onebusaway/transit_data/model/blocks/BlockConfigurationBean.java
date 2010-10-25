package org.onebusaway.transit_data.model.blocks;

import java.io.Serializable;
import java.util.List;

public final class BlockConfigurationBean implements Serializable {

  private static final long serialVersionUID = 1L;

  private List<String> activeServiceIds;

  private List<String> inactiveServiceIds;

  private List<BlockTripBean> trips;

  public List<String> getActiveServiceIds() {
    return activeServiceIds;
  }

  public void setActiveServiceIds(List<String> activeServiceIds) {
    this.activeServiceIds = activeServiceIds;
  }

  public List<String> getInactiveServiceIds() {
    return inactiveServiceIds;
  }

  public void setInactiveServiceIds(List<String> inactiveServiceIds) {
    this.inactiveServiceIds = inactiveServiceIds;
  }

  public List<BlockTripBean> getTrips() {
    return trips;
  }

  public void setTrips(List<BlockTripBean> trips) {
    this.trips = trips;
  }
}