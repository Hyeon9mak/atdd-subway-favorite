package nextstep.path.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import nextstep.path.application.PathFacade;
import nextstep.path.application.dto.PathRequest;
import nextstep.path.application.dto.PathResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/paths")
public class PathController {
    private final PathFacade pathFacade;

    @GetMapping
    public ResponseEntity<PathResponse> findShortestPaths(@ModelAttribute PathRequest pathRequest) {
        PathResponse pathResponse = pathFacade.findShortestPaths(pathRequest);

        return ResponseEntity.ok().body(pathResponse);
    }
}