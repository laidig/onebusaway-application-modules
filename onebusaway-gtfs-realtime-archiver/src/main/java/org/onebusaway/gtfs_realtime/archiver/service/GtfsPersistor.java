/**
 * Copyright (C) 2015 Cambridge Systematics, Inc.
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
package org.onebusaway.gtfs_realtime.archiver.service;

import org.onebusaway.gtfs_realtime.archiver.model.LinkAVLData;
import org.onebusaway.gtfs_realtime.model.AlertModel;
import org.onebusaway.gtfs_realtime.model.TripUpdateModel;
import org.onebusaway.gtfs_realtime.model.VehiclePositionModel;

public interface GtfsPersistor {

  void persist(TripUpdateModel tripUpdate);

  void persist(VehiclePositionModel vehiclePosition);

  void persist(AlertModel alert);

  void persist(LinkAVLData linkAvlData);
}
