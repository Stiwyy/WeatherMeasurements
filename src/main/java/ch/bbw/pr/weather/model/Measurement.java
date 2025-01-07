package ch.bbw.pr.weather.model;

import lombok.Getter;
import lombok.Setter;

//Automatisch Setter + Getter (spahrt Zeit + ist übersichtlicher)
@Getter
@Setter
public class Measurement {
    private String kind;
    private double value;
}
