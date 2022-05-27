package controller;

import bo.BoFactory;
import bo.custom.ItemBO;
import dto.CustomerDTO;
import dto.ItemDTO;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {
    private final ItemBO itemBO = (ItemBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.ITEM);
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
                    System.out.println("GET item");
                    String itemid = itemBO.generateNewID(connection);
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    response.add("status", 200);
                    response.add("message", "Successfully Added");
                    response.add("data", itemid);
                    writer.print(response.build());
                    break;

                case "GETALL":
                    ArrayList<ItemDTO> allCustomers = itemBO.getAllItems(connection);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); // json array
                    for (ItemDTO i:allCustomers) {
                        String id = i.getItemID();
                        String name = i.getItemName();
                        double qty = i.getQty();
                        double price = i.getPrice();

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("id", id);
                        objectBuilder.add("name", name);
                        objectBuilder.add("qty", qty);
                        objectBuilder.add("price", price);
                        arrayBuilder.add(objectBuilder.build());
                    }


                    JsonObjectBuilder responsegetall = Json.createObjectBuilder();
                    responsegetall.add("status", 200);
                    responsegetall.add("message", "Done");
                    responsegetall.add("data", arrayBuilder.build());
                    writer.print(responsegetall.build());
                    break;

                case "GETIDS":
                    List<String> allCustomersids = itemBO.getItemIds(connection);
                    JsonArrayBuilder arrayBuilderid = Json.createArrayBuilder(); // json array
                    for (String id:allCustomersids) {

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        arrayBuilderid.add(id);
                    }

                    JsonObjectBuilder responsegetallid = Json.createObjectBuilder();
                    responsegetallid.add("status", 200);
                    responsegetallid.add("message", "Done");
                    responsegetallid.add("data", arrayBuilderid.build());
                    writer.print(responsegetallid.build());
                    break;

                case "SEARCH":
                    String itemID = req.getParameter("ItemID");
                    ItemDTO itemDTO = itemBO.searchItem(connection, itemID);
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    objectBuilder.add("id", itemDTO.getItemID());
                    objectBuilder.add("name", itemDTO.getItemName());
                    objectBuilder.add("qty", itemDTO.getQty());
                    objectBuilder.add("price", itemDTO.getPrice());

                    JsonObjectBuilder responsesearch = Json.createObjectBuilder();
                    responsesearch.add("status", 200);
                    responsesearch.add("message", "Done");
                    responsesearch.add("data", objectBuilder.build());
                    writer.print(responsesearch.build());
                    break;
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST Item");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String itemID = jsonObject.getString("id");
        String ItemName = jsonObject.getString("name");
        double qty = Double.parseDouble(jsonObject.getString("qty"));
        double price = Double.parseDouble(jsonObject.getString("price"));

        ItemDTO itemDTO = new ItemDTO(itemID, ItemName, qty, price);

        /*resp.addHeader("Access-Control-Allow-Origin", "*");*/


        PrintWriter writer = resp.getWriter();
        //resp.setContentType("application/json");
        try {
            Connection connection = ds.getConnection();
            if (itemBO.addItem(connection,itemDTO)){
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
        String itemID = req.getParameter("ItemID");
        PrintWriter writer = resp.getWriter();
        //resp.setContentType("application/json");
        //resp.addHeader("Access-Control-Allow-Origin", "*");

        try {
            Connection connection = ds.getConnection();

            if (itemBO.deleteItem(connection,itemID)) {
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
}
