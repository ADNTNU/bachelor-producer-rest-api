package no.ntnu.gr10.bachelor_producer_rest_api.fishingFacility;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.gr10.bachelor_producer_rest_api.company.Company;

import java.time.LocalDateTime;

@Entity
@Table(name = "fishing_facility")
@Schema(
        name        = "FishingFacility",
        description = "JPA entity for a Fishing Facility"
)
public class FishingFacility {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Primary key", example = "1")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "company_id", nullable = false)
  @Schema(description = "Owning company", hidden = true)
  private Company company;

  @Column(name = "type", nullable = false)
  @Schema(description = "GeoJSON type", example = "Feature")
  private String type;

  @Column(name = "bbox", columnDefinition = "TEXT")
  @Schema(
          description = "Bounding box as JSON array",
          example     = "[26.3533333,71.4233333,26.3533333,71.4233333]"
  )
  private String bbox;

  @Column(name = "geometry", columnDefinition = "TEXT", nullable = false)
  @Schema(
          description = "Geometry object as JSON",
          example     = "{\"type\":\"Point\",\"coordinates\":[26.3533333,71.4233333]}"
  )
  private String geometry;

  // Properties
  @Column(name = "version", nullable = false)
  @Schema(description = "Version of the record", example = "0")
  private Integer version;

  @Column(name = "vessel_name")
  @Schema(description = "Name of the vessel (nullable)")
  private String vesselName;

  @Column(name = "vessel_phone")
  @Schema(description = "Phone number of the vessel (nullable)")
  private String vesselPhone;

  @Column(name = "tool_type_code", length = 50, nullable = false)
  @Schema(description = "Tool type code", example = "UNK")
  private String toolTypeCode;

  @Column(name = "setup_datetime", nullable = false)
  @Schema(description = "Setup date/time", example = "2020-01-28T20:29:00Z")
  private LocalDateTime setupDateTime;

  @Column(name = "tool_id", length = 36, nullable = false)
  @Schema(description = "UUID of the tool", example = "1942a413-8416-4c33-9432-6cec19f21736")
  private String toolId;

  @Column(name = "ircs")
  @Schema(description = "International Radio Call Sign (nullable)")
  private String ircs;

  @Column(name = "mmsi")
  @Schema(description = "Maritime Mobile Service Identity (nullable)")
  private String mmsi;

  @Column(name = "imo")
  @Schema(description = "IMO number (nullable)")
  private String imo;

  @Column(name = "vessel_email")
  @Schema(description = "Email of vessel contact (nullable)")
  private String vesselEmail;

  @Column(name = "tool_type_name", length = 100, nullable = false)
  @Schema(description = "Tool type name", example = "Unknown")
  private String toolTypeName;

  @Column(name = "tool_color", length = 7)
  @Schema(description = "Color code of the tool (nullable)", example = "#000000")
  private String toolColor;

  @Column(name = "source")
  @Schema(description = "Data source (nullable)")
  private String source;

  @Column(name = "comment", columnDefinition = "TEXT")
  @Schema(description = "Comment or notes (nullable)")
  private String comment;

  @Column(name = "removed_datetime")
  @Schema(description = "Removal date/time (nullable)")
  private LocalDateTime removedDateTime;

  @Column(name = "last_changed_datetime")
  @Schema(description = "Last change date/time (nullable)", example = "2020-01-28T20:31:24Z")
  private LocalDateTime lastChangedDateTime;

  @Column(name = "last_changed_by_source")
  @Schema(description = "Source of last change (nullable)")
  private String lastChangedBySource;

  @Column(name = "reg_num")
  @Schema(description = "Registration number (nullable)")
  private String regNum;

  @Column(name = "sbr_reg_num")
  @Schema(description = "SBR registration number (nullable)")
  private String sbrRegNum;

  @Column(name = "setup_processed_time")
  @Schema(description = "Setup processed time (nullable)")
  private LocalDateTime setupProcessedTime;

  @Column(name = "removed_processed_time")
  @Schema(description = "Removed processed time (nullable)")
  private LocalDateTime removedProcessedTime;

  @Column(name = "tool_count")
  @Schema(description = "Count of tools deployed (nullable)")
  private Integer toolCount;

  public FishingFacility(){
    //Default JPA method
  }

  public Long getId() { return id; }

  public Company getCompany() {
    return company;
  }
  public void setCompany(Company company) {
    if (company == null) throw new IllegalArgumentException("Company cannot be null");
    this.company = company;
  }

  public String getType() { return type; }
  public void setType(String type) { this.type = type; }

  public String getBbox() { return bbox; }
  public void setBbox(String bbox) { this.bbox = bbox; }

  public String getGeometry() { return geometry; }
  public void setGeometry(String geometry) { this.geometry = geometry; }

  public Integer getVersion() { return version; }
  public void setVersion(Integer version) { this.version = version; }

  public String getVesselName() { return vesselName; }
  public void setVesselName(String vesselName) { this.vesselName = vesselName; }

  public String getVesselPhone() { return vesselPhone; }
  public void setVesselPhone(String vesselPhone) { this.vesselPhone = vesselPhone; }

  public String getToolTypeCode() { return toolTypeCode; }
  public void setToolTypeCode(String toolTypeCode) { this.toolTypeCode = toolTypeCode; }

  public LocalDateTime getSetupDateTime() { return setupDateTime; }
  public void setSetupDateTime(LocalDateTime setupDateTime) { this.setupDateTime = setupDateTime; }

  public String getToolId() { return toolId; }
  public void setToolId(String toolId) { this.toolId = toolId; }

  public String getIrcs() { return ircs; }
  public void setIrcs(String ircs) { this.ircs = ircs; }

  public String getMmsi() { return mmsi; }
  public void setMmsi(String mmsi) { this.mmsi = mmsi; }

  public String getImo() { return imo; }
  public void setImo(String imo) { this.imo = imo; }

  public String getVesselEmail() { return vesselEmail; }
  public void setVesselEmail(String vesselEmail) { this.vesselEmail = vesselEmail; }

  public String getToolTypeName() { return toolTypeName; }
  public void setToolTypeName(String toolTypeName) { this.toolTypeName = toolTypeName; }

  public String getToolColor() { return toolColor; }
  public void setToolColor(String toolColor) { this.toolColor = toolColor; }

  public String getSource() { return source; }
  public void setSource(String source) { this.source = source; }

  public String getComment() { return comment; }
  public void setComment(String comment) { this.comment = comment; }

  public LocalDateTime getRemovedDateTime() { return removedDateTime; }
  public void setRemovedDateTime(LocalDateTime removedDateTime) { this.removedDateTime = removedDateTime; }

  public LocalDateTime getLastChangedDateTime() { return lastChangedDateTime; }
  public void setLastChangedDateTime(LocalDateTime lastChangedDateTime) { this.lastChangedDateTime = lastChangedDateTime; }

  public String getLastChangedBySource() { return lastChangedBySource; }
  public void setLastChangedBySource(String lastChangedBySource) { this.lastChangedBySource = lastChangedBySource; }

  public String getRegNum() { return regNum; }
  public void setRegNum(String regNum) { this.regNum = regNum; }

  public String getSbrRegNum() { return sbrRegNum; }
  public void setSbrRegNum(String sbrRegNum) { this.sbrRegNum = sbrRegNum; }

  public LocalDateTime getSetupProcessedTime() { return setupProcessedTime; }
  public void setSetupProcessedTime(LocalDateTime setupProcessedTime) { this.setupProcessedTime = setupProcessedTime; }

  public LocalDateTime getRemovedProcessedTime() { return removedProcessedTime; }
  public void setRemovedProcessedTime(LocalDateTime removedProcessedTime) { this.removedProcessedTime = removedProcessedTime; }

  public Integer getToolCount() { return toolCount; }
  public void setToolCount(Integer toolCount) { this.toolCount = toolCount; }

  @Override
  public String toString() {
    return "GeoJsonFeature{" +
            "id=" + id +
            ", type='" + type + '\'' +
            ", bbox='" + bbox + '\'' +
            ", geometry='" + geometry + '\'' +
            ", version=" + version +
            '}';
  }

}
