
package Repository;

import Modell.Author;
import static Modell.Author.getAuthorById;
import Modell.Database;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;


public class AuthorRepo {
    
    public static boolean addNewAuthor(Author a){
        try{
            EntityManager em = Database.getDbConn();

            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewAuthor");

            spq.registerStoredProcedureParameter("surname_in", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("lastname_in", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("status_in", Integer.class, ParameterMode.IN);

            spq.setParameter("surname_in", a.getSurname());
            spq.setParameter("lastname_in", a.getLastname());
            spq.setParameter("status_in", a.getStatus());

            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    
    public static List<Author> getAllAuthor(){
        List<Author> result = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllAuthor");

            List<Object[]> authors = spq.getResultList();           
            for(Object[] author : authors){
                Integer authorID = Integer.parseInt(author[0].toString());
                Author a = getAuthorById(authorID);
                result.add(a);
            }
        }
        catch(Exception ex){
        }
        finally{
            System.out.println(result);
            return result;
        }
    }
    
    
    public static boolean logicalDeleteOfAuthor(Author a){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeleteOfAuthor");
        
            spq.registerStoredProcedureParameter("authorID_in", Integer.class, ParameterMode.IN);
            
            spq.setParameter("authorID_in", a.getAuthorID()); 
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    
    public static List<Object[]> getAuthorListByPopularity() {
        List <Object[]> authors = new ArrayList();
        try {
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAuthorListByPopularity");
            
            authors = spq.getResultList();
                
        }                 
        catch(Exception ex){

        }
        finally{
            return authors;
        }
    }
    
    
    public static boolean updateAuthor(Author input){
        try{
            EntityManager em = Database.getDbConn();
            em.getTransaction().begin();
            Author uj = em.find(Author.class, input.getAuthorID());
            uj.setSurname(input.getSurname());
            uj.setLastname(input.getLastname());
            uj.setStatus(input.getStatus());
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }
    
    
    public static List<Integer> authorIdList(){
        List<Integer> result = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAuthorIdList");
            
            List<Object[]> authorIds = spq.getResultList();           
            for(Object[] id : authorIds){
                Integer authorID = Integer.parseInt(id[0].toString());
                result.add(authorID);
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
