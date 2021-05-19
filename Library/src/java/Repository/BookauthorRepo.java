
package Repository;

import Modell.Bookauthor;
import static Modell.Bookauthor.getBookAuthorById;
import Modell.Database;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;


public class BookauthorRepo {
    
    public static boolean addNewBookAuthor(Bookauthor ba){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewBookAuthor");
            
            spq.registerStoredProcedureParameter("bookISBN_in", Long.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("authorID_in", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("status_in", Integer.class, ParameterMode.IN);
            
            spq.setParameter("bookISBN_in", ba.getBookISBN());
            spq.setParameter("authorID_in", ba.getAuthorID());
            spq.setParameter("status_in", ba.getStatus());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
     
    public static List<Bookauthor> getAllBookAuthor(){
        List<Bookauthor> result = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllBookAuthor");
            
            List<Object[]> bookauthors = spq.getResultList();           
            for(Object[] ba : bookauthors){
                Integer bookauthorID = Integer.parseInt(ba[0].toString());
                Bookauthor bookauthor = getBookAuthorById(bookauthorID);
                result.add(bookauthor);
            }
        }
        catch(Exception ex){
        }
        finally{
            System.out.println(result);
            return result;
        }
    }
     
    
    public static boolean logicalDeleteOfBookAuthor(Bookauthor ba){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeleteOfBookAuthor");
        
            spq.registerStoredProcedureParameter("bookauthorID_in", Integer.class, ParameterMode.IN);
            
            spq.setParameter("bookauthorID_in", ba.getBookauthorID());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
     
    
    public static List<Object[]> getAllBookWithAuthor(){
        List<Object[]> authorbooks = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllBookWithAuthor");
            
            authorbooks = spq.getResultList();           
        }
        catch(Exception ex){
        }
        finally{
            return authorbooks;
        }
    }
    
    
    public static boolean updateBookAuthor(Bookauthor input){
        try{
            EntityManager em = Database.getDbConn();
            em.getTransaction().begin();
            Bookauthor uj = em.find(Bookauthor.class, input.getBookauthorID());
            uj.setBookISBN(input.getBookISBN());
            uj.setAuthorID(input.getAuthorID());
            uj.setStatus(input.getStatus());
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }
}
