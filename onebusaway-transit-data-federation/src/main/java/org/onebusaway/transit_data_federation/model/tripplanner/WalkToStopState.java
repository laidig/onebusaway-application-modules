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
package org.onebusaway.transit_data_federation.model.tripplanner;

import org.onebusaway.transit_data_federation.services.transit_graph.StopEntry;

public class WalkToStopState extends AtStopState {

  public WalkToStopState(long currentTime, StopEntry stop) {
    super(currentTime, stop);
  }

  public WalkToStopState shift(long offset) {
    return new WalkToStopState(getCurrentTime() + offset, getStop());
  }

  @Override
  public String toString() {
    return "WalkToStop(ts=" + getCurrentTimeString() + " stop=" + getStop() + ")";
  }
}
