package nl.novi.deepwrench42.entities;

public enum ToolStatus {
    UNKNOWN,
    CHECKED_IN,
    CHECKED_OUT,
    CALIBRATION_DUE,
    IN_CALIBRATION,
    MAINTENANCE_DUE,
    IN_MAINTENANCE,
    UNSERVICEABLE,
    SCRAPPED
}