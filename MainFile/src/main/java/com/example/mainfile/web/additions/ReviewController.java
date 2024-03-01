package com.example.mainfile.web.additions;

import com.example.mainfile.dto.HotelDto;
import com.example.mainfile.dto.ReviewDto;
import com.example.mainfile.dto.UserDto;
import com.example.mainfile.entity.HotelEntity;
import com.example.mainfile.entity.ReviewEntity;
import com.example.mainfile.entity.UserEntity;
import com.example.mainfile.service.HotelService;
import com.example.mainfile.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/hotels/{hotelId}")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private HotelService hotelService;

    @GetMapping("/reviews")
    public String showReviews(@PathVariable("hotelId") Integer hotelId,
                              @AuthenticationPrincipal UserEntity user,
                              Model model) {
        List<ReviewDto> reviews = reviewService.getReviewsByHotelId(hotelId);
        model.addAttribute("reviews", reviews);

        ReviewEntity newReview = new ReviewEntity();
        if (user != null) {
            newReview.setUser(user);
        }

        HotelEntity hotelEntity = hotelService.getHotelEntityById(hotelId);
        newReview.setHotel(hotelEntity);

        model.addAttribute("newReview", newReview);
        model.addAttribute("user", user);
        return "reviewsPage";
    }



    @PostMapping("/reviews")
    public String addReview(@PathVariable("hotelId") Integer hotelId,
                            @ModelAttribute("newReview") @Valid ReviewDto reviewDto,
                            BindingResult result, Model model, @AuthenticationPrincipal UserEntity user) {
        if (result.hasErrors()) {
            return showReviews(hotelId, user, model);
        }
        reviewService.addReview(reviewDto, user);
        return "redirect:/hotels/" + hotelId + "/reviews";
    }
}

