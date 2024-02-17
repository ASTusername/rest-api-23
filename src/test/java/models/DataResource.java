package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DataResource {
    int id;
    String name;
    int year;
    String color;
    @JsonProperty("pantone_value")
    String pantoneValue;
}
