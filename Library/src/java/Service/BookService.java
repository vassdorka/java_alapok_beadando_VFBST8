
package Service;

import Modell.Book;
import Repository.BookRepo;
import java.util.List;

public class BookService {
    public static String addNewBook(Book b){
        
        if(b.getStatus() == 1){
            
            if(b.getBookCondition().equals("jó")){
                
                if(BookRepo.addNewBook(b)){
                    return "A könyvet rögzítettük az adatbázisunkban!";
                }
                else{
                    return "Az adatok helyesek, de a rögzítés nem sikerült";
                }
                
            }
            else{
                return "Sérült könyvet nem veszünk be!";
            }
            
        }else{
            return "Új könyv felvételekor nem jelölheti meg a státuszt töröltként!";
        }
    }
    
    
    public static List<Book> getAllBook(){
        return BookRepo.getAllBook();
    }
    
    
    public static String logicalDeleteOfBook(Book b){
        if(BookRepo.logicalDeleteOfBook(b)){
                return "A könyvet töröltük!";
            }
            else{
                return "A könyv törlése nem sikerült";
            }
    }
    
    
    public static List<Object[]> getAllAccessabbleBook(){
        return BookRepo.getAllAccessabbleBook();
    }
    
    
    public static String updateBook(Book b){
        if(BookRepo.updateBook(b)){
            return "A könyv adatait sikeresen módosította!";
        } else{
            return "A könyv adatainak módosítása nem sikerült, nem létező azonosítót adott meg.!";
        }
    }
    
    
    public static List<Long> bookIsbnList(){
        return BookRepo.bookIsbnList();
    }
}
