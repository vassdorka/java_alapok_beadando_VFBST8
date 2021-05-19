
package Repository;

import Modell.Book;
import Modell.Database;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

public class BookRepo {
    
    public static boolean addNewBook(Book b){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewBook");
            
            spq.registerStoredProcedureParameter("isbn_in", Long.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("title_in", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("description_in", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("published_in", Short.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("bookCondition_in", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("status_in", Integer.class, ParameterMode.IN);
            
            spq.setParameter("isbn_in", b.getIsbn());
            spq.setParameter("title_in", b.getTitle());
            spq.setParameter("description_in", b.getDiscription());
            spq.setParameter("published_in", b.getPublished());
            spq.setParameter("bookCondition_in", b.getBookCondition());
            spq.setParameter("status_in", b.getStatus());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    
    public static List<Book> getAllBook(){
        List<Book> result = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllBook");
            
            List<Object[]> books = spq.getResultList();
            for(Object[] book : books){
                long isbn = Long.parseLong(book[0].toString());
                Book b = em.find(Book.class, isbn);
                result.add(b);
            }
        }
        catch(Exception ex){
        }
        finally{
            return result;
        }
    }
    
    
    public static boolean logicalDeleteOfBook(Book b){
        try{
            EntityManager em = Database.getDbConn();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeleteOfBook");

            spq.registerStoredProcedureParameter("bookISBN_in", Long.class, ParameterMode.IN);

            spq.setParameter("bookISBN_in", b.getIsbn());

            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    
    public static List<Object[]> getAllAccessabbleBook(){
        List<Object[]> books = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllAccessabbleBook");
            
            books = spq.getResultList();           
        }
        catch(Exception ex){
        }
        finally{
            return books;
        }
    }
        
    
    public static List<Long> getAllAccessabbleBookISBN(){
        List<Long> abookISBN = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllAccessabbleBook");
            
            List<Object[]> books = spq.getResultList();
            for(Object[] book : books){
                long isbn = Long.parseLong(book[0].toString());
                abookISBN.add(isbn);
            }
            
        }
        catch(Exception ex){
        }
        finally{
            return abookISBN;
        }
    }
        
    
    public static boolean updateBook(Book  input){
        try{
            EntityManager em = Database.getDbConn();
            em.getTransaction().begin();
            Book uj = em.find(Book.class, input.getIsbn());
            uj.setTitle(input.getTitle());
            uj.setDiscription(input.getDiscription());
            uj.setPublished(input.getPublished());
            uj.setBookCondition(input.getBookCondition());
            uj.setStatus(input.getStatus());
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }
        
    
    public static List<Long> bookIsbnList(){
        List<Long> result = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getBookIsbnList");
            
            List<Object[]> bookIsbn = spq.getResultList();           
            for(Object[] bisbn : bookIsbn){
                long isbn  = Long.parseLong(bisbn[0].toString());
                result.add(isbn);
            }
        }
        catch(Exception ex){
        }
        finally{
            System.out.println(result);
            return result;
        }
    }
}
