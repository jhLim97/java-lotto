package lotto;

import lotto.domain.Rank;
import lotto.dto.LottoNumber;
import lotto.domain.LottoPlay;
import lotto.domain.Winning;
import lotto.dto.LottoNumbers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.assertj.core.api.Assertions.*;

class LottoPlayTest {

    @Test
    @DisplayName("수동으로 로또를 구매할 수 있다.")
    void canBuyLottoByManual() {
        LottoPlay lottoPlay = new LottoPlay();
        lottoPlay.createLottoByManual(Arrays.asList(1, 2, 3, 4, 5, 6));
        assertThat(lottoPlay.getLottoNumbers().size()).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource({"14000, 14"})
    @DisplayName("입력된 금액만큼의 해당하는 로또를 발급한다.")
    void generateLotto(int input, int countLotto) {
        LottoPlay lottoPlay = new LottoPlay();
        lottoPlay.createLotto(input);
        assertThat(lottoPlay.getLottoNumbers().size()).isEqualTo(countLotto);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,2,3,7,8,9:3", "1,2,3,4,7,8:4", "1,2,3,4,5,7:5", "1,2,3,4,5,6:6"}, delimiter = ':')
    @DisplayName("당첨된 번호의 개수를 확인 할 수 있다.")
    void matchNumberCount(String input, int countWinnings) {
        String[] numbers = input.split(",");
        List<LottoNumber> lottoNumber = new ArrayList<>();

        List<Integer> winningNumber = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            winningNumber.add(i);
        }

        for (String number : numbers) {
            lottoNumber.add(new LottoNumber(Integer.parseInt(number)));
        }

        LottoNumbers lottoNumbers = new LottoNumbers(lottoNumber);
        assertThat(lottoNumbers.countMatchNumber(winningNumber)).isEqualTo(countWinnings);
    }

    @ParameterizedTest
    @CsvSource(value = {"3:5000", "4:50000", "5:1500000", "6:2000000000"}, delimiter = ':')
    @DisplayName("당첨 개수의 따라 금액이 달라진다.")
    void decisionMoneyByWinningNumbers(int countMatchNumber, int amount) {
        assertThat(Rank.rank(countMatchNumber, false).getPrice()).isEqualTo(amount);
    }

    @ParameterizedTest
    @CsvSource(value = {"3,4:55000", "3,5:1505000"}, delimiter = ':')
    @DisplayName("당첨된 금액의 총 합을 구할 수 있다.")
    void canSumWinningAmount(String input, int totalAmount) {
        String[] countMatchNumber = input.split(",");

        Winning winnings = new Winning();
        winnings.record(Integer.parseInt(countMatchNumber[0]), false);
        winnings.record(Integer.parseInt(countMatchNumber[1]), false);

        assertThat(winnings.getSumAmount()).isEqualTo(totalAmount);
    }

    @ParameterizedTest
    @CsvSource(value = {"5:30000000"}, delimiter = ':')
    @DisplayName("5개 일치에서 보너스 볼이 일치하는 경우 2등에 당첨된다.")
    void bonus(int countMatchNumber, int amount) {
        assertThat(Rank.rank(countMatchNumber, true).getPrice()).isEqualTo(amount);
    }
}
