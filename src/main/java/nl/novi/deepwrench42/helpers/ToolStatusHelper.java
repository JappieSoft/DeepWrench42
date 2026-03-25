package nl.novi.deepwrench42.helpers;

import nl.novi.deepwrench42.entities.EquipmentEntity;
import nl.novi.deepwrench42.entities.EquipmentStatus;
import org.springframework.stereotype.Component;

@Component
public class ToolStatusHelper {

    public void validateForCheckOut(EquipmentEntity equipment) {
        EquipmentStatus status = equipment.getStatus();
        switch (status) {
            case UNKNOWN:
                throw new IllegalStateException("Equipment status unknown");
            case CHECKED_OUT:
                throw new IllegalStateException("Equipment already checked out");
            case CALIBRATION_DUE:
                throw new IllegalStateException("Equipment calibration is due!");
            case IN_CALIBRATION:
                throw new IllegalStateException("Equipment away for calibration");
            case MAINTENANCE_DUE:
                throw new IllegalStateException("Equipment maintenance is due!");
            case IN_MAINTENANCE:
                throw new IllegalStateException("Equipment away for maintenance");
            case UNSERVICEABLE:
                throw new IllegalStateException("Equipment unserviceable");
            case MISSING:
                throw new IllegalStateException("Equipment missing");
            case SCRAPPED:
                throw new IllegalStateException("Equipment scrapped");
            case CHECKED_IN:
                return;
            default:
                throw new IllegalStateException("Equipment status details: " + status);
        }
    }

    public void validateForCheckIn(EquipmentEntity equipment) {
        EquipmentStatus status = equipment.getStatus();
        switch (status) {
            case CHECKED_IN:
                throw new IllegalStateException("Equipment already checked in");
            case CALIBRATION_DUE:
                throw new IllegalStateException("Equipment calibration is due!");
            case IN_CALIBRATION:
                throw new IllegalStateException("Equipment away for calibration");
            case MAINTENANCE_DUE:
                throw new IllegalStateException("Equipment maintenance is due!");
            case IN_MAINTENANCE:
                throw new IllegalStateException("Equipment away for maintenance");
            case UNSERVICEABLE:
                throw new IllegalStateException("Equipment unserviceable");
            case SCRAPPED:
                throw new IllegalStateException("Equipment scrapped");
            case UNKNOWN:
                throw new IllegalStateException("Equipment UNKNOWN, please complete data entry");
            case CHECKED_OUT, MISSING:
                return;
            default:
                throw new IllegalStateException("Equipment status details: " + status);
        }
    }
}