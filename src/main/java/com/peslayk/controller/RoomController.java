package com.peslayk.controller;

import com.peslayk.model.Room;

import com.peslayk.model.User;
import com.peslayk.repository.RoomRepository;
import com.peslayk.repository.UserRepository;
import com.peslayk.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class RoomController {

    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserRepository userRepo;

    @ModelAttribute
    private void userDetails(Model m, Principal p){
        if (p!=null) {
            String email = p.getName();
            User user = userRepo.findByEmail(email);
            m.addAttribute("user", user);
            m.addAttribute("user_role", user.getRole());
        }
    }

    // For Admin

    @PostMapping("/saveRoom")
    public String saveRoom(@ModelAttribute("room") Room room) {
        System.out.println(room);
        roomService.saveRoom(room);
        return "redirect:/admin/rooms";
    }

    @GetMapping("admin/rooms")
    public String getAllRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "admin/rooms";
    }

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

    @GetMapping(value = "admin/rooms/deleteRoom/{idRoom}")
    public String deleteRoom(@PathVariable Long idRoom) {
        roomService.deleteRoom(idRoom);
        return "redirect:/admin/rooms";
    }

    //For users


    @GetMapping("/rooms")
    public String viewAllRooms(Model model) {
        //List<Room> rooms = roomService.getAllRooms();
        //model.addAttribute("rooms", rooms);
        return "/rooms";
    }

    // ROOMS PAGE SEARCH

    @GetMapping("/rooms/searchRoom")
    public String findAvailableRoom(@RequestParam String checkIn,
                                    @RequestParam String checkOut,
                                    @RequestParam Integer capacity,
                                    Model model) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date checkInDate = dateFormat.parse(checkIn);
        Date checkOutDate = dateFormat.parse(checkOut);
        System.out.println(checkOut+"---"+checkOut+"---"+capacity);
        List<Room> rooms = roomService.findAvailableRooms(checkInDate, checkOutDate, capacity);
        model.addAttribute("rooms", rooms);
        System.out.println(rooms);
        return "/rooms";
    }


}
