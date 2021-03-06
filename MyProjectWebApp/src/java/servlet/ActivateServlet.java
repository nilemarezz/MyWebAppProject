/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import jpa.controller.AccountJpaController;
import jpa.controller.exceptions.RollbackFailureException;
import jpa.model.Account;

/**
 *
 * @author Nile
 */
public class ActivateServlet extends HttpServlet {
     @PersistenceUnit(unitName ="WebApplication1PU")
     EntityManagerFactory emf;
     @Resource
     UserTransaction utx;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String activateKey = request.getParameter("activateKey");
        boolean isActivate= false;
        if(username != null && activateKey != null){
            AccountJpaController accountCtrl = new AccountJpaController(utx, emf);
            Account account = accountCtrl.findAccount(username);
            if(activateKey.equals(account.getActivatekey())){
                account.setActivatedate(new Date());
                try{
                    accountCtrl.edit(account);
                    isActivate = true;
                    request.setAttribute("messageactivate", "activateSuccessfull,Please Login");
                    getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(ActivateServlet.class.getName()).log(Level.SEVERE, "jpa", ex);
                } catch (Exception ex) {
                    Logger.getLogger(ActivateServlet.class.getName()).log(Level.SEVERE, "jpa", ex);
                }
            }
            request.setAttribute("isActivate", isActivate);
            getServletContext().getRequestDispatcher("Activate.jsp").forward(request, response);
            
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
