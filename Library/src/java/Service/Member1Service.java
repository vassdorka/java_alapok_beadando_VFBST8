
package Service;

import Modell.Member1;
import Repository.Member1Repo;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Member1Service {
    
    public static String addNewMember(Member1 m){
       List<String> names = Member1Repo.memberCheckByName();
       String fullname = m.getSurname()+ m.getLastname();
       Boolean newmember = true;
      
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       Date today = new Date();
       dateFormat.format(today);
       
       for(int i = 0; i < names.size(); i++){
           if(fullname.equals(names.get(i))){
               newmember = false;
           }else{
               newmember = true;
           }
       }
       

        if(m.getStatus() == 1){        
       
                if (!(newmember)) {

                    if ((m.getStartOfMembership().compareTo(today) < 0) || (m.getStartOfMembership().compareTo(today) == 0)) {

                        if(Member1Repo.addNewMember(m)) {
                            return "Adatait rögzítettük az adatbázisunkban!";
                        }
                        else {
                            return "Az adatok helyesek, de a rögzítés nem sikerült";
                        }

                    }else{
                        return "Későbbi dátum nem adható meg tagság kezdetének!";
                    }

                }else{
                         return "Már rendelkezik tagsággal!";
                }
                
        }else{
            return "Új tag felvételekor nem jelölheti meg a státuszt töröltként!";
        }
    }
    
    public static List<Member1> getAllMember(){
        return Member1Repo.getAllMember();
    }
    
    
    public static String logicalDeleteOfMember(Member1 m){
        if(Member1Repo.logicalDeleteOfMember(m)){
                return "A tagságot töröltük!";
        }
        else{
            return "A tagság törlése nem sikerült, nincs ilyen azonosítóval rendelkező tagunk";
        }
    }
    
    
    public static List<Long> debtsCalculator() {
        List<String> datumLista = Member1Repo.dateList();
        List<Long> years = new ArrayList();
        List<Long> debts = new ArrayList();
        List<Long> paidFees = Member1Repo.paidFeeList();
        
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        dateFormat.format(today);
             
        try {
            for(int i=0; i<datumLista.size(); i++) {
                Date startOfMembership = dateFormat.parse(datumLista.get(i));                   

                Long difference = today.getTime() - startOfMembership.getTime();

                Long year = (difference / (1000l * 60 * 60 * 24 * 365));
                years.add(year);

                Long totalFee = year*600;
                Long result;
                Long debt = paidFees.get(i) - totalFee;
                
                if (debt < 0L) {
                    result = 0L;
                }else{
                    result = debt;
                }
                
                debts.add(result);

            }
        }catch(ParseException e) {
            e.printStackTrace();
        }
   
        return debts;
    }
    
    
    public static List<String[]> getDebtList() {
        List<Long> debts = Member1Service.debtsCalculator();
        List<String> members = Member1Repo.memberCheckByName();
        
        List<String[]> fullList = new ArrayList<String[]>();
        
        for(Integer i = 0; i < members.size(); i++) {
            String[] tomb = new String[2];
            if(debts.get(i) > 0){
             tomb[1] = members.get(i);
             tomb[0] = debts.get(i).toString();
            }
            fullList.add(tomb);         
        }
       
       return fullList;
    }
    
    
    public static List<String[]> getDebtListWithID() {
        List<Long> debts = Member1Service.debtsCalculator();
        List<Integer> memberIds = Member1Repo.IDCheck();
        
        List<String[]> fullList = new ArrayList<String[]>();
        
        for(Integer i = 0; i < memberIds.size(); i++) {
            String[] tomb = new String[2];
            if(debts.get(i) > 0){
             tomb[1] = memberIds.get(i).toString();
             tomb[0] = debts.get(i).toString();
            }
            fullList.add(tomb);         
        }
       
        return fullList;
    }
    
    
    public static List<Integer> getMemberIdsWithDebt() {
        List<String[]> fullist = Member1Service.getDebtListWithID();
        List<Integer> memberIds = new ArrayList();
        
        for(String[] id : fullist){
            Integer memberID = Integer.parseInt(id[1]);
            memberIds.add(memberID);
        }
        
       return memberIds;
    }
    
    
    public static String updateMember1(Member1 m){
        if(Member1Repo.updateMember1(m)){
            return "A tag adatait sikeresen módosította!";
        } else{
            return "A tag adatainak módosítása nem sikerült, a megadott azonosítóval nincs felvett tagunk!";
        }
    }
}
