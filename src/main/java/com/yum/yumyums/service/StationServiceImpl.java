package com.yum.yumyums.service;

import com.yum.yumyums.dto.StationDTO;
import com.yum.yumyums.entity.Station;
import com.yum.yumyums.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService{
    private final WebClient webClient = WebClient.create("https://t-data.seoul.go.kr/apig/apiman-gateway/tapi/TaimsKsccDvSubwayStationGeom/1.0");
    private final String apiKey = "2b0f47ca-9506-4b14-8a1c-7c6245ad3cde";
    private final WebClient.Builder webClientBuilder;
    private final StationRepository stationRepository;


    /*

 HttpURLConnection를 사용하는 전통적인 코딩방식.
 그러나 비동기 방식을 위하여 WebClient를 채택했기 때문에, 위처럼 스트림API 방식으로 코딩한다.
@Override
public List<Station> getAndSaveStations() {
    List<Station> stations = new ArrayList<>();

    try {
        URL url = new URL("https://api.example.com/stations");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // 응답 데이터를 파싱하여 StationDTO 리스트 생성
            List<StationDTO> stationDTOs = parseStationDTOs(response.toString());

            // StationDTO를 Station 엔티티로 변환
            stations = stationDTOs.stream()
                    .map(Station::dtoToEntity)
                    .collect(Collectors.toList());

            // 엔티티 리스트를 데이터베이스에 저장
            stations = stationRepository.saveAll(stations);
        } else {
            // 에러 처리
            System.err.println("HTTP 요청 실패: " + responseCode);
        }
    } catch (IOException e) {
        // 예외 처리
        e.printStackTrace();
    }

    return stations;
}

private List<StationDTO> parseStationDTOs(String responseBody) {
    // 응답 데이터를 파싱하여 StationDTO 리스트 생성
    // (구현 생략)
}
*/
    @Override
    public List<Station> getAndSaveStations() {

        Flux<StationDTO> stationDTOs = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToFlux(StationDTO.class);

        List<Station> stations = stationDTOs.toStream()
                .map(Station::dtoToEntity)
                .filter(station -> !stationRepository.existsById(station.getId())) // 데이터베이스에 이미 존재하는 역 정보인지 확인 후, 존재한다면 필터링
                .collect(Collectors.toList());

        return stationRepository.saveAllAndFlush(stations);
    }

    @Override
    public List<StationDTO> searchStations(String keyword) {
        List<Station> stations = stationRepository.findByNameContaining(keyword);

        return stations.stream()
                .map(StationDTO::entityToDto)
                .collect(Collectors.toList());

    }



}


