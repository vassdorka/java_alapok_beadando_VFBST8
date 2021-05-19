
package Service;

import Modell.Bookauthor;
import Repository.AuthorRepo;
import Repository.BookRepo;
import Repository.BookauthorRepo;
import java.util.List;

public class BookauthorService {
     public static String addNewBookAuthor(Bookauthor ba){
        List<Integer> authorIDs = AuthorRepo.authorIdList();
        List<Long> bookISBNs = BookRepo.bookIsbnList();

        if(ba.getStatus() == 1){    
            
            if(bookISBNs.contains(ba.getBookISBN())) {
                
                if(authorIDs.contains(ba.getAuthorID())) {
                    
                    if(BookauthorRepo.addNewBookAuthor(ba)){
                            return "A megadott szerzőt és könyvet sikeresen párosítottuk!";
                        }
                        else{
                            return "A megadott szerző és könyv párosítása sikertelen volt!";
                        }
                    
                } else{
                    return "Nincs ilyen azonosítójú szerző a nyilvántartásban!";
                }
                
            }else {
                return "Nincs ilyen ISBN szám a nyilvántartásban!";
            }
            
        }else{
            return "Új párosítás felvételekor nem jelölheti meg a státuszt töröltként!";
        }
    }
    
     
    public static List<Bookauthor> getAllBookAuthor(){
        return BookauthorRepo.getAllBookAuthor();
    }
    
        
    public static String logicalDeleteOfBookAuthor(Bookauthor ba){
        if(BookauthorRepo.logicalDeleteOfBookAuthor(ba)){
                return "A szerző és könyv kapcsolatát sikeresen töröltük a nyilvántartásunkból.";
        }
        else{
            return "A szerző és könyv kapcsolatának törlése nem sikerült";
        }
    }
    
        
    public static List<Object[]> getAllBookWithAuthor(){
        return BookauthorRepo.getAllBookWithAuthor();
    }
        
        
    public static String updateBookAuthor(Bookauthor ba){
        if(BookauthorRepo.updateBookAuthor(ba)){
            return "A szerző és könyv kapcsolatának adatait sikeresen módosította!";
        } else{
            return "A szerző és könyv kapcsolatának adatainak módosítása nem sikerült, nem létező azonosítót adott meg.!";
        }
    }
}
