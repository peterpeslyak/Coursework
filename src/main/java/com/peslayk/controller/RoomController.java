package com.peslayk.controller;

import com.peslayk.model.Room;

import com.peslayk.model.User;
import com.peslayk.repository.RoomRepository;
import com.peslayk.repository.UserRepository;
import com.peslayk.service.RoomService;
import com.peslayk.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class RoomController {
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
    public String saveRoom(@ModelAttribute("room") Room room, HttpSession session){
        roomService.saveRoom(room);
        session.setAttribute("msg", "Create a folder for room: id:" + room.getIdRoom());

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
        session.setAttribute("msg", "Room info is updated: id:" + idRoom);
        return "redirect:/admin/rooms";
    }

    @GetMapping(value = "admin/rooms/deleteRoom/{idRoom}")
    public String deleteRoom(@PathVariable Long idRoom, HttpSession session) {
        roomService.deleteRoom(idRoom);
        session.setAttribute("msg", "Room is deleted: id:" + idRoom);
        return "redirect:/admin/rooms";
    }

    //For users


    @GetMapping("/rooms")
    public String viewAllAvailableRooms(Model model) {
        List<Room> rooms = roomService.getAllAvailableRooms();
        model.addAttribute("rooms", rooms);
        return "/rooms";
    }

    // ROOMS PAGE SEARCH

    @GetMapping("/rooms/searchRoom")
    public String findAvailableRoom(@RequestParam String checkIn,
                                    @RequestParam String checkOut,
                                    @RequestParam Integer guests,
                                    Model model) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date checkInDate = dateFormat.parse(checkIn);
        Date checkOutDate = dateFormat.parse(checkOut);
        System.out.println(checkOut+"---" + checkOut + "---" + guests);
        List<Room> rooms = roomService.findAvailableRooms(checkInDate, checkOutDate, guests);
        model.addAttribute("rooms", rooms);
        model.addAttribute("checkIn", checkIn);
        model.addAttribute("checkOut", checkOut);
        model.addAttribute("guests", guests);
        System.out.println(rooms);
        return "/rooms";
    }


}
