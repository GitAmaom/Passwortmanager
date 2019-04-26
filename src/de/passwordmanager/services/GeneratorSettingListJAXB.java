package de.passwordmanager.services;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.passwordmanager.entities.GeneratorSettingList;

/**
 * Class that serializes the {@code GeneratorSettingList} with XML.
 * @author Moritz Schneider
 *
 */
public class GeneratorSettingListJAXB {

    /**
     * Serializes an GeneratorSettingList.
     * @param settings the GeneratorSettingList.
     * @param file the file it gets serialized to.
     */
    public void marshall(GeneratorSettingList settings, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(GeneratorSettingList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(settings, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Deserializes a file to an GeneratorSettingList.
     * @param file the file to deserialize.
     * @return the GeneratorSettingList.
     */
    public GeneratorSettingList unmarshall(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(GeneratorSettingList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            GeneratorSettingList setting = (GeneratorSettingList) unmarshaller.unmarshal(file);
            return setting;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("Datei konnte nicht deserialisiert werden.");
        return null;
    }

}
