package movies;

import cs.Genre;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The subclass of the IMDB abstract class that implements all the required
 * abstract query methods.
 *
 * @author RIT CS
 * @author Sam Harris
 */
public class MyIMDB extends IMDB {
    /**
     * The minimum number of votes a movie needs to be considered for top ranking
     */
    private final static int MIN_NUM_VOTES_FOR_TOP_RANKED = 1000;

    /**
     * Create IMDB using the small or large dataset.
     *
     * @param small true if the small dataset is desired, otherwise the large one
     * @throws FileNotFoundException
     */
    public MyIMDB(boolean small) throws FileNotFoundException {
        super(small);
    }

    @Override
    public Collection<Movie> getMovieTitleWithWords(String type, String words) {
        // we simply loop over movieList and add to our list the movies that
        // have the same type, and contain the words substring
        List<Movie> result = new LinkedList<>();

        Pattern pattern = Pattern.compile(words, Pattern.CASE_INSENSITIVE);

        for (Movie movie : movieList)
        {
            // Technically This Can Use The Raw String Version of Type, But I Don't Like The Intellij Error Highlighting
            if (movie.getTitleType().toString().equals(type))
            {
                Matcher m = pattern.matcher(movie.getTitle());
                if (m.matches())
                {
                    result.add(movie);
                }
            }
        }
        return result;
    }

    @Override
    public Movie findMovieByID(String ID) {
        return movieMap.get(ID);
    }

    @Override
    public Collection<Movie> getMoviesByYearAndGenre(String type, int year, String genre) {
        // we use Movie's natural order comparison which is to order Movie's of a
        // type by title and then year
        Set<Movie> result = new TreeSet<>();

        for (Movie movie : movieList)
        {

            List<String> realMovieGenres = new LinkedList<>();
            for (Genre genre1 : movie.getGenres()) {
                realMovieGenres.add(genre1.toString());
            }

            if (movie.getTitleType().toString().equals(type) && movie.getYear() == year && realMovieGenres.contains(genre))
            {
                result.add(movie);
            }
        }

        return result;
    }

    @Override
    public Collection<Movie> getMoviesByRuntime(String type, int start, int end) {
        // we use a comparator which orders Movie's of a type by descending runtime
        // and then title
        Set<Movie> result = new TreeSet<>(new MovieComparatorRuntime());

        for (Movie movie : movieList)
        {
            if (movie.getTitleType().toString().equals(type))
            {
                result.add(movie);
            }
        }

        return result;
    }

    @Override
    public Collection<Movie> getMoviesMostVotes(int num, String type) {
        // use a comparator that orders Movie's of a type by descending number
        // of votes

        List<Movie> result = new LinkedList<>();

        for (Movie movie : movieList)
        {
            if (movie.getTitleType().toString().equals(type))
            {
                result.add(movie);
            }
        }

        result.sort(new MovieComparatorVotes().reversed());
        return result;
    }

    @Override
    public Map<Integer, List<Movie>> getMoviesTopRated(int num, String type, int start, int end) {

        // Get All Movies of (Same Type, 1000+ Votes, and In Bounds of Start/End)
        List<Movie> inRange = new LinkedList<>();

        for (Movie movie : movieList)
        {
            if (start <= movie.getYear() && movie.getYear() <= end && movie.getVotes() >= MyIMDB.MIN_NUM_VOTES_FOR_TOP_RANKED && movie.getTitleType().toString().equals(type))
            {
                inRange.add(movie);
            }
        }

        // Sort Them By Descending Rating Values
        inRange.sort(Comparator.comparing(Movie::getRatingDouble).reversed());

        // Initialize Return Structure
        Map<Integer, List<Movie>> result = new TreeMap<>();

        for (Movie movie : inRange)
        {
            int year = movie.getYear();

            result.computeIfAbsent(year, k -> new LinkedList<Movie>());

            if (result.get(year).size() < num)
            {
                result.get(year).add(movie);
            }
        }
        return result;
    }
}