package nextstep.favorite.ui;

import nextstep.favorite.application.AddFavoriteService;
import nextstep.favorite.application.DeleteFavoriteService;
import nextstep.favorite.application.GetFavoriteService;
import nextstep.favorite.application.dto.FavoriteRequest;
import nextstep.favorite.application.dto.FavoriteResponse;
import nextstep.auth.domain.LoginMember;
import nextstep.auth.ui.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class FavoriteController {

    private final AddFavoriteService addFavoriteService;
    private final GetFavoriteService getFavoriteService;
    private final DeleteFavoriteService deleteFavoriteService;

    public FavoriteController(AddFavoriteService addFavoriteService, GetFavoriteService getFavoriteService, DeleteFavoriteService deleteFavoriteService) {
        this.addFavoriteService = addFavoriteService;
        this.getFavoriteService = getFavoriteService;
        this.deleteFavoriteService = deleteFavoriteService;
    }

    @PostMapping("/favorites")
    public ResponseEntity createFavorite(
            @AuthenticationPrincipal LoginMember loginMember,
            @RequestBody FavoriteRequest request
    ) {
        Long addedFavoriteId = addFavoriteService.addFavorite(loginMember, request);
        return ResponseEntity
                .created(URI.create("/favorites/" + addedFavoriteId))
                .build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<FavoriteResponse>> getFavorites(
            @AuthenticationPrincipal LoginMember loginMember
    ) {
        List<FavoriteResponse> favorites = getFavoriteService.getFavorites(loginMember);
        return ResponseEntity.ok().body(favorites);
    }

    @DeleteMapping("/favorites/{id}")
    public ResponseEntity deleteFavorite(
            @AuthenticationPrincipal LoginMember loginMember,
            @PathVariable Long id
    ) {
        deleteFavoriteService.deleteFavorite(loginMember, id);
        return ResponseEntity.noContent().build();
    }
}
