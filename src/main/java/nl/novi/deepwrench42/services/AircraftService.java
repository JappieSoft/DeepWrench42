package nl.novi.deepwrench42.services;

import jakarta.transaction.Transactional;
import nl.novi.deepwrench42.dtos.aircraft.AircraftRequestDTO;
import nl.novi.deepwrench42.dtos.aircraft.AircraftResponseDTO;
import nl.novi.deepwrench42.entities.AircraftEntity;
import nl.novi.deepwrench42.entities.AircraftTypeEntity;
import nl.novi.deepwrench42.entities.EngineTypeEntity;
import nl.novi.deepwrench42.exceptions.RecordNotFoundException;
import nl.novi.deepwrench42.mappers.AircraftDTOMapper;
import nl.novi.deepwrench42.repository.AircraftRepository;
import nl.novi.deepwrench42.repository.AircraftTypeRepository;
import nl.novi.deepwrench42.repository.EngineTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AircraftService {

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

    @Transactional
    public List<AircraftResponseDTO> findAllAircraft() {
        return aircraftDTOMapper.mapToDto(aircraftRepository.findAll());
    }

    @Transactional
    public AircraftResponseDTO findAircraftById(Long id) {
        AircraftEntity aircraftEntity = getAircraftEntity(id);
        return aircraftDTOMapper.mapToDto(aircraftEntity);
    }

    @Transactional
    public AircraftResponseDTO createAircraft(AircraftRequestDTO model) {
        AircraftEntity aircraftEntity = aircraftDTOMapper.mapToEntity(model);

        AircraftTypeEntity type = aircraftTypeRepository.findById(model.getAircraftTypeId())
                .orElseThrow(() -> new RecordNotFoundException("Aircraft Type " + model.getAircraftTypeId() + " not found"));
        aircraftEntity.setAircraftType(type);

        EngineTypeEntity engine = engineTypeRepository.findById(model.getEngineTypeId())
                .orElseThrow(() -> new RecordNotFoundException("Engine Type " + model.getEngineTypeId() + " not found"));
        aircraftEntity.setEngineType(engine);


        aircraftEntity = aircraftRepository.save(aircraftEntity);
        return aircraftDTOMapper.mapToDto(aircraftEntity);
    }

    @Transactional
    public AircraftResponseDTO updateAircraft(Long id, AircraftRequestDTO requestDto) {
        AircraftEntity existingEntity = getAircraftEntity(id);
        boolean aircraftTypeDifferent = !Objects.equals(existingEntity.getAircraftType().getId(), requestDto.getAircraftTypeId());
        boolean engineTypeDifferent = !Objects.equals(existingEntity.getEngineType().getId(), requestDto.getEngineTypeId());

        existingEntity.setShipNumber(requestDto.getShipNumber());
        existingEntity.setRegistration(requestDto.getRegistration());

        if (aircraftTypeDifferent) {
            AircraftTypeEntity type = aircraftTypeRepository.findById(requestDto.getAircraftTypeId())
                    .orElseThrow(() -> new RecordNotFoundException("Aircraft Type " + requestDto.getAircraftTypeId() + " not found"));
            existingEntity.setAircraftType(type);
        }

        if (engineTypeDifferent) {
            EngineTypeEntity engine = engineTypeRepository.findById(requestDto.getEngineTypeId())
                    .orElseThrow(() -> new RecordNotFoundException("Engine Type " + requestDto.getEngineTypeId() + " not found"));
            existingEntity.setEngineType(engine);
        }

        existingEntity = aircraftRepository.save(existingEntity);
        return aircraftDTOMapper.mapToDto(existingEntity);
    }

    @Transactional
    public void deleteAircraft(Long id) {
        AircraftEntity aircraft = getAircraftEntity(id);

        aircraft.setAircraftType(null);
        aircraft.setEngineType(null);
        aircraftRepository.deleteById(id);
    }

    //Generic FIndById Helper
    private AircraftEntity getAircraftEntity(Long id) {
        AircraftEntity existingAircraftEntity = aircraftRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Aircraft " + id + " not found"));
        return existingAircraftEntity;
    }
}