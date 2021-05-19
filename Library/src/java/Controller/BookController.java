
package Controller;

import Modell.Book;
import Service.BookService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class BookController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            //AddNewBook
            if(request.getParameter("task").equals("addBook")){
               JSONObject returnValue = new JSONObject();

                  if(!request.getParameter("isbn").isEmpty() && !request.getParameter("title").isEmpty() && !request.getParameter("description").isEmpty() && !request.getParameter("published").isEmpty() && !request.getParameter("bookCondition").isEmpty() && !request.getParameter("status").isEmpty()) {
                       Long isbn = Long.parseLong(request.getParameter("isbn"));
                       String title = request.getParameter("title");
                       String description = request.getParameter("description");
                       Short published = Short.parseShort(request.getParameter("published"));
                       String bookCondition = request.getParameter("bookCondition");
                       Integer status = Integer.parseInt(request.getParameter("status"));

                       Book b = new Book(isbn, title, description, published, bookCondition, status);
                       String result = BookService.addNewBook(b);
                       returnValue.put("result", result);
                  }
                  else{
                       returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                  }
            out.print(returnValue.toString());            
          }
          
            
            //getAllBook
            if(request.getParameter("task").equals("getAllBook")){
                  JSONObject obj = new JSONObject();
                  List<Book> books = BookService.getAllBook();

                  if(books.isEmpty()){
                      obj.put("Result", "Üres a künyvtár");
                      out.print(obj.toString());
                  }
                  else{
                      for(Book b : books){
                          out.print(b.toJson().toString());
                      }
                  }
              }
            
        
            //logicalDeleteOfBook
            if(request.getParameter("task").equals("logicalDeleteOfBook")){
                  JSONObject returnValue = new JSONObject();

                  if(!request.getParameter("isbn").isEmpty()){
                      Long isbn = Long.parseLong(request.getParameter("isbn"));

                      Book b = Book.getBookByIsbn(isbn);

                      String result = BookService.logicalDeleteOfBook(b);
                      returnValue.put("result", result);
                  }
                  else{
                      returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                  }
                  out.print(returnValue.toString());
              }
            
          
            //getAllAccessableBook
            if(request.getParameter("task").equals("getAllAccessabbleBook")){
                List <Object[]> acbooks = BookService.getAllAccessabbleBook();
                JSONObject object = new JSONObject();

                if(acbooks.isEmpty()){
                    object.put("Result", "Jelenleg nincs kikölcsönözhető könyvünk!");
                    out.print(object.toString());

                } else{

                  for(Object[] book : acbooks){
                        Long isbn = Long.parseLong(book[0].toString());
                        object.put("ISBN szám: ", isbn);
                        String title = book[1].toString();
                        object.put("Könyv címe: ", title);
                        String surname = book[2].toString();
                        String lastname = book[3].toString();
                        String fullname = surname + " " + lastname;
                        object.put("Szerző: ", fullname);
                        out.println(object);

                   }  
                } 
            }
            
          
            //updateBook
            if(request.getParameter("task").equals("updateBook")){
                JSONObject valasz = new JSONObject();

                if(!request.getParameter("isbn").isEmpty() && !request.getParameter("title").isEmpty()
                   && !request.getParameter("description").isEmpty() && !request.getParameter("published").isEmpty()
                   && !request.getParameter("bookCondition").isEmpty() && !request.getParameter("status").isEmpty()){

                      Long isbn = Long.parseLong(request.getParameter("isbn"));
                      String title = request.getParameter("title");
                      String description = request.getParameter("description");
                      Short published = Short.parseShort(request.getParameter("published"));
                      String bookCondition = request.getParameter("bookCondition");
                      Integer status = Integer.parseInt(request.getParameter("status"));

                      Book b = new Book(isbn, title, description, published, bookCondition, status);
                      String result = BookService.updateBook(b);
                      valasz.put("result", result);

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
