package com.mingyang.busmanagementsystem.service.tripRecord;

import com.mingyang.busmanagementsystem.model.dto.request.tripRecord.CreateTripRecordRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.tripRecord.GetTripRecordListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.tripRecord.UpdateTripRecordRequestDto;
import com.mingyang.busmanagementsystem.model.entity.TripRecord;
import java.util.List;

public interface TripRecordService {
    List<TripRecord> getTripRecordList(GetTripRecordListRequestDto requestDto);

    TripRecord getTripRecordById(Long id);

    TripRecord createTripRecord(CreateTripRecordRequestDto requestDto);

    TripRecord updateTripRecord(Long id, UpdateTripRecordRequestDto requestDto);

    void deleteTripRecord(Long id);
} 