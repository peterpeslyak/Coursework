package com.peslayk.controller;

import com.peslayk.model.Reservation;
import com.peslayk.model.Room;
import com.peslayk.model.User;
import com.peslayk.repository.ReservationRepository;
import com.peslayk.repository.RoomRepository;
import com.peslayk.repository.UserRepository;
import com.peslayk.service.ReservationService;
import com.peslayk.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
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

    /*
    @GetMapping(value = "admin/rooms/{idRoom}")
    public String getRoomById(@PathVariable Long idRoom, Model model) {
        Room room = roomService.getRoomById(idRoom);
        model.addAttribute("room", room);
        return "room-details";
    }

    @RequestMapping(value = "admin/rooms/editRoom/{idRoom}")
    public String editRoom(@PathVariable Long idRoom, Model model) {
        model.addAttribute("room", roomService.getRoomById(idRoom));
        return "admin/editRoom";
    }

    @PostMapping(value = "/admin/rooms/editRoom/update/{idRoom}")
    public String updateRoom(@PathVariable Long idRoom,
                             @ModelAttribute("room") Room room,
                             Model model, HttpSession session) {
        roomService.editRoom(idRoom, room);
        return "redirect:/admin/rooms";
    }
     */

}
