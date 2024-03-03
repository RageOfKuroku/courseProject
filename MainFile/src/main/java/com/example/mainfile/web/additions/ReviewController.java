package com.example.mainfile.web.additions;

import com.example.mainfile.dto.ReviewDto;
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
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private HotelService hotelService;

    @GetMapping("/hotel/{hotelId}")
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



    @PostMapping("/hotel/{hotelId}/add")
    public String addReview(@PathVariable("hotelId") Integer hotelId,
                            @ModelAttribute("newReview") @Valid ReviewDto reviewDto,
                            BindingResult result, Model model, @AuthenticationPrincipal UserEntity user) {
        if (reviewDto.getRating() < 1 || reviewDto.getRating() > 10) {
            model.addAttribute("ratingError", "Оценка должна быть между 1 и 10");
            return showReviews(hotelId, user, model);
        }
        if (result.hasErrors()) {
            return showReviews(hotelId, user, model);
        }
        reviewService.addReview(reviewDto, user, hotelId);
        return "redirect:/reviews/hotel/" + hotelId;
    }


    @PostMapping("/{reviewId}/delete")
    public String deleteReview(@PathVariable("reviewId") Integer reviewId, @AuthenticationPrincipal UserEntity currentUser) {
        Integer hotelId = reviewService.deleteReview(reviewId, currentUser);
        return "redirect:/reviews/hotel/" + hotelId;
    }


}

