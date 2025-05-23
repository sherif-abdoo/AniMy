package com.AniMy.controller.User;

import com.AniMy.Dto.WatchListDto;
import com.AniMy.models.WatchList;
import com.AniMy.models.WatchListStatus;
import com.AniMy.repository.UserRepository;
import com.AniMy.services.AddToListService;
import com.AniMy.services.GetListService;
import com.AniMy.utils.JSendResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user/animeList")
@AllArgsConstructor
public class Lists {
    private final UserRepository userRepository;
    private final AddToListService addToListService;
    private final GetListService getListService;


    @PostMapping("/{animeId}")
    public ResponseEntity<JSendResponse<Map<String, String>>>
        addingToList(@PathVariable String animeId,
                     @RequestParam WatchListStatus status,
                     @AuthenticationPrincipal UserDetails userDetails) {
        JSendResponse<Map<String,String>> res = new JSendResponse<>();
        Map<String,String> data = new HashMap<>();
        if(userDetails == null){
            res.setStatus("fail");
            data.put("message", "you need to login first");
            res.setData(data);
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(res);
        }

        String username = userDetails.getUsername();
        String result = addToListService.addToList(animeId, username,status);

        res.setStatus("success");
        data.put("message", result);
        res.setData(data);
        return ResponseEntity
                .status(200)
                .body(res);
    }

    @GetMapping()
    public ResponseEntity<JSendResponse<Map<String,List<WatchListDto>>>>
        getList(@RequestParam WatchListStatus status,
                @AuthenticationPrincipal UserDetails user) {
        JSendResponse<Map<String,List<WatchListDto>>> res = new JSendResponse<>();
        Map<String,List<WatchListDto>> data = new HashMap<>();
        boolean exists = userRepository.existsByUsername(user.getUsername());
        if(!exists){
            res.setStatus("fail");
            res.setData(null);
            return ResponseEntity
                    .status(404)
                    .body(res);
        }

        List<WatchList> results = getListService.getUserList(user.getUsername(),status);
        data.put("animes" ,
                results.stream().map(anime ->
                        new WatchListDto(anime.getAnimeId(),anime.getStatus()))
                        .toList());
        res.setStatus("success");
        res.setData(data);
        System.out.println(res);
        return ResponseEntity
                .status(200)
                .body(res);

    }
}
