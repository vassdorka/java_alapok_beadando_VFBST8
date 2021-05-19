
package Controller;

import Modell.Author;
import Service.AuthorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

public class AuthorController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            //AddNewAuthor
            if(request.getParameter("task").equals("addAuthor")){
               JSONObject returnValue = new JSONObject();

                  if(!request.getParameter("surname").isEmpty() && !request.getParameter("lastname").isEmpty() && !request.getParameter("status").isEmpty()) {
                       String surname = request.getParameter("surname");
                       String lastname = request.getParameter("lastname");
                       Integer status = Integer.parseInt(request.getParameter("status"));

                       Author a = new Author(0, surname, lastname, status);
                       String result = AuthorService.addNewAuthor(a);
                       returnValue.put("result", result);
                  }
                  else{
                       returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                  }
            out.print(returnValue.toString());            
          }
          
            
            //getAllAuthor
            if(request.getParameter("task").equals("getAllAuthor")){
                  JSONObject obj = new JSONObject();
                  List<Author> authors = AuthorService.getAllAuthor();

                  if(authors.isEmpty()){
                      obj.put("Result", "Nem szerepel egy szerző sem a nyilvántartásunkban.");
                       out.print(obj.toString());

                  }
                  else{
                      for(Author a : authors){
                        out.print(a.toJson().toString());
                      }
                  }
              }
            
            
            //logicalDeleteOfAuthor
             if(request.getParameter("task").equals("logicalDeleteOfAuthor")){
                   JSONObject returnValue = new JSONObject();

                   if(!request.getParameter("authorID").isEmpty()){
                       Integer authorID = Integer.parseInt(request.getParameter("authorID"));
                       returnValue.put("result", AuthorService.logicalDeleteOfAuthor(Author.getAuthorById(authorID)));
                   }
                   else{
                       returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                   }
                   out.print(returnValue.toString());
               }
             
             
            //getAuthorListByPopularity
            if(request.getParameter("task").equals("getAuthorListByPopularity")){
                List<String[]> nameplusmessages = AuthorService.getAuthorListByPopularity();
                  JSONObject object = new JSONObject();     

                if(nameplusmessages.isEmpty()){
                      object.put("Result", "Nem vettek még ki egy könyvet sem a könyvtárból");
                      out.print(object.toString());

                  } else {

                      for(String[] mes : nameplusmessages){                   
                          String name = mes[0];
                          object.put("Author name",name);
                          String mess = mes[1];
                          object.put("Popularity level",mess);
                          out.println(object);

                      }
                  }
            }
            
            
            //updateAuthor
            if(request.getParameter("task").equals("updateAuthor")){
                JSONObject valasz = new JSONObject();

                if(!request.getParameter("authorID").isEmpty() && !request.getParameter("surname").isEmpty() && !request.getParameter("lastname").isEmpty() && !request.getParameter("status").isEmpty()){

                      Integer authorID = Integer.parseInt(request.getParameter("authorID"));
                      String surname = request.getParameter("surname");
                      String lastname = request.getParameter("lastname");
                      Integer status = Integer.parseInt(request.getParameter("status"));

                       Author a = new Author(authorID, surname, lastname, status);
                       String result = AuthorService.updateAuthor(a);

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
