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
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
///**
// *
// * @author User
// */
//public class ReadXml {
//    public static void main(String args[]) throws Exception{
//        File xmlFile=new File("C:\\XML\\data.xml");
//        
//        DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
//        DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
//        
//        Document document=documentBuilder.parse(xmlFile);
//        
//        NodeList list=document.getElementsByTagName("Developer");
//        
//        for(int i=0;i<list.getLength();i++)
//        {
//            Node node=list.item(i);
//            
//            if(node.getNodeType()==Node.ELEMENT_NODE){
//                
//                Element element=(Element)node;
//                System.out.println("ID: "+element.getAttribute("Id"));
//                System.out.println("UserName:"+element.getElementsByTagName("UserName").item(0).getTextContent());
//                System.out.println("Password:"+element.getElementsByTagName("Password").item(0).getTextContent());
//
//            
//            }
//            
//        }
//        
//        
//    }
//}
