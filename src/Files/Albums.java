package Files;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is for creating albums.
 * This class also arrange the songs according to last access to the file.
 * This class uses mp3agic library.
 * @author Mohammad tavakoli & Amir saadatmand
 *
 */

public class Albums {

    private HashMap<String, ArrayList<String>> allAlbums;
    private ArrayList<String> songAdresses;

    /**
     * Constructor for Album class
     */
    public Albums(ArrayList<String> songAdresses) {
        allAlbums = makeAlbums(songAdresses);
    }

    /**
     *
     *
     * @return HashMap of albums
     */
    public HashMap<String, ArrayList<String>> getAllAlbums() {
        return allAlbums;
    }

    /**
     *
     *
     * @return Arraylist of songs addrreses
     */
    public ArrayList<String> getSongAdresses() {
        return songAdresses;
    }

    /**
     *
     *
     * @return HashMap of new album
     */

    public HashMap<String, ArrayList<String>> makeAlbums(ArrayList<String> songAdresses) {

        Mp3File mp3File = null;
        ID3v2 song = null;
        HashMap<String, ArrayList<String>> allAlbums = new HashMap<>();

        for (String songAdress : songAdresses) {

            try {
                mp3File = new Mp3File(songAdress);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedTagException e) {
                e.printStackTrace();
            } catch (InvalidDataException e) {
                e.printStackTrace();
            }

            song = mp3File.getId3v2Tag();
            String albumNameOfSong = song.getAlbum();

            if (albumNameOfSong != null) {
                if ( allAlbums.containsKey(albumNameOfSong)) {
                    for (String s : allAlbums.keySet()) {
                        if (s.equals(albumNameOfSong)) {
                            allAlbums.get(s).add(songAdress);
                        }
                    }
                } else {
                    allAlbums.put(albumNameOfSong, new ArrayList<>());
                    for (String s : allAlbums.keySet()) {
                        if (s.equals(albumNameOfSong)) {
                            allAlbums.get(s).add(songAdress);
                        }
                    }
                }
            }
        }
        return allAlbums;
    }
}