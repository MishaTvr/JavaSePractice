import persistence.dao.WorkerDAO;

public class Main {
    public static void main(String[] args) {
        WorkerDAO workerDAO = new WorkerDAO();

        workerDAO.findAll().stream().forEach(System.out::println);


    }

}