package com.mingyang.busmanagementsystem.service.polylinePlot;

import com.mingyang.busmanagementsystem.model.dto.request.polylinePlot.CreatePolylinePlotRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.polylinePlot.GetPolylinePlotListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.polylinePlot.UpdatePolylinePlotRequestDto;
import com.mingyang.busmanagementsystem.model.entity.PolylinePlot;
import java.util.List;

public interface PolylinePlotService {
    List<PolylinePlot> getPolylinePlotList(GetPolylinePlotListRequestDto requestDto);

    PolylinePlot getPolylinePlotById(Long id);

    PolylinePlot createPolylinePlot(CreatePolylinePlotRequestDto requestDto);

    PolylinePlot updatePolylinePlot(Long id, UpdatePolylinePlotRequestDto requestDto);

    void deletePolylinePlot(Long id);
} 