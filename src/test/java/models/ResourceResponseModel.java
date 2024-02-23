package models;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
public class ResourceResponseModel {
    DataResource data;
    Support support;
}
