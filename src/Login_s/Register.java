/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Login_s;
import javax.swing.JOptionPane;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author User
 */
public class Register extends javax.swing.JFrame {
     public static String Uname;
     public static String Pword;
     
     
    public void store(String user,String pass)
    {
        this.Uname=user;
        this.Pword=pass;
    }
//    public String getUname()
//    {
//        return this.Uname;
//    }
//    public String getPword()
//    {
//        return this.Pword;
//    }
    /**
     * Creates new form Register
     */
    public static String checkusername;
    public static String checkpassword;
    public static String append="a";
public int checkExsisting(String a,String b)
{
    int check=0;
     try {
    File fXmlFile = new File("C:\\XML\\data.xml");
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(fXmlFile);
    doc.getDocumentElement().normalize();

    //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
    NodeList nList = doc.getElementsByTagName("Player");
    //System.out.println("----------------------------");

    for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        //System.out.println("\nCurrent Element :" + nNode.getNodeName());
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element eElement = (Element) nNode;
            
            checkusername=eElement.getElementsByTagName("UserName")
                                 .item(0).getTextContent();
            checkpassword=eElement.getElementsByTagName("Password")
                                 .item(0).getTextContent();
            //System.out.println(checkusername+checkpassword);
            if(a.equals(checkusername))
            {
            JOptionPane.showMessageDialog(null,"UserName already Exsists","Login Error",JOptionPane.ERROR_MESSAGE);
            check=1;
            return check;
            } 
            
        }
    }
    } catch (Exception e) {
    e.printStackTrace();
    }
     return check;
}
    
    
    public Register() {
        initComponents();
        setLocation(300,300);
        setSize(345,340);
        setResizable(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        RegisterUserName = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        RegisterPassword = new javax.swing.JPasswordField();
        RegisterPasswordReEntry = new javax.swing.JPasswordField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        RegisterUserName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterUserNameActionPerformed(evt);
            }
        });
        getContentPane().add(RegisterUserName);
        RegisterUserName.setBounds(192, 69, 130, 30);

        jButton1.setFont(new java.awt.Font("Rockwell Condensed", 1, 24)); // NOI18N
        jButton1.setText("Submit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(20, 250, 120, 40);

        RegisterPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterPasswordActionPerformed(evt);
            }
        });
        getContentPane().add(RegisterPassword);
        RegisterPassword.setBounds(190, 140, 128, 30);

        RegisterPasswordReEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterPasswordReEntryActionPerformed(evt);
            }
        });
        getContentPane().add(RegisterPasswordReEntry);
        RegisterPasswordReEntry.setBounds(190, 200, 130, 30);

        jTextField2.setFont(new java.awt.Font("Rockwell Condensed", 1, 14)); // NOI18N
        jTextField2.setText("Username");
        jTextField2.setEnabled(false);
        getContentPane().add(jTextField2);
        jTextField2.setBounds(20, 70, 120, 40);

        jTextField3.setFont(new java.awt.Font("Rockwell Condensed", 1, 14)); // NOI18N
        jTextField3.setText("Password");
        jTextField3.setEnabled(false);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField3);
        jTextField3.setBounds(20, 130, 120, 40);

        jTextField4.setFont(new java.awt.Font("Rockwell Condensed", 1, 14)); // NOI18N
        jTextField4.setText("Re-Enter Password");
        jTextField4.setEnabled(false);
        jTextField4.setVerifyInputWhenFocusTarget(false);
        getContentPane().add(jTextField4);
        jTextField4.setBounds(20, 190, 120, 40);

        jButton2.setFont(new java.awt.Font("Rockwell Condensed", 1, 24)); // NOI18N
        jButton2.setText("Goto Login");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(170, 250, 150, 40);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Login_s/My_Post_(6).jpg"))); // NOI18N
        jButton3.setText("jButton3");
        getContentPane().add(jButton3);
        jButton3.setBounds(0, 0, 340, 310);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void RegisterUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterUserNameActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_RegisterUserNameActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int checkExsisting=1;
        String RegisterName=RegisterUserName.getText();
        String RegisterPass=RegisterPassword.getText();
        String RegisterPassReEntry=RegisterPasswordReEntry.getText();
        checkExsisting=checkExsisting(RegisterName,RegisterPass);
        if(checkExsisting==0){
        int verifiedValue=0;
        verifiedValue=verifyLogin(RegisterName,RegisterPass,RegisterPassReEntry);
   
         //creating xml
        try{
            //String s="a";
    DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
    DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
    
    Document document =documentBuilder.newDocument();
    
    Element element =  document.createElement("Player");
    document.appendChild(element);
   
    Attr attr = document.createAttribute("Id");
    attr.setValue(append);
    append=append+"a";
    element.setAttributeNode(attr);
    
    Element Username = document.createElement("UserName");
    Username.appendChild(document.createTextNode(RegisterName));
    element.appendChild(Username);
    
    Element password = document.createElement("Password");
    password.appendChild(document.createTextNode(RegisterPass));
    element.appendChild(password);
    
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    
    DOMSource source = new DOMSource(document);
    
        StreamResult streamResult = new StreamResult(new File("C:\\XML\\data.xml"));
        transformer.transform(source,streamResult);
        }
        catch(Exception e){
            System.out.println(e);
        }  
        }
        else 
        {
            RegisterUserName.setText(null);
            RegisterPassword.setText(null);
            RegisterPasswordReEntry.setText(null);
        }
    }//GEN-LAST:event_jButton1ActionPerformed
   
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Login obj1=new Login();
        obj1.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void RegisterPasswordReEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterPasswordReEntryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RegisterPasswordReEntryActionPerformed

    private void RegisterPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RegisterPasswordActionPerformed

    public int verifyLogin(String RegisterName,String RegisterPass,String RegisterPassReEntry)
    {
        int temp=0;
        char tempChar;
        int uppercaseCount=0,digitCount=0,specialCharCount=0,lowerCount=0;
        //int uppercaseCount1=0,digitCount1=0,specialCharCount1=0,lowerCount1=0;
        if(!(RegisterPass.equals(RegisterPassReEntry)))
        {
            JOptionPane.showMessageDialog(null,"Password and Re-Entered PassWord do not match","Login Error",JOptionPane.ERROR_MESSAGE);
        }
        else if((RegisterName.length()>9 && RegisterName.length()<25) && (RegisterPass.length()>8 && RegisterPass.length()<20))
        {
            if(RegisterName.contains("@") && RegisterName.contains(".com"))
            {
                for(int i=0;i<RegisterPass.length();i++)
                {
                        tempChar=RegisterPass.charAt(i);
                        if(Character.isUpperCase(tempChar))
                        {
                            uppercaseCount++;
                        }
                        else if(Character.isDigit(tempChar))
                        {
                            digitCount++;
                        }  
                        else if(Character.isLowerCase(tempChar))
                        {
                            lowerCount++;
                        }
                        else
                        {
                            specialCharCount++;
                        }
                }
            }
        }
        else
        {
         JOptionPane.showMessageDialog(null,"UserName must have min 9 characters\n"
                 + "Username must have an @ and .com\n"
                 + "Password must have min 8 characters\n"
                 + "Password must have an Uppercase Character\n"
                 + "Password must have an Special Character\n"
                 + "Password must have a Digit\n","Login Error",JOptionPane.ERROR_MESSAGE);
        }
        if(uppercaseCount>0 && digitCount>0 && specialCharCount>0)
        {
        JOptionPane.showMessageDialog(null,"User Created","User Successfully Created",JOptionPane.INFORMATION_MESSAGE);
        store(RegisterName,RegisterPass);
        temp=1;
        }
        return temp;
    }
   
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Register().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField RegisterPassword;
    private javax.swing.JPasswordField RegisterPasswordReEntry;
    private javax.swing.JTextField RegisterUserName;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
