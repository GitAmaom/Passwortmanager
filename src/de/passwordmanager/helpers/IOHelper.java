package de.passwordmanager.helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.concurrent.Task;
/**
 * Helper class for IO-operations.
 * @author Moritz Schneider
 *
 */
public class IOHelper {

    /**
     * The relative path leading to the resources.data package.
     */
    public static final String DATA_PATH = "/de/passwordmanager/resources/data/";
    /**
     * The relative path leading to the resources.css package.
     */
    public static final String CSS_PATH = "/de/passwordmanager/resources/css/";
    /**
     * The relative path leading to the resources.fxml package.
     */
    public static final String FXML_PATH = "/de/passwordmanager/resources/FXML/";
    /**
     * The relative path leading to the resources.img package.
     */
    public static final String IMG_PATH = "/de/passwordmanager/resources/FXML/";
    
    /**
     * Reads all lines of a file and puts them in an ArrayList of Strings.
     * @param file the file to be read.
     * @return an ArrayList containing all lines of the file.
     * @throws IOException if the file doesn't exist.
     */
    public static ArrayList<String> fileToArrayList(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<String> list = new ArrayList<String>();
        while(reader.ready()) {
            list.add(reader.readLine());
        }
        reader.close();
        return list;
    }
    
    /**
     * Reads all lines of a file and puts them in a HashSet of Strings.
     * @param file the file to be read.
     * @param initialCapacity the initial capacity of the HashSet.
     * @param loadFactor the loadFactor.
     * @return a HashSet containg all lines of the file.
     * @throws IOException if the file doesn't exist.
     */
    public static HashSet<String> fileToHashSet(File file, int initialCapacity, long loadFactor) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HashSet<String> set = new HashSet<String>(initialCapacity, loadFactor);
        String line;
        while((line = reader.readLine()) != null) {
          set.add(line);
        }
        reader.close();
             
        return set;
    }
    
    /**
     * Tries to find a file inside the application.
     * @param filename the name of the file.
     * @return the found File.
     * @throws FileNotFoundException if the file doesn't exist.
     */
    public static InputStream readInternalFile(String filename) throws FileNotFoundException {
        InputStream stream = IOHelper.class.getResourceAsStream(DATA_PATH + filename);
        if(stream == null) {
            stream = IOHelper.class.getResourceAsStream(CSS_PATH + filename);
        }
        if(stream == null) {
            stream = IOHelper.class.getResourceAsStream(FXML_PATH + filename);
        }
        if(stream == null) {
            stream = IOHelper.class.getResourceAsStream(IMG_PATH + filename);
        }
        if(stream == null) {
            throw new FileNotFoundException("Datei " + filename + " wurde in den Ressourcen nicht gefunden");
        }
        return stream;
        /*File file = new File(IOHelper.class.getResource(DATA_PATH + filename).getFile());
        if(!file.exists()) {
            file = new File(IOHelper.class.getResource(CSS_PATH + filename).getFile());
        }
        if(!file.exists()) {
            file = new File(IOHelper.class.getResource(FXML_PATH + filename).getFile());
        }
        if(!file.exists()) {
            file = new File(IOHelper.class.getResource(IMG_PATH + filename).getFile());
        }
        if(!file.exists()) {
            throw new FileNotFoundException("Datei " + filename + " wurde in den Ressourcen nicht gefunden");
        }
        return file;*/
    }
    
    /**
     * Creates a task which reads a file to a HashSet.
     * @param stream the InputStream to be read.
     * @param lineNumber the number of lines which should be read.
     * @return a Task for reading a file to a HashSet.
     * @throws FileNotFoundException if the file doesn't exist.
     */
    public static Task<HashSet<String>> fileToHashSetTask(InputStream stream, int lineNumber) throws FileNotFoundException {
        Task<HashSet<String>> task = new Task<HashSet<String>>() {
            @Override public HashSet<String> call() throws IOException {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                //BufferedReader reader = new BufferedReader(new FileReader(file));
                HashSet<String> set = new HashSet<String>(lineNumber, 1);
                for (int i=0; i<=lineNumber; i++) {
                    if (isCancelled()) {
                       break;
                    }
                    updateProgress(i, lineNumber);
                    set.add(reader.readLine());
                }
                reader.close();
                return set;
            }
        };
        return task;
    }
}
