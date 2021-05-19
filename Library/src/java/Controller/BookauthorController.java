
package Controller;

import Modell.Bookauthor;
import Service.BookauthorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class BookauthorController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            //AddNewBookAuthor
            if(request.getParameter("task").equals("addBookAuthor")){
               JSONObject returnValue = new JSONObject();

                  if(!request.getParameter("bookISBN").isEmpty() && !request.getParameter("authorID").isEmpty()  && !request.getParameter("status").isEmpty()) {
                       Long bookISBN= Long.parseLong(request.getParameter("bookISBN").toString());
                       Integer authorID = Integer.parseInt(request.getParameter("authorID").toString());
                       Integer status = Integer.parseInt(request.getParameter("status"));

                       returnValue.put("result", BookauthorService.addNewBookAuthor(new Bookauthor(0, bookISBN, authorID, status)));
                  }
                  else{
                       returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                  }
            out.print(returnValue.toString());            
            }
          
            
            //getAllBookAuthor
            if(request.getParameter("task").equals("getAllBookAuthor")){
                  JSONObject obj = new JSONObject();
                  List<Bookauthor> ba = BookauthorService.getAllBookAuthor();

                  if(ba.isEmpty()){
                      obj.put("Result", "Nem szerepel egy szerző, sem könyv a nyilvántartásunkban.");
                       out.print(obj.toString());

                  }
                  else{
                      for(Bookauthor bookauthor : ba){
                        out.print(bookauthor.toJson().toString());
                      }
                  }
            }
            
        
            //logicalDeleteOfBookAuthor
            if(request.getParameter("task").equals("logicalDeleteOfBookAuthor")){
                  JSONObject returnValue = new JSONObject();

                  if(!request.getParameter("bookauthorID").isEmpty()){
                      Integer bookauthorID = Integer.parseInt(request.getParameter("bookauthorID"));

                      //Book b = Author.getAuthorByID(authorID);

                      //String result = BookService.logicalDeleteOfBook(b);
                      returnValue.put("result", BookauthorService.logicalDeleteOfBookAuthor(Bookauthor.getBookAuthorById(bookauthorID)));
                  }
                  else{
                      returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                  }
                  out.print(returnValue.toString());
            }
            
         
            //getAllBookWithAuthor
            if(request.getParameter("task").equals("getAllBookWithAuthor")){
                List <Object[]> bookauthors = BookauthorService.getAllBookWithAuthor();
                JSONObject object = new JSONObject();
                int statusnaumber = 1;

                if(bookauthors.isEmpty()){
                    object.put("Result", "Üres a könyvtár!");
                    out.print(object.toString());

                } else{

                  for(Object[] ba : bookauthors){
                        Long isbn = Long.parseLong(ba[0].toString());
                        object.put("ISBN szám: ", isbn);
                        String title = ba[1].toString();
                        object.put("Könyv címe: ", title);
                        String surname = ba[2].toString();
                        String lastname = ba[3].toString();
                        String fullname = surname + " " + lastname;
                        object.put("Szerző: ", fullname);
                        String discription = ba[4].toString();
                        object.put("Leírás: ", discription);
                        Short published = Short.parseShort(ba[5].toString());
                        object.put("Kiadás éve: ", published);
                        Boolean status = Boolean.parseBoolean(ba[6].toString());
                        if (status) {
                            object.put("Státusz: ", 1);
                        } else {
                        object.put("Státusz: ", 0);
                        }
                        out.println(object);

                   }  
                } 
            }
            
          
            //updateBookAuthor
            if(request.getParameter("task").equals("updateBookAuthor")){
                JSONObject valasz = new JSONObject();

                if(!request.getParameter("bookauthorID").isEmpty() && !request.getParameter("bookISBN").isEmpty() && !request.getParameter("authorID").isEmpty()  && !request.getParameter("status").isEmpty()) {

                     Integer bookauthorID = Integer.parseInt(request.getParameter("bookauthorID"));
                     Long bookISBN= Long.parseLong(request.getParameter("bookISBN").toString());
                     Integer authorID = Integer.parseInt(request.getParameter("authorID").toString());
                     Integer status = Integer.parseInt(request.getParameter("status"));

                     valasz.put("result", BookauthorService.updateBookAuthor(new Bookauthor(bookauthorID, bookISBN, authorID, status)));


                }else{
                    valasz.put("result", "A mezők nincsenek megfelelően kitöltve.");
                }
                out.print(valasz.toString()); 
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
