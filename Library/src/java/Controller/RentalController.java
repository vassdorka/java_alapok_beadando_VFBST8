
package Controller;

import Modell.Rental;
import Service.RentalService;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class RentalController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            //AddNewRental
            if(request.getParameter("task").equals("addRental")){
               JSONObject returnValue = new JSONObject();
               DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                  if(!request.getParameter("memberID").isEmpty() && !request.getParameter("bookISBN").isEmpty()
                      && !request.getParameter("rentalDate").isEmpty() && !request.getParameter("active").isEmpty()
                      && !request.getParameter("status").isEmpty()) {

                       Integer memberID = Integer.parseInt(request.getParameter("memberID"));
                       Long bookISBN = Long.parseLong(request.getParameter("bookISBN"));                                        
                       Date rentalDate;            
                          try {
                              rentalDate = dateFormat.parse(request.getParameter("rentalDate"));

                       Integer active = Integer.parseInt(request.getParameter("active"));
                       Integer status = Integer.parseInt(request.getParameter("status"));

                       returnValue.put("result", RentalService.addNewRental(new Rental(0, memberID, bookISBN, rentalDate, null, active, status)));

                          } catch (ParseException e) {
                              e.printStackTrace();
                          }
                  }
                  else{
                       returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                      }               

                out.print(returnValue.toString());         
            }
            
          
            //getAllRental
            if(request.getParameter("task").equals("getAllRental")){
                JSONObject obj = new JSONObject();
                List<Rental> rentals = RentalService.getAllRental();

                if(rentals.isEmpty()){
                    obj.put("Result", "Eddig nem vettek ki egy könyvet sem.");
                    out.print(obj.toString());
                }
                else{
                    for(Rental r : rentals){  
                        out.print(r.toJson().toString());
                    }    
                }
            }
        
        
            //logicalDeleteOfRental
            if(request.getParameter("task").equals("logicalDeleteOfRental")){
                  JSONObject returnValue = new JSONObject();

                  if(!request.getParameter("rentalID").isEmpty()){
                      Integer rentalID = Integer.parseInt(request.getParameter("rentalID"));

                      returnValue.put("result", RentalService.logicalDeleteOfRental(Rental.getRentalById(rentalID)));
                  }
                  else{
                      returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                  }
                out.print(returnValue.toString());
            }
            
         
            //updateRental
            if(request.getParameter("task").equals("updateRental")){
                JSONObject returnValue = new JSONObject();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                   if(!request.getParameter("rentalID").isEmpty() && !request.getParameter("memberID").isEmpty() && !request.getParameter("bookISBN").isEmpty()
                       && !request.getParameter("rentalDate").isEmpty() && !request.getParameter("rentalEndDate").isEmpty() && !request.getParameter("active").isEmpty()
                       && !request.getParameter("status").isEmpty()) {

                        Integer rentalID = Integer.parseInt(request.getParameter("rentalID"));
                        Integer memberID = Integer.parseInt(request.getParameter("memberID"));
                        Long bookISBN = Long.parseLong(request.getParameter("bookISBN"));                                        
                        Date rentalDate, rentalEndDate;            
                           try {
                               rentalDate = dateFormat.parse(request.getParameter("rentalDate"));
                               rentalEndDate = dateFormat.parse(request.getParameter("rentalEndDate"));

                        Integer active = Integer.parseInt(request.getParameter("active"));
                        Integer status = Integer.parseInt(request.getParameter("status"));

                        returnValue.put("result", RentalService.updateRental(new Rental(rentalID, memberID, bookISBN, rentalDate, rentalEndDate, active, status)));

                           } catch (ParseException e) {
                               e.printStackTrace();
                           }
                   }
                   else{
                        returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                       }               

                out.print(returnValue.toString());         
            }
            
         
            //mostWantedBooks
            if(request.getParameter("task").equals("mostWantedBooks")){
                List<Object[]> books = RentalService.mostWantedBooks();
                JSONObject returnValue = new JSONObject();

                if(books.isEmpty()){
                    returnValue.put("Result", "Eddig nem vettek ki egy könyvet sem");
                    out.print(returnValue.toString());
                }else{
                    for(Object[] book : books){
                      Long bookISBN = Long.parseLong(book[0].toString());
                      returnValue.put("ISBN", bookISBN);
                      String title = book[1].toString();
                      returnValue.put("Cím", title);
                      Integer count = Integer.parseInt(book[2].toString());
                      returnValue.put("Eddig ennyi alkalommal vették ki", count);
                      out.println(returnValue);
                    }  
                }
            }        
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
