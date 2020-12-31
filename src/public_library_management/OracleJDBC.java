/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package public_library_management;

import oracle.jdbc.driver.*;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.Text;

/**
 *
 * @author Dipcenation
 */
public class OracleJDBC {

    Connection c = null;
    Statement stmt = null;
    ResultSet r;
    boolean insertintobook2(String[] s) {
        try {
            String maxid = new String();
            stmt = c.createStatement();
            maxid = "SELECT Max(Book_ID) FROM Book";

            r = stmt.executeQuery(maxid);
            int max = 0;
            while (r.next()) {
                max = r.getInt(1);
                System.out.println("" + r.getString(1));
            }
            System.out.println(max);
            String insert = "insert into book(book_id ,book_name,publisher_id,language,date_of_publication ,genre,no_of_copies)"
                    + " VALUES (" + Integer.toString(max + 1);
            for (int i = 0; i < s.length; i++) {
                //s[i]= "aa" ;
                if (i == 3) {
                    insert = insert + ",TO_DATE('" + s[i] + "','dd/mm/yyyy')";
                } else if (i == 6 || i == 1) {
                    insert = insert + "," + s[i] + "";
                } else if (i != 5 && i != 1 && i != 6) {
                    insert = insert + ",'" + s[i] + "'";
                }
            }

            insert = insert + ")";
            System.out.println("string: " + insert);
            r = stmt.executeQuery(insert);

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
        //return true ;
    }
    
    boolean rateCommentBook(String comment,int rate,int memberID,int bookID,int flag)
    {
        try {
            stmt = c.createStatement();
            String query = new String();
            if(flag==1)query="insert into REVIEW(REVIEW_ID,MEMBER_ID,BOOK_ID,RATING) values "
                    + "((select max(REVIEW_ID) from review)+1,"+memberID+","+bookID+","+rate+")";
            else if(flag==2)query="insert into REVIEW(REVIEW_ID,MEMBER_ID,BOOK_ID,comments) values "
                    + "((select max(REVIEW_ID) from review)+1,"+memberID+","+bookID+",'"+comment+"')";
            else if(flag==3)
                query="update  review \n" +
                        "set rating= "+rate+" where book_id = "+bookID+" and member_id= "+memberID;
            else if(flag==4)
                query="update  review \n" +
                        "set comments= '"+comment+"' where book_id = "+bookID+" and member_id= "+memberID;
            System.out.println(query);
            r = stmt.executeQuery(query);

            
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return true; 
    }
    boolean insertEntry(String[] s, String[] type, String table) {
        try {
            int i = 0;
            String proc = new String("");
            proc = "{call insert_into_"+table+"(";
            for (i = 1 ; i <= s.length ;i++)
            {
                
                proc+= "?";
                if (i<s.length)
                    proc += "," ;
            }
                   proc += ")}"; 
            System.out.println("Procedure: " + proc);
            CallableStatement cst = c.prepareCall(proc);
//
//            cst.setString(1, "fuck");
//            cst.setString(2, "you");
//            cst.setString(3, "12/12/2012");
//            cst.setString(4, "fuckyou@gmail.com");
//            cst.setString(5, "fucker");
//            cst.setString(6, "01728212466");
//
//            cst.setString(7, "fuck12345");
//            cst.setString(8, "male");
            System.out.println("l:"+s.length);
           // cst.setInt(9, 3005);

            for (i = 1 ; i <= s.length ;i++)
            {
                if (type [i-1].equals("number"))
                {
                    cst.setInt(i, Integer.parseInt(s[i-1]));
             
                }
                else if (type [i-1].equals("varchar2"))
                {
                    System.out.println("entered");
                    cst.setString(i ,s[i-1]);
              
                }
                
            }
            //           cst.setInt(9, 2000);
            // System.out.println(""+query);
            cst.execute();
            //stmt.(query);

            // return true ;
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    boolean checkUniqueEmail (String email , String table)
    {
        boolean isUnique = true ;
        try {
            
            String query = new String () ;
            query = "Select email from "+ table+" m where (m.email = '"+email+"')" ;
            stmt = c.createStatement() ;
            r = stmt.executeQuery(query) ;
            if (!r.next())
                isUnique=  true ;//email unique ;
            else
               isUnique =  false ; 
                
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isUnique ;
    }
    void createConnection() {
        try {
            c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:globaldb", "LIBRARY", "123456789");
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void closeConnection()
    {
        try {
            if(c!=null)
            {
                 c.close();
                   c=null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    boolean checkUser(String email, String password, boolean isMember) {
        try {
            stmt = c.createStatement();
            String query = new String();
            if (isMember == true) {
                query = "select email,PASS_WORD\n"
                        + "from MEMBERS\n"
                        + "where email='" + email + "' and pass_word='" + password + "'";
            } else {
                query = "select email,PASS_WORD\n"
                        + "from Librarian\n"
                        + "where email='" + email + "' and pass_word='" + password + "'";
            }
            r = stmt.executeQuery(query);

            if (!r.next()) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    boolean createMember(String[] s) {
        try {

            String maxid = new String();
            stmt = c.createStatement();
            maxid = "SELECT Max(MEMBER_ID) FROM Members";

            r = stmt.executeQuery(maxid);
            int max = 0;
            while (r.next()) {
                max = r.getInt(1);
                System.out.println("" + r.getString(1));
            }
            System.out.println(max);
             String insert = "insert into Members(MEMBER_id,member_name,address,BirthDate,email,occupation,contact_no,pass_word,gender)"
                    + " VALUES ((SELECT Max(MEMBER_ID) FROM Members)+1,'" 
                    +s[0]+"','"+s[1]+"',TO_DATE('"+s[2]+"','dd/mm/yyyy'),'"+s[3]
                    +"','"+s[4]+"','"
                    +s[5]+"','"+s[6]+"','"+s[7]+"')";
            r = stmt.executeQuery(insert);

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    boolean updateMember(String[] s) {
        try {

            String maxid = new String();
            stmt = c.createStatement();
            maxid = "SELECT Max(MEMBER_ID) FROM Members";

            r = stmt.executeQuery(maxid);
            int max = 0;
            while (r.next()) {
                max = r.getInt(1);
                System.out.println("" + r.getString(1));
            }
            System.out.println(max);
             String update = "update MEMBERS\n" +
"set MEMBER_NAME='"+s[0]+"',ADDRESS='"+s[1]+"',birthDate=TO_DATE('"+s[2]+"','dd/mm/yyyy'),\n" +
"occupation='"+s[4]+"',CONTACT_NO='"+s[5]+"',PASS_WORD='"+s[6]+"', GENDER='"+s[7]+"'\n" +
"where email='"+s[3]+"'";
            r = stmt.executeQuery(update);

            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    ResultSet retrievemembers() {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select* from members";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
        ResultSet retrieveFromTable(String Table) {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select* from "+Table;
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
        ResultSet searchBooks(String keyword,String Table) {
        try {
            stmt = c.createStatement();
            String query=" ";
            if(Table.equals("BOOK")){ 
                query = "Select book_id,book_name from book  where upper(book_name) like '";
                    if(keyword.length()>3) query=query+"%"+keyword+"%";
                    else query = query+keyword;
                query=query  + "'";
            for(int i=1;i<keyword.length()-1;i++)
            {
                StringBuilder myName = new StringBuilder(keyword);
                myName.setCharAt(i, '%');
                query+= "or upper(book_name) like '"+myName+"'";
            }
            }
            else if(Table.equals("AUTHOR")){ 
          query = "Select b.book_id,b.book_name,auth.AUTHOR_Name\n" +
                        "from BOOK b \n" +
                        "join Writer W\n" +
                        "on(b.BOOK_ID=W.BOOK_ID)\n" +
                        "join AUTHOR Auth\n" +
                        "on(W.AUTHOR_ID=AUTH.AUTHOR_ID)\n" +
                        "where  upper(AUTH.AUTHOR_Name) like '";
                   if(keyword.length()>3) query=query+"%"+keyword+"%";
                    else query = query+keyword;
                query=query  + "'";
            for(int i=1;i<keyword.length()-1;i++)
            {
                StringBuilder myName = new StringBuilder(keyword);
                myName.setCharAt(i, '%');
                query+= "or upper(AUTH.AUTHOR_Name) like '"+myName+"'";
            }
            }
            else 
                if(Table.equals("PUBLICATION"))
                { 
                query = "select b.BOOK_ID,b.BOOK_NAME,pub.PUBLICATION_NAME from book b\n" +
                         "join PUBLICATION pub on (b.PUBLISHER_ID=PUB.PUBLICATION_ID)\n" +
                        "where upper(PUB.PUBLICATION_NAME) like '";
                    if(keyword.length()>3) query=query+"%"+keyword+"%";
                    else query = query+keyword;
                query=query  + "' ";
            for(int i=1;i<keyword.length()-1;i++)
            {
                StringBuilder myName = new StringBuilder(keyword);
                myName.setCharAt(i, '%');
                query+= "or upper(PUB.PUBLICATION_NAME) like '"+myName+"'";
            }
            }
        
            r = stmt.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    ResultSet retrieveBookInfo(String bookName) {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select BOOK_NAME,LANGUAGE,DATE_OF_PUBLICATION,GENRE,PREQUEL from book where book_name= '" + bookName + "'";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    ResultSet retrieveBookInfo(int bookID) {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select BOOK_NAME,LANGUAGE,DATE_OF_PUBLICATION,GENRE,prequel from book where book_ID= '" + bookID + "'";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrieveAuthorName(int bookID) {
        try {
            stmt = c.createStatement();
            String query;
            query = "select AU.author_name from book b \n" +
"join writer W on(b.BOOK_ID=W.BOOK_ID)\n" +
"join AUTHOR Au on (W.author_ID=AU.author_ID)\n" +
"where b.BOOK_ID = "+bookID;
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrievePublisherName(int bookID) {
        try {
            stmt = c.createStatement();
            String query;
            query = "select PUBLICATION_Name\n"
                    + "from book b join PUBLICATION p\n"
                    + "on(b.publisher_id=p.PUBLICATION_id)\n"
                    + "where b.book_ID='" + bookID + "'";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrieveBookList(String menuType,String arg) {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select book_id,book_name from book where "+menuType+"='"+arg+"'";
            System.out.println(menuType);
            if(menuType.equals("author"))
                query = "Select b.BOOK_ID,b.book_name,Auth.AUTHOR_Name\n" +
                        "from BOOK b \n" +
                        "join Writer W\n" +
                        "on(b.BOOK_ID=W.BOOK_ID)\n" +
                        "join AUTHOR Auth\n" +
                        "on(W.AUTHOR_ID=AUTH.AUTHOR_ID)\n" +
                        "where  AUTH.AUTHOR_Name=  '"+arg+"'";
            System.out.println(query);
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    ResultSet retrieveBookList() {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select book_id,book_name from book";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    ResultSet menuList(String s1,String s2) {
        try {
            stmt = c.createStatement();
            String query;
            query = "select distinct "+s1 +" from "+ s2;
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    ResultSet retrieveBookCount() {
        try {
            stmt = c.createStatement();
            String query;
            query = "Select count(*) from book";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrievePrequel(int bookID) {
        try {
            stmt = c.createStatement();
            String query;
            query = "select B2.BOOK_NAME from book b1 join book b2\n"
                    + "on(b1.PREQUEL=B2.BOOK_ID) where B1.book_ID='" + bookID + "'";
            r = stmt.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    ResultSet retrieveInfoss(String s,String dataType, String Tablename) {
        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");

            //c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:globaldb","Library","123456789");
            stmt = c.createStatement();

            String query = "select * from " + Tablename + " where "+dataType+" = '" + s + "'";
            System.out.println("ttttttttttttttt");
            r = stmt.executeQuery(query);

            int i = 0;
            System.out.println("in query\n");
            //try {
//            while (r.next())
//            {
//                System.out.println(r.getString(i+1));
//                i++;
//            }
        } catch (Exception e) {
            System.out.println("error in query");
        }
        return r;
    }
    
    ResultSet rateInfo(int memberID,int bookID) {
        try {
            stmt = c.createStatement();

            String query = "select * from REVIEW where member_ID="+memberID+" and book_ID = " +bookID;
            System.out.println("ttttttttttttttt");
            r = stmt.executeQuery(query);

            int i = 0;
            System.out.println("in query\n");
            //try {
//            while (r.next())
//            {
//                System.out.println(r.getString(i+1));
//                i++;
//            }
        } catch (Exception e) {
            System.out.println("error in query");
        }
        return r;
    }


    boolean sendBorrowRequest(int memberID,int bookID) {
        try {
            String query = new String();
              query="insert into book_request(request_Id,book_ID,status,member_ID) \n" +
"values((select max(request_ID) from BOOK_request)+1,"+bookID+",'pending',"+memberID+")";
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
    ResultSet checkBorrowStatus(int memberID,int bookID) {
        try {
            String query = new String();
              query="select status from book_request where member_id ="+memberID;
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    ResultSet checkIssueStatus(int memberID) {
        try {
            String query = new String();
              query="select b.BOOK_name from issue iss\n" +
"join book b on(iss.book_id=b.BOOK_ID)\n" +
" where member_id = "+memberID;
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    ResultSet retrieveBorrowList(int memberID) {
        try {
            String query = new String();
            query="select b.BOOK_NAME,BRQ.status from book_request brq\n" +
"join book b on(BRQ.book_id=b.BOOK_ID)\n" +
" where member_id ="+memberID;
              
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
    ResultSet retrieveIssueList(int memberID) {
        try {
            String query = new String();
              query="select b.BOOK_name from issue iss\n" +
"join book b on(iss.book_id=b.BOOK_ID)\n" +
" where member_id ="+memberID;
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    //
    ResultSet searchinTable(String name , String column , String Table) {
        try {
            String query = "Select* from "+Table+" where "+column+" Like '%" + name + "%'";
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
//            while (r.next())
//            {
//                System.out.println(""+r.getString("Member_name"));
//            }

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    boolean checkUniqueEntry(String column_name, String website, String table) {
        boolean isunique = false;
        try {
            String query = "Select* from " + table + " where " + column_name + " = '" + website + "'";

            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            if (r.next() == false) {
                isunique = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isunique;
    }

    //check if the place for shelf is occupied or not
    boolean checkIsOccupied(String floor_no, String row, String column) {
        boolean isoccupied = false;
        try {
            String query = "Select* from shelf  where floor_no = " + floor_no + " and row_no = " + row + " and column_no = " + column;

            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            if (r.next() == false) {
                isoccupied = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isoccupied;
    }

    boolean isValidEntry(String column_name, String entry, String table) {
        try {
            String query = "Select* from " + table + " where " + column_name + " ='" + entry + "'";
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            if (r.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    boolean replacelibrarian(int dlid, int lid) {
        try {
            String proc = new String("");
            proc = "{call replace_librarian(?, ?)}";

            CallableStatement cst = c.prepareCall(proc);
            cst.setInt(1, dlid);
            cst.setInt(2, lid);
            cst.execute();

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;

    }

	boolean insertintoissue(String s0 , String s1 , String s2) {
        try {
            String query = new String () ;
            query = "insert into issue(issue_id,issue_date,member_ID,Book_ID,LIBRARIAN_id,required_return_date)\n" +
                    " values ((select nvl(max(issue_id), 0) from issue)+1,TO_DATE(SYSDATE,'dd/mm/yyyy'),"
                    +s0+","+s1+","+s2+","+"TO_DATE(SYSDATE+10,'dd/mm/yyyy'))" ;
            System.out.println(query);
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            
        } catch (SQLException ex) {
            
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false ;
        }
        return true;
    }

    boolean insertintobook(String[] s) {
        try {
            String maxid = new String();
            stmt = c.createStatement();
            maxid = "SELECT Max(Book_ID) FROM Book";

            r = stmt.executeQuery(maxid);
            int max = 0;
            while (r.next()) {
                max = r.getInt(1);
                System.out.println("" + r.getString(1));
            }
            System.out.println(max);
            String insert = "insert into book(book_id ,book_name,publisher_id,language,date_of_publication ,genre,prequel ,no_of_copies)"
                    + " VALUES (" + Integer.toString(max + 1);
            for (int i = 0; i < s.length; i++) {
                //s[i]= "aa" ;
                if (i == 3) {
                    insert = insert + ",TO_DATE('" + s[i] + "','dd/mm/yyyy')";
                } else if (i == 6 || i == 1 || i == 5) {
                    insert = insert + "," + s[i] + "";
                } else if (i != 5 && i != 1 && i != 6) {
                    insert = insert + ",'" + s[i] + "'";
                }
            }

            insert = insert + ")";
            System.out.println("string: " + insert);
            r = stmt.executeQuery(insert);

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
        //return true ;
    }

	boolean updateinfo(String valupdate, String valcol, String condition, String conditioncol, String table) {
        try {
            String query = new String();
            query = "Update " + table + "\n Set " + valcol + " = ";
            if (valupdate.matches("[0-9]+") == false) {
                query = query + "'" + valupdate + "' \n where ";
            } else {
                query = query + valupdate + "\n where ";
            }
            query = query + conditioncol + " = ";
            if (condition.matches("[0-9]+") == false) {
                query = query + "'" + condition + "'";
            } else {
                query = query + condition;
            }

            System.out.println(query);
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            System.out.println("executed");

        } catch (Exception ex) {

            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
        return true;
    }

    boolean deletefromcontain(String bookid, String shelfid) {
        try {
            String query = new String();
            query = "Delete \n From Contain \n Where book_id = " + bookid + " and " + "shelf_id = " + shelfid;
            System.out.println(query);
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            System.out.println("executed");

        } catch (Exception ex) {

            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
        return true;
    }

    boolean deletefromwriter(String bookid, String writerid) {
        try {
            String query = new String();
            query = "Delete \n From Writer \n Where book_id = " + bookid + " and " + "author_id = " + writerid;
            System.out.println(query);
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            System.out.println("executed");

        } catch (Exception ex) {

            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);

            return false;
        }
        return true;
    }

	
	ResultSet retrieveInfo(String dataType, String s, String Tablename) {
        try {
//Class.forName("oracle.jdbc.driver.OracleDriver");

//c=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:globaldb","Library","123456789");
            stmt = c.createStatement();

            String query = "select* from " + Tablename + " where " + dataType + " = ";
            if (s.matches("[0-9]+") == true) {
                query = query + s;
            } else {
                query = query + "'" + s + "'";
            }
            System.out.println(query);
            r = stmt.executeQuery(query);
            int i = 0;
            System.out.println("in query\n");
//try {
// while (r.next())
// {
// System.out.println(r.getString(i+1));
// i++;
// }
        } catch (Exception e) {
            System.out.println("error in query");
        }
        return r;
    }

    ResultSet retrievecontainInfo(String bookid, String shelfid) {
        try {
            String query = new String();
            query = "Select* \n From Contain \n Where book_id = " + bookid + " and " + "shelf_id = " + shelfid;
            System.out.println(query);
            stmt = c.createStatement();
            r = stmt.executeQuery(query);
            System.out.println("executed");

        } catch (Exception ex) {

            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);

            //return false;
        }
        return r;
    }

	
	  ResultSet RetrieveInfo(String column_name, String val, String table) {
        //boolean isunique = false;
        try {
            String query = "Select* from " + table + " where " + column_name + " = '" + val + "'";

            stmt = c.createStatement();
            r = stmt.executeQuery(query);

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;

    }

}
