package persistence.dao;

import configuration.BeansLoader.BeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.entities.Property;
import persistence.entities.Worker;
import services.XMLService;


import java.sql.*;
import java.util.List;

public class WorkerDAO implements AbstractDAO<Worker, String> {

    private String dbURL;
    private String user;
    private String password;

    private static List<Worker> workerList;
    private static int dbLength;
    private static WorkerDAO instance;


    @Autowired
    public BeanBuilder<Worker> workerBeanBuilder;
    @Autowired
    public BeanBuilder<Property> propertyBeanBuilder;

    private WorkerDAO() { }

    public static WorkerDAO getInstance(){
        if (WorkerDAO.instance == null) {
            WorkerDAO.instance = new WorkerDAO();
            return WorkerDAO.instance;
        }
        else
            return WorkerDAO.instance;
    }

    public int getDbLength() {
        return dbLength;
    }

    public static List<Worker> getWorkerList() {
        return WorkerDAO.workerList;
    }

    public List<Worker> findAll() {
        return XMLService.getInstance().createWorkerList();
    }





    public int setDbLength(String dbURL, String user, String password) {
        int length = 0;
        this.password = password;
        this.user = user;
        this.dbURL = dbURL;
        try(Connection con =DriverManager.getConnection(dbURL,user, password)) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(*) from workers ;");
            rs.next();
            length = rs.getInt(1);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(-1);
        }
        WorkerDAO.dbLength = length;
        return length;

    }


    public List<Worker> extractWorkers() {
        List<Worker> workers = workerBeanBuilder.load();
        List<Property> propertyList = propertyBeanBuilder.load();

        try {


            Connection con = DriverManager.getConnection(dbURL,user, password);
            Statement st = con.createStatement();
            String sql =  ("SELECT * FROM workers ;");
            ResultSet rs = st.executeQuery(sql);



            int counter = 0;
            int counter1 = 0;



            while (rs.next()) {



                Worker currentWorker = workers.get(counter);

                currentWorker.setMail(rs.getString("email"));

                Property fullName = propertyList.get(counter1);
                Property department = propertyList.get(counter1 + 1);
                Property salary = propertyList.get(counter1 + 2);

                fullName.setProperty("full-name", rs.getString("name"));
                department.setProperty("department", rs.getString("department"));
                salary.setProperty("salary", rs.getString("salary"));

                currentWorker.addProperty(fullName);
                currentWorker.addProperty(department);
                currentWorker.addProperty(salary);
                counter++;
                counter1 +=3;
            }


        }
        catch (Exception ex) {
            System.out.println("WorkerDAO ERROR:");
            System.out.println(ex.getMessage());
            System.exit(32443);
        }
        workerList = workers;

        return workers;
    }

    }
