package ch.bbw.pr.weather.model;


import java.util.List;
import lombok.Setter;
import lombok.Getter;

//Automatisch Setter + Getter (spahrt Zeit + ist übersichtlicher)
@Getter
@Setter
public class WeatherMeasurement {
    private String type;
    private String timestamp;
    private Station station;
    private List<Measurement> measurements;
}
