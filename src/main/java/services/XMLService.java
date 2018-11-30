package services;


import org.w3c.dom.Element;
import persistence.entities.FileLoader;
import persistence.entities.Property;
import persistence.entities.Worker;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;


public class XMLService implements FileLoader {

    private static String fileName = "recipients.xml";
    private static List<Worker> workerList = new ArrayList<>();
    private static XMLService instance;


    private XMLService() {
    }

    public static XMLService getInstance(){
        if(instance == null){
            return new XMLService();
        }else
            return instance;
    }

    public static List<Worker> getWorkerList() {
        return workerList;
    }

    public List<Worker> createWorkerList() {

        String path = "";

        try {
            path = getFilePath(fileName);
        }
        catch (NoSuchFileException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }

        try {

            File xmlFile = new File(path);
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList workers = document.getElementsByTagName("worker");

            for (int i = 0; i < workers.getLength(); i++) {
                Worker currentWorker = new Worker();
                Element workerItem = (Element) workers.item(i);
                currentWorker.setMail(workerItem.getElementsByTagName("mail").item(0).getTextContent());

                NodeList props = workerItem.getElementsByTagName("properties").item(0).getChildNodes();
                for (int j = 0; j < props.getLength(); j++) {
                    if (Node.ELEMENT_NODE != props.item(j).getNodeType())
                        continue;
                    Element element = (Element) props.item(j);
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String value = element.getElementsByTagName("value").item(0).getTextContent();
                    if ((name != null) && (value!= null))
                        currentWorker.addProperty(new Property(name, value));
                }

                workerList.add(currentWorker);
            }


        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
        return workerList;
    }
}
