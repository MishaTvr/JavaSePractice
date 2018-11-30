package persistence.entities;

import structures.Field;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Mail implements FileLoader {
    private String message = null;
    private String fileName = "letterTemplate.txt";
    private String email;
    private String subject = "Salary Alert";


    public String getSubject() {
        return subject;
    }

    public String getMessage() {return message;}
    public String getEmail() {return email;}

    public static List<Mail>  getMailList (List<Worker> workerList) {
        List<Mail> mailList = new ArrayList<>();
        for(Worker worker: workerList) {
            mailList.add(new Mail(worker));
        }
        return mailList;
    }

    public Mail (Worker worker) {
        String path = "";

        try {
            path = getFilePath(fileName);
        }
        catch (NoSuchFileException ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }





        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            StringBuilder line = new StringBuilder();
            while ( (message = reader.readLine()) != null) {
                line.append(message);
                line.append('\n');
            }
            message = line.toString();
        }
        catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        email = worker.getMail();
        List <Property> propertyList = worker.getPropertyList();


        for (Property prop: propertyList) {
            for (Field field: Field.values()) {
                if (prop.getName().equals(field.getName()))
                    field.setValue(prop.getValue());
            }
        }

        Field.DATE.setValue(new Date().toString());

        for (Field field: Field.values()) {
            message = message.replaceAll(field.getCode(), field.getValue());
        }


    }




}
