package nextstep.subway.applicaion;

import nextstep.member.application.StationQueryService;
import nextstep.subway.applicaion.dto.StationRequest;
import nextstep.subway.applicaion.dto.StationResponse;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StationService implements StationQueryService {
  private final StationRepository stationRepository;

  public StationService(StationRepository stationRepository) {
    this.stationRepository = stationRepository;
  }

  public StationResponse saveStation(StationRequest stationRequest) {
    Station station = stationRepository.save(new Station(stationRequest.getName()));
    return StationResponse.of(station);
  }

  @Transactional(readOnly = true)
  public List<StationResponse> findAllStations() {
    List<Station> stations = stationRepository.findAll();

    return stations.stream()
      .map(StationResponse::of)
      .collect(Collectors.toList());
  }

  public void deleteStationById(Long id) {
    stationRepository.deleteById(id);
  }

  public Station findById(Long id) {
    return stationRepository.findById(id).orElseThrow(IllegalArgumentException::new);
  }

  @Transactional(readOnly = true)
  public StationResponse searchStation(Long id) {
    return stationRepository.findById(id).map(StationResponse::of).orElseThrow(IllegalArgumentException::new);
  }
}
