package order;


import exceptions.InvalidArgumentException;
import facility.Facility;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * OrderXmlLoaderImpl.java
 * LogisticsApplication
 */
public class OrderXmlLoaderImpl implements OrderLoader {
    @Override
    public Map<String, Order> loadOrders(String source) throws InvalidArgumentException {
        Map<String, Order> orderMap = new HashMap<>();
        try {
            String fileName = source;

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            File xml = new File(fileName);
            if (!xml.exists()) {
                System.err.println("**** XML File '" + fileName + "' cannot be found");
                System.exit(-1);
            }

            Document doc = db.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList orderEntries = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < orderEntries.getLength(); i++) {
                if (orderEntries.item(i).getNodeType() == Node.TEXT_NODE) {
                    continue;
                }

                String entryName = orderEntries.item(i).getNodeName();
                if (!entryName.equals("Order")) {
                    System.err.println("Unexpected node found: " + entryName);
                    return null;
                }

                // Fetch the order
                NamedNodeMap aMap = orderEntries.item(i).getAttributes();
                String orderId = aMap.getNamedItem("Id").getNodeValue();

                // Fetch Order Time, Destination and Items.
                Element elem = (Element) orderEntries.item(i);
                String orderTime = elem.getElementsByTagName("Time").item(0).getTextContent();
                String orderDestination = elem.getElementsByTagName("Destination").item(0).getTextContent();




                // Get all the items from order.

                Map<String, Integer> orderItems = new HashMap<>();
                NodeList itemList = elem.getElementsByTagName("Item");
                for (int j = 0; j < itemList.getLength(); j++) {
                    if (itemList.item(j).getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }

                    entryName = itemList.item(j).getNodeName();
                    if (!entryName.equals("Item")) {
                        System.err.println("Unexpected node found: " + entryName);
                        return null;
                    }

                    // Fetch item id and quantity.
                    elem = (Element) itemList.item(j);
                    String itemId = elem.getElementsByTagName("Id").item(0).getTextContent();
                    String quantity = elem.getElementsByTagName("Quantity").item(0).getTextContent();

                    // Populate order items.
                    orderItems.put(itemId, Integer.parseInt(quantity));

                }

                // System.out.println("Order_ID: " + orderId + " Time: " + orderTime + " Destination: "+ orderDestination + " OrderItems: ["+orderItems+"]");
                // Populating facilitiesMap
                orderMap.put(orderId, OrderFactory.build("basic", orderId, Integer.parseInt(orderTime), orderDestination, orderItems));

            }

        } catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
            e.printStackTrace();
        }
        return orderMap;
    }
}
