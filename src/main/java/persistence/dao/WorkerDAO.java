package persistence.dao;

import configuration.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import persistence.entities.Property;
import persistence.entities.Worker;
import services.XMLService;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class WorkerDAO implements AbstractDAO<Worker, String> {
    private static String dbClass;
    private static String dbURL;
    private static String user;
    private static String password;
    private static List<Worker> workerList;
    private static WorkerDAO instance;



    private WorkerDAO () {}

    public static WorkerDAO getInstance(){
        if(instance == null){
            return new WorkerDAO();
        }else
            return instance;
    }

    public void setDbURL(String dbURL) {
        WorkerDAO.dbURL = dbURL;
    }

    public void setUser(String user) {
        WorkerDAO.user = user;
    }

    public void setPassword(String password) {
        WorkerDAO.password = password;
    }

    public void setDbClass(String dbClass) {

        WorkerDAO.dbClass = dbClass;
    }

    public static List<Worker> getWorkerList() {
        return WorkerDAO.workerList;
    }

    public List<Worker> findAll() {
        return XMLService.getInstance().createWorkerList();
    }



    public static List<Worker> extractWorkers() {
        List<Worker> workerList = new ArrayList<>();
        try {
            System.out.println("maybe here");
            System.out.println(dbClass);
            System.out.println(dbURL);

            Class.forName(dbClass).newInstance();
            System.out.println("maybe here1");
            Connection con = DriverManager.getConnection(dbURL,user, password);
            System.out.println("maybe here2");
            Statement st = con.createStatement();
            String sql =  ("SELECT * FROM workers ;");
            ResultSet rs = st.executeQuery(sql);

            //ApplicationContext factory = new AnnotationConfigApplicationContext(AppConfig.class);


            while (rs.next()) {
                Worker currentWorker = new Worker();
                currentWorker.setMail(rs.getString("email"));
                Property fullName = new Property("full-name", rs.getString("name"));
                Property department = new Property("department", rs.getString("department"));
                Property salary = new Property("salary", rs.getString("salary"));
                currentWorker.addProperty(fullName);
                currentWorker.addProperty(department);
                currentWorker.addProperty(salary);
                workerList.add(currentWorker);
            }


        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(32443);
        }

        WorkerDAO.workerList = workerList;

        return workerList;
    }

    }
