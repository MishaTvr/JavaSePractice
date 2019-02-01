package services;

import persistence.dao.WorkerDAO;
import persistence.entities.Property;
import persistence.entities.Worker;

import static java.lang.Integer.parseInt;

public class DBservice {


    public static int getTotalPayments() {
        int payments = 0;
        for (Worker currentWorker: WorkerDAO.getWorkerList()) {
            for (Property property : currentWorker.getPropertyList()) {
                if (property.getName().equals("salary"))
                    payments += parseInt(property.getValue());

            }
        }
        return payments;
    }
}
