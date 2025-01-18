package com.mingyang.busmanagementsystem.service.polylinePlot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingyang.busmanagementsystem.model.dto.request.polylinePlot.CreatePolylinePlotRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.polylinePlot.GetPolylinePlotListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.polylinePlot.UpdatePolylinePlotRequestDto;
import com.mingyang.busmanagementsystem.model.entity.PolylinePlot;
import com.mingyang.busmanagementsystem.repository.PolylinePlotRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class PolylinePlotServiceImpl implements PolylinePlotService {

    private final PolylinePlotRepository polylinePlotRepository;

    @Autowired
    public PolylinePlotServiceImpl(PolylinePlotRepository polylinePlotRepository) {
        this.polylinePlotRepository = polylinePlotRepository;
    }

    @Override
    public List<PolylinePlot> getPolylinePlotList(GetPolylinePlotListRequestDto requestDto) {
        List<PolylinePlot> polylinePlots = polylinePlotRepository.findAll();

        if (requestDto != null) {
            return polylinePlots.stream()
                    .filter(polylinePlot -> (requestDto.getPolylineData() == null ||
                            requestDto.getPolylineData().equals(polylinePlot.getPolylineData())))
                    .collect(Collectors.toList());
        }

        return polylinePlots;
    }

    @Override
    public PolylinePlot getPolylinePlotById(Long id) {
        return polylinePlotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Polyline plot not found with id: " + id));
    }

    @Override
    public PolylinePlot createPolylinePlot(CreatePolylinePlotRequestDto requestDto) {

        // validate the polyline data
        if (requestDto.getPolylineData() == null || requestDto.getPolylineData().trim().isEmpty()) {
            throw new IllegalArgumentException("Polyline data cannot be empty");
        }

        // validate the polyline data format
        if (!requestDto.getPolylineData().matches("\\d+,\\d+;\\d+,\\d+;\\d+,\\d+;.*")) {
            throw new IllegalArgumentException("Invalid polyline data format");
        }

        // validate the polyline data length
        if (requestDto.getPolylineData().split(";").length < 2) {
            throw new IllegalArgumentException("Polyline data must contain at least two points");
        }

        PolylinePlot polylinePlot = new PolylinePlot();
        polylinePlot.setPolylineData(requestDto.getPolylineData());
        polylinePlot.setCreatedAt(LocalDateTime.now());
        polylinePlot.setUpdatedAt(LocalDateTime.now());
        return polylinePlotRepository.save(polylinePlot);
    }

    @Override
    @Transactional
    public PolylinePlot updatePolylinePlot(Long id, UpdatePolylinePlotRequestDto requestDto) {

        // validate the polyline data
        if (requestDto.getPolylineData() == null || requestDto.getPolylineData().trim().isEmpty()) {
            throw new IllegalArgumentException("Polyline data cannot be empty");
        }

        // validate the polyline data format
        if (!requestDto.getPolylineData().matches("\\d+,\\d+;\\d+,\\d+;\\d+,\\d+;.*")) {
            throw new IllegalArgumentException("Invalid polyline data format");
        }

        // validate the polyline data length
        if (requestDto.getPolylineData().split(";").length < 2) {
            throw new IllegalArgumentException("Polyline data must contain at least two points");
        }

        PolylinePlot polylinePlot = polylinePlotRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Polyline plot not found with id: " + id));
        polylinePlot.setPolylineData(requestDto.getPolylineData());
        polylinePlot.setUpdatedAt(LocalDateTime.now());
        return polylinePlotRepository.save(polylinePlot);
    }

    @Override
    @Transactional
    public void deletePolylinePlot(Long id) {
        PolylinePlot polylinePlot = polylinePlotRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Polyline plot not found with id: " + id));
        polylinePlot.setUpdatedAt(LocalDateTime.now());
        polylinePlotRepository.delete(polylinePlot);
    }
}