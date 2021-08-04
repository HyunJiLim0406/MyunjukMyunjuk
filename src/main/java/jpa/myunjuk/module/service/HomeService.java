package jpa.myunjuk.module.service;

import jpa.myunjuk.infra.exception.InvalidReqParamException;
import jpa.myunjuk.module.mapper.HomeMapper;
import jpa.myunjuk.module.model.domain.BookStatus;
import jpa.myunjuk.module.model.domain.Characters;
import jpa.myunjuk.module.model.domain.User;
import jpa.myunjuk.module.model.dto.HomeDto;
import jpa.myunjuk.module.repository.CharactersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static jpa.myunjuk.module.model.dto.HomeDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HomeService {

    private final HomeMapper homeMapper;
    private final CharactersRepository charactersRepository;

    public HomeDto home(User user, Integer year, Integer month) {
        validateYear(year);
        validateMonth(month);
        List<Item> itemList = user.getBooks().stream()
                .sorted((o1, o2) -> o2.getEndDate().compareTo(o1.getEndDate()))
                .sorted(((o1, o2) -> o2.getStartDate().compareTo(o1.getStartDate())))
                .filter(o -> o.getBookStatus() == BookStatus.DONE)
                .filter(o -> year == null || o.getEndDate().getYear() == year)
                .filter(o -> month == null || o.getEndDate().getMonthValue() == month)
                .map(homeMapper.INSTANCE::toItem)
                .collect(Collectors.toList());

        return builder()
                .size(itemList.size())
                .heightInfo(getCharacters(calcHeight(itemList)))
                .itemList(itemList)
                .build();
    }

    private HeightInfo getCharacters(double height) {
        Characters result = charactersRepository.findFirstByHeightLessThanEqualOrderByHeightDesc(height);
        return HeightInfo.builder()
                .totHeight(height)
                .name(result.getName())
                .img(result.getImg())
                .build();
    }

    private double calcHeight(List<Item> itemList) {
        return itemList.stream()
                .filter(o -> o.getTotPage() != null)
                .mapToInt(Item::getTotPage).sum() * 0.005;
    }

    private void validateYear(Integer year) {
        if (year != null && (year < 2000 || year > LocalDate.now().getYear()))
            throw new InvalidReqParamException("year = " + year);
    }

    private void validateMonth(Integer month) {
        if (month != null && (month < 1 || month > 12))
            throw new InvalidReqParamException("month = " + month);
    }
}
