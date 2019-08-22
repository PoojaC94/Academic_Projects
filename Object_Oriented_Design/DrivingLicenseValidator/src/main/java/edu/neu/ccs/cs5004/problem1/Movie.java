package edu.neu.ccs.cs5004.problem1;

import java.util.List;

public class Movie extends MultiMedia {

  public Movie(String title, int year, List<Director> directorsList, List<Actors> actorsList) {
    super(title, year, directorsList, actorsList);
  }
}
