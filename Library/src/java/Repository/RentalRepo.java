
package Repository;

import Modell.Database;
import Modell.Rental;
import static Modell.Rental.getRentalById;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;


public class RentalRepo {
    
    public static boolean addNewRental(Rental r){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewRental");
            
            spq.registerStoredProcedureParameter("memberID_in", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("bookISBN_in", Long.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("rentalDate_in", Date.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("active_in", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("status_in", Integer.class, ParameterMode.IN);
            
            spq.setParameter("memberID_in", r.getMemberID());
            spq.setParameter("bookISBN_in", r.getBookISBN());
            spq.setParameter("rentalDate_in", r.getRentalDate());
            spq.setParameter("active_in", r.getActive());
            spq.setParameter("status_in", r.getStatus());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
   
    public static List<Rental> getAllRental(){
        List<Rental> result = new ArrayList();
         try{
             EntityManager em = Database.getDbConn();
             StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllRental");

             List<Object[]> rentals = spq.getResultList();
             for(Object[] ren : rentals){
                Integer rentalID = Integer.parseInt(ren[0].toString());
                Rental r = em.find(Rental.class, rentalID);
                result.add(r);
            }    
         }
         catch(Exception ex){
         }
         finally{
             System.out.println(result);
             return result;
         }
    }
    
   
    public static boolean logicalDeleteOfRental(Rental r){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeleteOfRental");
        
            spq.registerStoredProcedureParameter("rentalID_in", Integer.class, ParameterMode.IN);
            
            spq.setParameter("rentalID_in", r.getRentalID());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    
    public static List<Integer> moreThanFiveActiveRentals(){
        List<Integer> result = new ArrayList();
        
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("memberIdListWithMoreThanFiveActiveRentals");

            List<Object[]> mtfar = spq.getResultList();
            for(Object[] id : mtfar){
                int memberID = Integer.parseInt(id[0].toString());
                result.add(memberID);
            }
        }
        catch(Exception ex){
        }
        finally{
            System.out.println(result);
            return result;
        }
    }
   
   
    public static boolean updateRental(Rental input){
        try{
            EntityManager em = Database.getDbConn();
            em.getTransaction().begin();
            Rental uj = em.find(Rental.class, input.getRentalID());
            uj.setMemberID(input.getMemberID());
            uj.setBookISBN(input.getBookISBN());
            uj.setRentalDate(input.getRentalDate());
            uj.setRentalEndDate(input.getRentalEndDate());
            uj.setActive(input.getActive());
            uj.setStatus(input.getStatus());
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }
   
    
    public static List<Object[]> mostWantedBooks(){
        List<Object[]> books = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("mostWantedBooks");
            
            books = spq.getResultList();           
        }
        catch(Exception ex){
        }
        finally{
            return books;
        }
    }
}
