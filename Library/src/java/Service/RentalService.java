
package Service;

import Modell.Rental;
import Repository.BookRepo;
import Repository.Member1Repo;
import Repository.RentalRepo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RentalService {
    
    public static String addNewRental(Rental r){       
        List<Integer> memIdsWMTFAR = RentalRepo.moreThanFiveActiveRentals();
        List<Long> accessabbleBooks = BookRepo.getAllAccessabbleBookISBN();
        List<Long> bookISBNs = BookRepo.bookIsbnList();
        List<Integer> memberIDs = Member1Repo.IDCheck();
        
       DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
       Date today = new Date();
       dateFormat.format(today);
   
        if(r.getStatus() == 1){
            
            if(r.getActive() == 1){

                if (memberIDs.contains(r.getMemberID())){

                        if(!(memIdsWMTFAR.contains(r.getMemberID()))){

                            if(bookISBNs.contains(r.getBookISBN())){
                                
                                if ((r.getRentalDate().compareTo(today) < 0) || (r.getRentalDate().compareTo(today) == 0)){

                                    if(accessabbleBooks.contains(r.getBookISBN())){

                                        if(RentalRepo.addNewRental(r)){ 
                                            return "A kölcsönzést rögzítettük az adatbázisunkban!";
                                        }
                                        else{
                                            return "Az adatok helyesek, de a rögzítés nem sikerült";
                                        }

                                    }else {
                                        return "Ez a könyv jelenleg nem elérhető, már kivették!";
                                    }
                                    
                                }else{
                                        return "Nem vehet fel jövőbeli időpontot a kölcsönzés időpontjának!";
                                }

                            }else {
                                return "Ilyen ISBN szám nem létezik az adatbázisunkban!";
                            }    

                        }else{
                            return "5 könyvnél nem vehet ki többet, hozzon vissza előbb belőlük!";
                        }

                } else {
                   return "Amíg nincs beiratkozva, nem vehet ki könyvet!";
                }
            }else{
                return "Új kölcsönzés rögzítésekor nem adhat meg inaktív megjelölést!";
            }
        }else{
            return "Új kölcsönzés rögzítésekor nem jelölheti meg a státuszt töröltként!";
        }    
    }
    
    
    public static List<Rental> getAllRental(){
        return RentalRepo.getAllRental();
    }

    
    public static String logicalDeleteOfRental(Rental r){
        if(RentalRepo.logicalDeleteOfRental(r)){
                return "A kölcsönzés adatait töröltük!";
        }
        else{
            return "A kölcsönzés adatainak törlése nem sikerült";
        }
    }
    
    
    public static String updateRental(Rental r){
        if(RentalRepo.updateRental(r)){
            return "A kölcsönzés adatait sikeresen módosította!";
        } else{
            return "A kölcsönzés adatainak módosítása nem sikerült, nem létező azonosítót adott meg.";
        }
    }
    
    
    public static List<Object[]> mostWantedBooks(){
        return RentalRepo.mostWantedBooks();
    }
}
