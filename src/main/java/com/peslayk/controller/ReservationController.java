package com.peslayk.controller;

import com.peslayk.model.Reservation;
import com.peslayk.model.Room;
import com.peslayk.model.User;
import com.peslayk.repository.ReservationRepository;
import com.peslayk.repository.RoomRepository;
import com.peslayk.repository.UserRepository;
import com.peslayk.service.ReservationService;
import com.peslayk.service.RoomService;
import com.peslayk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Controller
public class ReservationController {

    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepo;

    @Autowired
    private UserService userService;

    @ModelAttribute
    private void userDetails(Model m, Principal p){
        if (p!=null) {
            String email = p.getName();
            User user = userRepo.findByEmail(email);
            m.addAttribute("user", user);
            m.addAttribute("user_role", user.getRole());
        }
    }

    @GetMapping("admin/reservations")
    public String getAllReservations(Model model) {
        List<Reservation> reservations = reservationService.getAllReservations();
        model.addAttribute("reservations", reservations);
        return "admin/reservations";
    }



    // FOR USER

    @PostMapping(value = "/rooms/booking")
    public String bookingPage(@RequestParam String checkIn,
                              @RequestParam String checkOut,
                              @RequestParam Long idRoom,
                              @RequestParam Integer guests,
                              Model model) {

        System.out.println(checkIn);
        System.out.println(checkOut);
        System.out.println(idRoom);

        Room room = roomService.getRoomById(idRoom);

        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);

        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        Double priceTotal = room.getPrice()*nights;

        System.out.println(priceTotal);


        model.addAttribute("room", room);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("nights", nights);
        model.addAttribute("priceTotal", priceTotal);
        model.addAttribute("guests", guests);

        return "/booking";
    }

    @PostMapping("/rooms/booking/payment")
    public String payment(@ModelAttribute User user,
                          @RequestParam String checkIn,
                          @RequestParam String checkOut,
                          @RequestParam (name = "idRoom") Long idRoom,
                          @RequestParam Integer guests,
                          @RequestParam Integer nights,
                          @RequestParam Double priceTotal,
                          @RequestParam String firstName,
                          @RequestParam String lastName,
                          @RequestParam String email,
                          @RequestParam String phoneNumber,
                          Model model,
                          RedirectAttributes redirectAttributes) {

        System.out.println(checkIn);
        System.out.println(checkOut);
        System.out.println(user);
        System.out.println(idRoom);
        System.out.println(guests);
        System.out.println(nights);
        System.out.println(priceTotal);

        Room room = roomService.getRoomById(idRoom);

        // If user is registered
        User existingUser = userRepo.findByEmail(email);
        if(existingUser == null) {
            System.out.println("No such user");

            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPhoneNumber(phoneNumber);
            user.setPassword("12345678");

            userService.createUser(user);

            System.out.println("New user is created " + user);
            model.addAttribute("idUser", user.getIdUser());
            model.addAttribute("newAcc", "Yes");

        } else {
            Long idUser = existingUser.getIdUser();
            model.addAttribute("idUser", idUser);
        }

        model.addAttribute("room", room);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("nights", nights);
        model.addAttribute("priceTotal", priceTotal);
        model.addAttribute("guests", guests);

        return "/payment";
    }

    @PostMapping("/rooms/booking/payment/createReservation")
    public String createReservation(@ModelAttribute User user,
                                    @RequestParam String checkIn,
                                    @RequestParam String checkOut,
                                    @RequestParam Long idRoom,
                                    @RequestParam Integer guests,
                                    @RequestParam Double priceTotal,
                                    @RequestParam Long idUser,
                                    @RequestParam("newAcc") String newAcc,
                                    Model model,
                                    HttpSession session) {
        System.out.println(user);
        User user1 = userService.getUserById(idUser);
        System.out.println(idUser);

        Room room = roomService.getRoomById(idRoom);
        if (reservationService.newReservation(checkIn, checkOut, room, user1, guests, priceTotal) == null) {
            session.setAttribute("reservationMsg", "Reservation cannot be complete. Check out if this room is still available...");
        }
        if (newAcc!=null){
            model.addAttribute("newAcc", "Yes");
            session.setAttribute("msg", newAcc);
            System.out.println(newAcc);
        }
        return "paymentSuccess";
    }

    @GetMapping ("/rooms/booking/paymentSuccess")
    public String paymentSuccess(@RequestParam("newAcc") String newAcc,
                                 HttpSession session) {
        if (newAcc!=null){
            session.setAttribute("msg", newAcc);
            System.out.println(newAcc);
        }
        return "resirect:/paymentSuccess";
    }

}
