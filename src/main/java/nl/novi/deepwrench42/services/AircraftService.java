package nl.novi.deepwrench42.services;

import nl.novi.deepwrench42.dtos.aircraft.AircraftRequestDTO;
import nl.novi.deepwrench42.dtos.aircraft.AircraftResponseDTO;
import nl.novi.deepwrench42.entities.AircraftEntity;
import nl.novi.deepwrench42.entities.AircraftTypeEntity;
import nl.novi.deepwrench42.entities.EngineTypeEntity;
import nl.novi.deepwrench42.mappers.AircraftDTOMapper;
import nl.novi.deepwrench42.repository.AircraftRepository;
import nl.novi.deepwrench42.repository.AircraftTypeRepository;
import nl.novi.deepwrench42.repository.EngineTypeRepository;

import java.util.List;

public class AircraftService{

    private final AircraftRepository aircraftRepository;
    private final AircraftTypeRepository aircraftTypeRepository;
    private final EngineTypeRepository engineTypeRepository;
    private final AircraftDTOMapper aircraftDTOMapper;


    public AircraftService(AircraftRepository aircraftRepository, AircraftTypeRepository aircraftTypeRepository, EngineTypeRepository engineTypeRepository, AircraftDTOMapper aircraftDTOMapper) {
        this.aircraftRepository = aircraftRepository;
        this.aircraftTypeRepository = aircraftTypeRepository;
        this.engineTypeRepository = engineTypeRepository;
        this.aircraftDTOMapper = aircraftDTOMapper;
    }

    public List<AircraftResponseDTO> findAllAircrafts() {
        return aircraftDTOMapper.mapToDto(aircraftRepository.findAll());
    }

    public AircraftResponseDTO findAircraftById(Long id) {
        AircraftEntity aircraftEntity = getAircraftEntity(id);
        return aircraftDTOMapper.mapToDto(aircraftEntity);
    }

    public AircraftResponseDTO createAircraft(AircraftRequestDTO model) {
        AircraftEntity aircraftEntity = aircraftDTOMapper.mapToEntity(model);
        aircraftEntity = aircraftRepository.save(aircraftEntity);
        return aircraftDTOMapper.mapToDto(aircraftEntity);
    }

    public AircraftResponseDTO updateAircraft(Long id, AircraftRequestDTO requestDto) {
        AircraftEntity existingEntity = getAircraftEntity(id);
        AircraftTypeEntity aircraftType = aircraftTypeRepository.getReferenceById(requestDto.getAircraftTypeId());
        EngineTypeEntity engineType = engineTypeRepository.getReferenceById(requestDto.getEngineTypeId());

        existingEntity.setShipNumber(requestDto.getShipNumber());
        existingEntity.setRegistration(requestDto.getRegistration());
        existingEntity.setAircraftType(aircraftType);
        existingEntity.setEngineType(engineType);

        existingEntity = aircraftRepository.save(existingEntity);
        return aircraftDTOMapper.mapToDto(existingEntity);
    }


    private AircraftEntity getAircraftEntity(Long id) {
        AircraftEntity existingAircraftEntity = aircraftRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Aircraft " + id +" not found"));
        return existingAircraftEntity;
    }

    public void deleteAircraft(Long id) {
        AircraftEntity aircraft = getAircraftEntity(id);
        aircraftRepository.deleteById(id);
    }
}