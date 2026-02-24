/*
package nl.novi.deepwrench42.services;

import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.equipement.EquipmentResponseDTO;
import nl.novi.deepwrench42.dtos.tool.ToolRequestDTO;
import nl.novi.deepwrench42.dtos.toolKit.ToolKitRequestDTO;
import nl.novi.deepwrench42.entities.ToolEntity;
import nl.novi.deepwrench42.entities.ToolKitEntity;
import nl.novi.deepwrench42.mappers.EquipmentDTOMapper;
import nl.novi.deepwrench42.mappers.ToolDTOMapper;
import nl.novi.deepwrench42.mappers.ToolKitDTOMapper;
import nl.novi.deepwrench42.repository.ToolKitRepository;
import nl.novi.deepwrench42.repository.ToolRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EquipmentService {
    private final ToolDTOMapper toolDTOMapper;
    private final ToolRepository toolRepository;
    private final EquipmentDTOMapper equipmentDTOMapper;
    private final ToolKitDTOMapper toolKitDTOMapper;
    private final ToolKitRepository toolKitRepository;

    public EquipmentService(ToolDTOMapper toolDTOMapper, ToolRepository toolRepository, EquipmentDTOMapper equipmentDTOMapper, ToolKitDTOMapper toolKitDTOMapper, ToolKitRepository toolKitRepository) {
        this.toolDTOMapper = toolDTOMapper;
        this.toolRepository = toolRepository;
        this.equipmentDTOMapper = equipmentDTOMapper;
        this.toolKitDTOMapper = toolKitDTOMapper;
        this.toolKitRepository = toolKitRepository;
    }

    public EquipmentResponseDTO createTool(ToolRequestDTO dto) {
        ToolEntity tool = toolDTOMapper.mapToEntity(dto);
        ToolEntity saved = toolRepository.save(tool);
        return equipmentDTOMapper.mapToDto(saved);
    }

    public EquipmentResponseDTO createToolKit(ToolKitRequestDTO dto) {
        ToolKitEntity kit = toolKitDTOMapper.mapToEntity(dto);
        ToolKitEntity saved = toolKitRepository.save(kit);
        return equipmentDTOMapper.mapToDto(saved);
    }
}
*/
