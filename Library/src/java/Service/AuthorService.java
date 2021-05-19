
package Service;

import Modell.Author;
import Repository.AuthorRepo;
import java.util.ArrayList;
import java.util.List;

public class AuthorService {
    
    public static String addNewAuthor(Author a){
        if(a.getStatus() == 1){
            
            if(AuthorRepo.addNewAuthor(a)){
                    return "A szerzőt sikeresen rögzítettük!";
                }
                else{
                    return "A szerző rögzítése nem sikerült";
                }
        
        }else{
            return "Űj szerző felvételekor nem jelölheti meg a státuszt töröltként!";
        }
    }
    
    
    public static List<Author> getAllAuthor(){
        return AuthorRepo.getAllAuthor();
    }
    
    
    public static String logicalDeleteOfAuthor(Author a){
        if(AuthorRepo.logicalDeleteOfAuthor(a)){
                return "A szerzőt sikeresen töröltük a nyilvántartásunkból.";
            }
            else{
                return "A szerző törlése nem sikerült";
            }
    }
    
    
    public static List<String[]> getAuthorListByPopularity() { //szerzők nepszerűségének értékelése a könyvtárból kölcsönzött könyveik alapján 3 kategóriába osztva
        List <Object[]> authors = AuthorRepo.getAuthorListByPopularity();
        ArrayList<Integer> levels= new ArrayList<Integer>();
        List <String> authornames = new ArrayList<String>();
        
        //kivesszük a level és a name értékeket
        for(Object[] author : authors){
                Integer id = Integer.parseInt(author[0].toString());
                String surname = author[1].toString();               
                String lastname = author[2].toString();
                String name = surname + " " + lastname;
                authornames.add(name);
                Integer level = Integer.parseInt(author[3].toString());
                levels.add(level);
        }
        
        //max level érték kiszámolása
        Integer max = levels.get(0);
        for(int i=1; i<levels.size(); i++) {
            if(levels.get(i)> max) {
                max = levels.get(i);
            }          
        }
        
       //max -> a legtöbb kivett könyv száma
       String categoryOne = "Béna író";
       String categoryTwo = "Középszerű író";
       String categoryThree = "Népszerű író";
       String message;
       List<String> messages = new ArrayList<String>();
       
       for(Integer level : levels) {
           if (level <= max/3) {
               message = categoryOne;
           }
           else if (level <= 2*(max/3)) {
               message = categoryTwo;
           }
           else {
               message = categoryThree;
           }
           messages.add(message);
       }
       
       //két lista összefésülése
       List<String[]> nameplusmessage = new ArrayList<String[]>();
       for(Integer i = 0; i < authornames.size(); i++) {
           String[] tomb = new String[2];
           tomb[0] = authornames.get(i);
           tomb[1] = messages.get(i);
           
           nameplusmessage.add(tomb);         
       }
       
       return nameplusmessage;
    }
    
    
    public static String updateAuthor(Author a){
        if(AuthorRepo.updateAuthor(a)){
            return "A szerző adatait sikeresen módosította!";
        } else{
            return "A szerző adatainak módosítása nem sikerült, nem létező azonosítót adott meg.!";
        }
    }
    
    
    public static List<Integer> AuthorIds(){
        return AuthorRepo.authorIdList();
    }
}
