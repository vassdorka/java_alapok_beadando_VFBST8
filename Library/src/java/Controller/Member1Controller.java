
package Controller;

import Modell.Member1;
import Service.Member1Service;
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
import org.json.JSONArray;
import org.json.JSONObject;

public class Member1Controller extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            
            //AddNewMember
            if(request.getParameter("task").equals("addMember")){
               JSONObject returnValue = new JSONObject();
               DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                  if(!request.getParameter("surname").isEmpty() && !request.getParameter("lastname").isEmpty()
                      && !request.getParameter("startOfMembership").isEmpty() && !request.getParameter("paidMembershipFee").isEmpty()
                      && !request.getParameter("status").isEmpty()) {

                       String surname = request.getParameter("surname");
                       String lastname = request.getParameter("lastname");                                        
                       Date startOfMembership;            
                          try {
                              startOfMembership = dateFormat.parse(request.getParameter("startOfMembership"));

                       Integer paidMembershipFee = Integer.parseInt(request.getParameter("paidMembershipFee"));
                       Integer status = Integer.parseInt(request.getParameter("status"));

                       returnValue.put("result", Member1Service.addNewMember(new Member1(0, surname, lastname, startOfMembership, paidMembershipFee, status)));

                          } catch (ParseException e) {
                              e.printStackTrace();
                          }
                  }
                  else{
                       returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                      }               

                out.print(returnValue.toString());         
            }
            
        
            //getAllMember
            if(request.getParameter("task").equals("getAllMember")){
                 List<Member1> members = Member1Service.getAllMember();
                 JSONObject obj = new JSONObject();

                 if(members.isEmpty()){
                     obj.put("Result", "Jelenleg nincs egy aktív tagunk sem");
                     out.print(obj.toString());
                 }
                 else{
                     for(Member1 mem : members){
                         out.print(mem.toJson().toString());
                     }
                 }
            }    
        
        
            //logicalDeleteOfMember
            if(request.getParameter("task").equals("logicalDeleteOfMember")){
                  JSONObject returnValue = new JSONObject();

                  if(!request.getParameter("memberID").isEmpty()){
                      Integer memberID = Integer.parseInt(request.getParameter("memberID"));

                      returnValue.put("result", Member1Service.logicalDeleteOfMember(Member1.getMemberByID(memberID)));
                  }
                  else{
                      returnValue.put("result", "A mezők nincsenek megfelelően kitöltve.");
                  }
                  out.print(returnValue.toString());
            }
            
         
            //getDebtList
            if(request.getParameter("task").equals("getDebtList")){
                JSONArray returnValue = new JSONArray();
                JSONObject obj = new JSONObject();
                List<String[]> lista = Member1Service.getDebtList();

              if(lista.isEmpty()){     
                    obj.put("Result", "Jelenleg nincs egy aktív tagunk sem");
                    out.print(obj.toString());
                }
                else {
                      for(String[] l : lista){                     
                        String debt = l[0];
                        obj.put("Tartozás díja forintban: ",debt);
                        String name = l[1];
                        obj.put("Név",name);
                        out.println(obj);

                    }
                }             
            }
            
            
            //updateMember1
            if(request.getParameter("task").equals("updateMember")){
                JSONObject valasz = new JSONObject();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                if(!request.getParameter("memberID").isEmpty() && !request.getParameter("surname").isEmpty() && !request.getParameter("lastname").isEmpty()
                      && !request.getParameter("startOfMembership").isEmpty() && !request.getParameter("paidMembershipFee").isEmpty()
                      && !request.getParameter("status").isEmpty()){

                       Integer memberID = Integer.parseInt(request.getParameter("memberID"));
                       String surname = request.getParameter("surname");
                       String lastname = request.getParameter("lastname");                                        
                       Date startOfMembership;            
                          try {
                              startOfMembership = dateFormat.parse(request.getParameter("startOfMembership"));

                       Integer paidMembershipFee = Integer.parseInt(request.getParameter("paidMembershipFee"));
                       Integer status = Integer.parseInt(request.getParameter("status"));

                       valasz.put("result", Member1Service.updateMember1(new Member1(memberID, surname, lastname, startOfMembership, paidMembershipFee, status)));

                          } catch (ParseException e) {
                                 e.printStackTrace();
                          }

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
