package nextstep.line.application;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import nextstep.common.domain.model.exception.EntityNotFoundException;
import nextstep.line.application.dto.LineRequest;
import nextstep.line.application.dto.LineResponse;
import nextstep.line.domain.Line;
import nextstep.station.domain.Station;
import nextstep.common.domain.model.exception.FieldDuplicateException;
import nextstep.line.application.dto.SectionRequest;
import nextstep.line.domain.repository.LineRepository;
import nextstep.station.application.StationService;

@RequiredArgsConstructor
@Service
@Transactional
public class LineService {
    private static final String ENTITY_NAME_FOR_EXCEPTION = "지하철 노선";

    private final LineRepository lineRepository;
    private final StationService stationService;

    public Line findById(Long id) {
        return lineRepository.findById(id)
                             .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME_FOR_EXCEPTION));
    }

    public Line findByIdWithStations(Long id) {
        return lineRepository.findByIdWithStations(id)
                             .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME_FOR_EXCEPTION));
    }

    @Transactional(readOnly = true)
    public LineResponse findLine(Long id) {
        Line line = findByIdWithStations(id);

        return LineResponse.withStationsFrom(line);
    }

    public LineResponse saveLine(LineRequest request) {
        if (lineRepository.existsByName(request.getName())) {
            throw new FieldDuplicateException("노선 이름");
        }
        Line line = lineRepository.save(
            new Line(request.getName(), request.getColor())
        );
        if (isAddableSection(request)) {
            Station upStation = stationService.findById(request.getUpStationId());
            Station downStation = stationService.findById(request.getDownStationId());
            line.addSection(upStation, downStation, request.getDistance());
        }
        return LineResponse.withStationsFrom(line);
    }

    private boolean isAddableSection(LineRequest request) {
        return Objects.nonNull(request.getUpStationId())
            && Objects.nonNull(request.getDownStationId())
            && Objects.nonNull(request.getDistance());
    }

    @Transactional(readOnly = true)
    public List<LineResponse> showLines() {
        return lineRepository.findAll()
                             .stream()
                             .map(LineResponse::notWithStationsFrom)
                             .collect(Collectors.toList());
    }


    public void updateLine(Long id, LineRequest lineRequest) {
        Line line = findById(id);

        line.edit(lineRequest.getName(), lineRequest.getColor());
    }


    public void deleteLine(Long id) {
        lineRepository.deleteById(id);
    }

    public void addSection(Long lineId, SectionRequest request) {
        Station upStation = stationService.findById(request.getUpStationId());
        Station downStation = stationService.findById(request.getDownStationId());
        Line line = findByIdWithStations(lineId);

        line.addSection(upStation, downStation, request.getDistance());
    }

    public void deleteSection(Long lineId, Long stationId) {
        Station station = stationService.findById(stationId);

        findById(lineId).deleteSection(station);
    }
}