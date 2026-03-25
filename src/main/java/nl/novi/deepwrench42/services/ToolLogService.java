package nl.novi.deepwrench42.services;

import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.tool.ToolRequestDTO;
import nl.novi.deepwrench42.dtos.tool.ToolResponseDTO;
import nl.novi.deepwrench42.dtos.toolLog.ToolLogResponseDTO;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.entities.ToolLogEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.ToolDTOMapper;
import nl.novi.deepwrench42.mappers.ToolLogDTOMapper;
import nl.novi.deepwrench42.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToolLogService {
    private final ToolLogRepository toolLogRepository;
    private final ToolLogDTOMapper toolLogDTOMapper;

    public ToolLogService(
            ToolLogRepository toolLogRepository,
            ToolLogDTOMapper toolLogDTOMapper) {
        this.toolLogRepository = toolLogRepository;
        this.toolLogDTOMapper = toolLogDTOMapper;
    }

    @Transactional
    public List<ToolLogResponseDTO> findAllToolLogs() {
        return toolLogDTOMapper.mapToDto(toolLogRepository.findAll());
    }

    @Transactional
    public ToolLogResponseDTO findToolLogById(Long id) {
        ToolLogEntity toolLogEntity = getToolLogEntity(id);
        return toolLogDTOMapper.mapToDto(toolLogEntity);
    }

    private ToolLogEntity getToolLogEntity(Long id) {
        return toolLogRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Tool Log " + id + " not found"));
    }
}