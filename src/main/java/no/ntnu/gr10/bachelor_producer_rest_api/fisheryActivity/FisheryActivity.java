package no.ntnu.gr10.bachelor_producer_rest_api.fisheryActivity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import no.ntnu.gr10.bachelor_producer_rest_api.company.Company;

import java.time.LocalDateTime;

@Entity
@Table(name = "fishery_activities")
@Schema(
        name        = "FisheryActivityEntity",
        description = "JPA entity for a fishery activity"
)
public class FisheryActivity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Primary key of the activity", example = "123")
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "company_id", nullable = false)
  @Schema(description = "Owning company", hidden = true)
  private Company company;

  @Column(name = "setup_date_time", nullable = false)
  @Schema(
          description = "When this activity was set up",
          example     = "2025-04-20T01:47:10"
  )
  private LocalDateTime setupDateTime;

  @Column(name = "tool_type_code", length = 50, nullable = false)
  @Schema(
          description = "Tool type code (e.g. NETS)",
          example     = "NETS"
  )
  private String toolTypeCode;

  @Column(name = "tool_type_name", length = 100, nullable = false)
  @Schema(
          description = "Tool type name (human‑readable)",
          example     = "Nets"
  )
  private String toolTypeName;

  @Column(name = "tool_id", length = 36, nullable = false)
  @Schema(
          description = "UUID of the actual tool",
          example     = "31e47253-6d8a-4060-942b-aec5ad0d4d12"
  )
  private String toolId;

  @Column(name = "removed_date_time")
  @Schema(
          description = "When the tool was removed (nullable)",
          example     = "2025-04-20T02:30:00"
  )
  private LocalDateTime removedDateTime;

  @Column(name = "last_changed_date_time")
  @Schema(
          description = "Last time this record was changed",
          example     = "2025-04-20T02:14:26"
  )
  private LocalDateTime lastChangedDateTime;

  @Column(name = "starting_point_lat", nullable = false)
  @Schema(
          description = "Latitude of the starting point",
          example     = "58.07933"
  )
  private Double startingPointLat;

  @Column(name = "starting_point_lon", nullable = false)
  @Schema(
          description = "Longitude of the starting point",
          example     = "3.60231"
  )
  private Double startingPointLon;

  @Column(name = "length")
  @Schema(
          description = "Length of the activity in meters",
          example     = "5321.06"
  )
  private Double length;

  @Column(name = "geometry", columnDefinition = "TEXT")
  @Schema(
          description = "Raw WKT geometry of the fishing path",
          example     = "LINESTRING(3.60231 58.07933,3.61934 58.07934,...)"
  )
  private String geometry;

  public FisheryActivity() {}

  public Long getId() {
    return id;
  }

  public Company getCompany() {
    return company;
  }
  public void setCompany(Company company) {
    if (company == null) throw new IllegalArgumentException("Company cannot be null");
    this.company = company;
  }

  public LocalDateTime getSetupDateTime() {
    return setupDateTime;
  }
  public void setSetupDateTime(LocalDateTime setupDateTime) {
    this.setupDateTime = setupDateTime;
  }

  public String getToolTypeCode() {
    return toolTypeCode;
  }
  public void setToolTypeCode(String toolTypeCode) {
    this.toolTypeCode = toolTypeCode;
  }

  public String getToolTypeName() {
    return toolTypeName;
  }
  public void setToolTypeName(String toolTypeName) {
    this.toolTypeName = toolTypeName;
  }

  public String getToolId() {
    return toolId;
  }
  public void setToolId(String toolId) {
    this.toolId = toolId;
  }

  public LocalDateTime getRemovedDateTime() {
    return removedDateTime;
  }
  public void setRemovedDateTime(LocalDateTime removedDateTime) {
    this.removedDateTime = removedDateTime;
  }

  public LocalDateTime getLastChangedDateTime() {
    return lastChangedDateTime;
  }
  public void setLastChangedDateTime(LocalDateTime lastChangedDateTime) {
    this.lastChangedDateTime = lastChangedDateTime;
  }

  public Double getStartingPointLat() {
    return startingPointLat;
  }
  public void setStartingPointLat(Double startingPointLat) {
    this.startingPointLat = startingPointLat;
  }

  public Double getStartingPointLon() {
    return startingPointLon;
  }
  public void setStartingPointLon(Double startingPointLon) {
    this.startingPointLon = startingPointLon;
  }

  public Double getLength() {
    return length;
  }
  public void setLength(Double length) {
    this.length = length;
  }

  public String getGeometry() {
    return geometry;
  }
  public void setGeometry(String geometry) {
    this.geometry = geometry;
  }

  @Override
  public String toString() {
    return "FisheryActivity{" +
            "id=" + id +
            ", company=" + company.getId() +
            ", setupDateTime=" + setupDateTime +
            ", toolTypeCode='" + toolTypeCode + '\'' +
            ", toolTypeName='" + toolTypeName + '\'' +
            ", toolId='" + toolId + '\'' +
            ", removedDateTime=" + removedDateTime +
            ", lastChangedDateTime=" + lastChangedDateTime +
            ", startingPointLat=" + startingPointLat +
            ", startingPointLon=" + startingPointLon +
            ", length=" + length +
            ", geometry='" + geometry + '\'' +
            '}';
  }
}