package controller;

import bo.BoFactory;
import bo.custom.CustomerBO;
import dto.CustomerDTO;

import javax.annotation.Resource;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    private final CustomerBO customerBO = (CustomerBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.CUSTOMER);
    @Resource(name = "java:comp/env/jdbc/pool")
    DataSource ds;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String option = req.getParameter("option");
            /*resp.setContentType("application/json");*/
            Connection connection = ds.getConnection();
            PrintWriter writer = resp.getWriter();

            //resp.addHeader("Access-Control-Allow-Origin", "*");

            switch (option){
                case "GENERATEID":
                    String custid = customerBO.generateNewID(connection);
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    response.add("status", 200);
                    response.add("message", "Successfully Added");
                    response.add("data", custid);
                    writer.print(response.build());
                    break;

                case "GETALL":
                    //ResultSet rst = connection.prepareStatement("select * from Customer").executeQuery();
                    ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); // json array
                    for (CustomerDTO c:allCustomers) {
                        String id = c.getCustomerId();
                        String name = c.getCustomerName();
                        String address = c.getCustomerAddress();
                        double salary = c.getSalary();

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("id", id);
                        objectBuilder.add("name", name);
                        objectBuilder.add("address", address);
                        objectBuilder.add("salary", salary);
                        arrayBuilder.add(objectBuilder.build());
                    }

                    JsonObjectBuilder responsegetall = Json.createObjectBuilder();
                    responsegetall.add("status", 200);
                    responsegetall.add("message", "Done");
                    responsegetall.add("data", arrayBuilder.build());
                    writer.print(responsegetall.build());

                case "SEARCH":


            }


            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Post");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String customerID = jsonObject.getString("id");
        String customerName = jsonObject.getString("name");
        String customerAddress = jsonObject.getString("address");
        double customerSalary = Double.parseDouble(jsonObject.getString("salary"));

        CustomerDTO customer = new CustomerDTO(customerID, customerName, customerAddress, customerSalary);

        /*resp.addHeader("Access-Control-Allow-Origin", "*");*/


        PrintWriter writer = resp.getWriter();
        //resp.setContentType("application/json");
        try {
            Connection connection = ds.getConnection();
            if (customerBO.addCustomer(connection,customer)){
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());

            }
            connection.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            JsonObjectBuilder response = Json.createObjectBuilder();
            response.add("status", 400);
            response.add("message", "Error");
            response.add("data", throwables.getLocalizedMessage());
            writer.print(response.build());
            resp.setStatus(HttpServletResponse.SC_OK); //200
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerID = req.getParameter("CusID");
        PrintWriter writer = resp.getWriter();
        //resp.setContentType("application/json");
        //resp.addHeader("Access-Control-Allow-Origin", "*");

        try {
            Connection connection = ds.getConnection();

            if (customerBO.deleteCustomer(connection,customerID)) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Deleted");
                writer.print(objectBuilder.build());
            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("data", "Wrong Id Inserted");
                objectBuilder.add("message", "");
                writer.print(objectBuilder.build());
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            resp.setStatus(200);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Error");
            objectBuilder.add("data", throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
        }

    }

/*    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String customerID = jsonObject.getString("id");
        String customerName = jsonObject.getString("name");
        String customerAddress = jsonObject.getString("address");
        String customerSalary = jsonObject.getString("salary");
        PrintWriter writer = resp.getWriter();
        //resp.setContentType("application/json");
        //resp.addHeader("Access-Control-Allow-Origin", "*");

        try {
            Connection connection = ds.getConnection();
            PreparedStatement pstm = connection.prepareStatement("Update Customer set name=?,address=?,salary=? where id=?");
            pstm.setObject(1, customerName);
            pstm.setObject(2, customerAddress);
            pstm.setObject(3, customerSalary);
            pstm.setObject(4, customerID);
            if (pstm.executeUpdate() > 0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Updated");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("message", "Update Failed");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
            connection.close();
        } catch (SQLException throwables) {
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("status", 500);
            objectBuilder.add("message", "Update Failed");
            objectBuilder.add("data", throwables.getLocalizedMessage());
            writer.print(objectBuilder.build());
        }
    }*/

}
