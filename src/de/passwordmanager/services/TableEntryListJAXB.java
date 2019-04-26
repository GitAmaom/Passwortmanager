package de.passwordmanager.services;

import java.io.File;

import javax.xml.bind.*;

import de.passwordmanager.entities.TableEntryList;

/**
 * Class that serializes the {@code TableEntryList} with XML.
 * @author Moritz Schneider
 *
 */
public class TableEntryListJAXB {

    /**
     * Serializes an GeneratorSettingList.
     * @param list the TableEntryList.
     * @param file the file it gets serialized to.
     */
    public void marshall(TableEntryList list, File file) {
        try {
            JAXBContext jc = JAXBContext.newInstance(TableEntryList.class);
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(list, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Deserializes a file to an TableEntryList.
     * @param file the file to deserialize.
     * @return the TableEntryList.
     */
    public TableEntryList unmarshall(File file) {
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(TableEntryList.class);
            Unmarshaller u = jc.createUnmarshaller();
            TableEntryList list = (TableEntryList) u.unmarshal(file);
            return list;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("Datei konnte nicht deserialisiert werden.");
        return null;
    }
}
