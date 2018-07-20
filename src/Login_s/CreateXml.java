package Login_s;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package Login_s;
//
//import java.io.File;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import org.w3c.dom.Attr;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//
///**
// *
// * @author User
// */
//public class CreateXml {
//    public static void main(String[] args) throws Exception{
//    DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
//    DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
//    
//    Document document =documentBuilder.newDocument();
//    
//    Element element =  document.createElement("Developer");
//    document.appendChild(element);
//    
//    Attr attr = document.createAttribute("Id");
//    attr.setValue("1");
//    element.setAttributeNode(attr);
//    
//    Element Username = document.createElement("UserName");
//    Username.appendChild(document.createTextNode("Chiranjeevi"));
//    element.appendChild(Username);
//    
//    Element password = document.createElement("Password");
//    password.appendChild(document.createTextNode("abc"));
//    element.appendChild(password);
//    
//    TransformerFactory transformerFactory = TransformerFactory.newInstance();
//    Transformer transformer = transformerFactory.newTransformer();
//    
//    DOMSource source = new DOMSource(document);
//    
//        StreamResult streamResult = new StreamResult(new File("C:\\XML\\data.xml"));
//        transformer.transform(source,streamResult);
//        
//    }
//    
//    
//    
//    }
//
