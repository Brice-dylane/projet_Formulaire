package serveletClass;

import personne.User;
import personne.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(name = "account")
public class NewAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstname=request.getParameter("prenom");
        String lastname=request.getParameter("nom");
        String gender=request.getParameter("gender");
        String city=request.getParameter("city");
        String address=request.getParameter("address");
        String email=request.getParameter("email");
        String login=request.getParameter("login");
        String password=request.getParameter("password");
        String course=request.getParameter("course");
        String cryptpass="";
        Utilisateur utilisateur = new Utilisateur();
        try {
            cryptpass = utilisateur.cryptPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(firstname,lastname,gender,city,address,email,login,cryptpass,course);
        int verif = 0;
        try {
            verif = utilisateur.addUser(user);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(verif<1){
            request.setAttribute("error","Failed to create account");
            this.getServletContext().getRequestDispatcher("/createAccount.jsp").forward(request, response);
        }
        else if (verif==2){
            request.setAttribute("error","Try again with another login");
            this.getServletContext().getRequestDispatcher("/createAccount.jsp").forward(request, response);
        }
        else {
            request.setAttribute("pass","Creation of successful account!");
            this.getServletContext().getRequestDispatcher("/createAccount.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/createAccount.jsp").forward(request, response);
    }
}
