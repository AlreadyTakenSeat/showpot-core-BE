package com.example.show.controller;

import com.example.artist.controller.dto.response.ArtistNameApiResponse;
import com.example.artist.service.ArtistAdminService;
import com.example.genre.controller.dto.response.GenreNameApiResponse;
import com.example.genre.service.GenreAdminService;
import com.example.show.controller.dto.request.ShowCreateApiForm;
import com.example.show.controller.dto.request.ShowUpdateApiForm;
import com.example.show.controller.dto.response.ShowInfoApiResponse;
import com.example.show.service.ShowAdminService;
import com.example.show.service.dto.response.ShowInfoServiceResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/shows")
public class ShowAdminController {

    private final ShowAdminService showAdminService;
    private final ArtistAdminService artistAdminService;
    private final GenreAdminService genreAdminService;

    @GetMapping
    public String createShow(Model model) {
        List<ArtistNameApiResponse> artists = artistAdminService.findAllArtistName()
            .stream()
            .map(ArtistNameApiResponse::new)
            .toList();

        List<GenreNameApiResponse> genres = genreAdminService.findAllGenres()
            .stream()
            .map(GenreNameApiResponse::new)
            .toList();

        model.addAttribute("artists", artists);
        model.addAttribute("genres", genres);
        return "show_create_form";
    }

    @PostMapping
    public String createShow(@Valid ShowCreateApiForm showCreateApiForm) {
        showAdminService.save(showCreateApiForm.toServiceRequest());
        return "redirect:/admin/shows/list";
    }

    @GetMapping("/list")
    public String findAllShow(Model model) {
        var showWithTicketingTimes = showAdminService.findShowDetailWithTicketingTimes();

        List<ShowInfoApiResponse> shows = showWithTicketingTimes.stream()
            .map(ShowInfoApiResponse::new)
            .toList();

        model.addAttribute("shows", shows);
        return "show_list_form";
    }

    @GetMapping("/{id}")
    public String findShow(@PathVariable("id") UUID id, Model model) {
        List<ArtistNameApiResponse> artists = artistAdminService.findAllArtistName()
            .stream()
            .map(ArtistNameApiResponse::new)
            .toList();

        List<GenreNameApiResponse> genres = genreAdminService.findAllGenres()
            .stream()
            .map(GenreNameApiResponse::new)
            .toList();

        ShowInfoServiceResponse showInfoServiceResponse = showAdminService.findShowInfo(id);
        ShowInfoApiResponse shows = new ShowInfoApiResponse(showInfoServiceResponse);

        model.addAttribute("artists", artists);
        model.addAttribute("genres", genres);
        model.addAttribute("shows", shows);

        return "show_form";
    }

    @PutMapping("/{id}")
    public String updateArtist(
        @PathVariable("id") UUID id,
        @Valid ShowUpdateApiForm showUpdateApiForm
    ) {
        showAdminService.updateShow(id, showUpdateApiForm.toServiceRequest());
        return "redirect:/admin/shows/list";
    }

    @DeleteMapping("/{id}")
    public String deleteShow(@PathVariable("id") UUID id) {
        showAdminService.deleteShow(id);
        return "redirect:/admin/shows/list";
    }
}
