
package Repository;

import Modell.Database;
import Modell.Member1;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;


public class Member1Repo {
    
    public static boolean addNewMember(Member1 m){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("addNewMember");
            
            spq.registerStoredProcedureParameter("surname_in", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("lastname_in", String.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("startOfMembership_in", Date.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("paidMembershipFee_in", Integer.class, ParameterMode.IN);
            spq.registerStoredProcedureParameter("status_in", Integer.class, ParameterMode.IN);
            
            spq.setParameter("surname_in", m.getSurname());
            spq.setParameter("lastname_in", m.getLastname());
            spq.setParameter("startOfMembership_in", m.getStartOfMembership());
            spq.setParameter("paidMembershipFee_in", m.getPaidMembershipFee());
            spq.setParameter("status_in", m.getStatus());
            
            spq.execute();
            return true;
                   
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    
    public static List<Member1> getAllMember(){
        List<Member1> result = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllMember");
            
            List<Object[]> members = spq.getResultList();
            for(Object[] mem : members){
                Integer memberID = Integer.parseInt(mem[0].toString());
                Member1 m = em.find(Member1.class, memberID);
                result.add(m);
            }
        }
        catch(Exception ex){
        }
        finally{
            System.out.println(result);
            return result;
        }
    }
    
    
    public static boolean logicalDeleteOfMember(Member1 m){
        try{
            EntityManager em = Database.getDbConn();
            
            StoredProcedureQuery spq = em.createStoredProcedureQuery("logicalDeleteOfMember");
        
            spq.registerStoredProcedureParameter("memberID_in", Integer.class, ParameterMode.IN);
            
            spq.setParameter("memberID_in", m.getMemberID());
            
            spq.execute();
            return true;
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    
    public static List<String> memberCheckByName(){
        List<String> names = new ArrayList();

        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllMember");
            
            List<Object[]> members = spq.getResultList();
            for(Object[] mem : members){
                Integer memberID = Integer.parseInt(mem[0].toString());
                String surname = mem[1].toString();
                String lastname = mem[2].toString();
                String fullname = surname + " " + lastname;
                names.add(fullname);
           }
        }
        catch(Exception ex){
        }
        finally{
            return names;
        }
    }
    
    
    public static List<Integer> IDCheck(){       
        List<Integer> memid = new ArrayList();
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllMemberID");
            
            List<Object[]> ids = spq.getResultList();
            for(Object[] id : ids){
                Integer memberID = Integer.parseInt(id[0].toString());
                memid.add(memberID);
           }
        }
        catch(Exception ex){
        }
        finally{
            return memid;
        }
    }
    
    
    public static List<String> dateList() {
        List<String> datumLista = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllMember");
            
            List<Object[]> members = spq.getResultList();
            
            for(Object[] mem : members) {
            String startOfMembership = mem[3].toString();
            datumLista.add(startOfMembership);
            }
           
        }
        catch(Exception ex){
        }
        finally{
            return datumLista;
        }
    }
    
    
    public static List<Long> paidFeeList() {
        List<Long> paidFees = new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try{
            EntityManager em = Database.getDbConn();
            StoredProcedureQuery spq = em.createStoredProcedureQuery("getAllMember");
            
            List<Object[]> members = spq.getResultList();
            
            for(Object[] mem : members) {
            Long paidMembershipFee = Long.parseLong(mem[4].toString());
            paidFees.add(paidMembershipFee);
            }
           
        }
        catch(Exception ex){
        }
        finally{
            return paidFees;
        }
    }
    
    
    public static boolean updateMember1(Member1 input){
        try{
            EntityManager em = Database.getDbConn();
            em.getTransaction().begin();
            Member1 uj = em.find(Member1.class, input.getMemberID());
            uj.setSurname(input.getSurname());
            uj.setLastname(input.getLastname());
            uj.setStartOfMembership(input.getStartOfMembership());
            uj.setPaidMembershipFee(input.getPaidMembershipFee());
            uj.setStatus(input.getStatus());
            em.getTransaction().commit();
            return true;
        }
        catch(Exception ex) {
            return false;
        }
    }
}
