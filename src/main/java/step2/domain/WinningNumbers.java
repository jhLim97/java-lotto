package step2.domain;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class WinningNumbers {
    private static final int BONUS_INDEX = 6;
    private List<LottoNumber> winNumbers;

    public WinningNumbers(List<Integer> winNumbers) {
        this.winNumbers = makeWinNumbers(winNumbers);
    }

    private List<LottoNumber> makeWinNumbers(List<Integer> winNumbers) {
        return winNumbers.stream()
                .map(LottoNumber::new)
                .collect(toList());
    }

    public int getMatchAmount(LottoTicket lottoTicket) {
        return (int) lottoTicket.getNumbers().stream()
                .filter(this::isMatch)
                .count();
    }

    private boolean isMatch(LottoNumber lottoNumber) {
        return winNumbers.stream()
                .anyMatch(winNumber -> winNumber.equals(lottoNumber));
    }

    public boolean hasBonus(LottoTicket lottoTicket) {
        return lottoTicket.getNumbers().stream()
                .anyMatch(lottoNumber -> lottoNumber.equals(this.winNumbers.get(BONUS_INDEX)));
    }
}