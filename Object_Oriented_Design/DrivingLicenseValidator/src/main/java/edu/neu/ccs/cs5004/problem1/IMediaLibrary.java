package edu.neu.ccs.cs5004.problem1;

import java.util.List;

/**
 * An interface that contains operations that will be supported by a Media Library.
 */

public interface IMediaLibrary {

  /**
   * When given a director's name, this method returns the list of movies of the director. It is
   * arranged in order of most recent to least recent movie.
   *
   * @param director name of director.
   * @return list of movies of that director sorted appropriately.
   */
  List<MultiMedia> getMediaByDirector(String director);

  /**
   * This method returns the title of the most streamed multimedia.
   *
   * @return the title in form of string.
   */

  String returnMostStreamed();

  /**
   * For a given alias, it returns the number of times the media asssociated with that alias is
   * streamed.
   *
   * @param alias alias in form of int.
   * @return number of times media is streamed.
   */
  int streamingStatistic(Integer alias);

  /**
   * For every time a multimedia is asked for playing, request for that is updated
   *
   * @param alias alias of movie/TV series given as input.
   */

  void updateRequest(Integer alias);

  /**
   * Adds media to library if the alias is not present already.
   *
   * @param media movie/TV series to be added.
   * @throws DuplicateFoundException if the alias already exists.
   */

  void addMediaToLibrary(MultiMedia media) throws DuplicateFoundException;

}
