package lotto.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyTest {

    @Test
    public void 구매한_전체_티켓_수_카운트_테스트() {
        Money money = new Money(14000, 3);
        int result = money.countTicket();
        assertThat(result).isEqualTo(14);
    }

    @Test
    public void 자동구매_티켓_수_카운트_테스트() {
        Money money = new Money(14000, 3);
        int result = money.countAutoGeneratedTicket();
        assertThat(result).isEqualTo(11);
    }

}