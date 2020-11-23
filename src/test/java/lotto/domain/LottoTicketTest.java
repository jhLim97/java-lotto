package lotto.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LottoTicketTest {

    private List<Integer> lottoNumbers = IntStream.range(1, 46).boxed().collect(Collectors.toList());

    private LottoTicket lottoTicket;

    private static final int BONUS_NUMBER = 11;

    @BeforeEach
    void setUp(){
        int[] numbers = {1,2,3,4,5,6};
        this.lottoTicket = new LottoTicket(IntStream.of(numbers).boxed().collect(Collectors.toList()));

        Collections.shuffle(lottoNumbers);
        this.lottoNumbers  = lottoNumbers.stream().limit(6).collect(Collectors.toList());
    }

    @DisplayName("LottoTicket 랜덤 번호 생성 테스트")
    @Test
    void lottoTicketRandomNumberCreateTest(){
        LottoTicket lottoTicket = new LottoTicket(lottoNumbers);
        assertThat(lottoTicket.getSortedLottoNumbers().size()).isEqualTo(6);
    }

    @DisplayName("LottoTicket 번호 오름차순으로 조회되는지 테스트")
    @Test
    void getLottoTicketSortedNumberTest(){
        //given
        LottoTicket lottoTicket = new LottoTicket(lottoNumbers);

        //when
        int min = lottoTicket.getSortedLottoNumbers()
                .stream()
                .mapToInt(v -> v)
                .min().orElseThrow(NoSuchElementException::new);

        // then
        assertThat(min).isEqualTo(lottoTicket.getSortedLottoNumbers().get(0));
    }

    @DisplayName("일치하는 당첨번호 갯수 계산 테스트")
    @Test
    void countWinningNumbersTest(){
        int[] lastWinningNumbers = {1,2,3,7,8,9};
        List<Integer> lastWinningNumberList = IntStream.of(lastWinningNumbers).boxed().collect(Collectors.toList());

        PrizeUnit prizeUnit = this.lottoTicket.countWinningNumbers(lastWinningNumberList, BONUS_NUMBER);

        assertThat(prizeUnit).isEqualTo(PrizeUnit.FIFTH_GRADE);

    }

    @DisplayName("최소 당첨번호 갯수 미만인 경우 0리턴 테스트")
    @Test
    void countWinningNumbersUnderMinToPrizeTest(){
        int[] lastWinningNumbers = {1,2,44,55,66,77};
        List<Integer> lastWinningNumberList = IntStream.of(lastWinningNumbers).boxed().collect(Collectors.toList());

        PrizeUnit prizeUnit = this.lottoTicket.countWinningNumbers(lastWinningNumberList, BONUS_NUMBER);

        assertThat(prizeUnit).isEqualTo(PrizeUnit.FAIL_GRADE);

    }

    @DisplayName("보너스 번호 당첨 테스트")
    @Test
    void countWinningNumbersAndBonusTest(){
        int[] lastWinningNumbers = {1,2,3,4,5,11};
        int bonusNumber = 6;
        List<Integer> lastWinningNumberList = IntStream.of(lastWinningNumbers).boxed().collect(Collectors.toList());

        PrizeUnit prizeUnit = this.lottoTicket.countWinningNumbers(lastWinningNumberList, bonusNumber);

        assertThat(prizeUnit).isEqualTo(PrizeUnit.SECOND_GRADE);

    }
}