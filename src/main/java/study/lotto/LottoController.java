package study.lotto;

import study.lotto.core.Lotto;
import study.lotto.core.WinLottoNumbers;
import study.lotto.dispenser.LottoDispenser;
import study.lotto.dispenser.Lottos;
import study.lotto.lottery.Lottery;
import study.lotto.lottery.LotteryResult;
import study.lotto.view.input.ManualLottoInputView;
import study.lotto.view.input.PurchaseInputView;
import study.lotto.view.input.WinNumbersInputView;
import study.lotto.view.result.LotteryResultView;
import study.lotto.view.result.LottoPurchaseResultView;

import java.util.List;

public class LottoController {

    private PurchaseInputView purchaseInputView = PurchaseInputView.getInstance();
    private ManualLottoInputView manualLottoInputView = ManualLottoInputView.getInstance();
    private WinNumbersInputView winNumbersInputView = WinNumbersInputView.getInstance();
    private LottoPurchaseResultView lottoPurchaseResultView = LottoPurchaseResultView.getInstance();
    private LotteryResultView lotteryResultView = LotteryResultView.getInstance();

    private LottoDispenser lottoDispenser = LottoDispenser.getInstance();

    public void purchaseByAuto() {

        // 구매
        Lottos lottos = purchaseLottos();

        // 구매한 로또 출력 
        lottoPurchaseResultView.display(lottos);
        
        // 당첨 번호 및 추첨
        WinLottoNumbers winLottoNumbers = winNumbersInputView.display();
        Lottery lottery = new Lottery(winLottoNumbers, lottos);
        LotteryResult lotteryResult = lottery.checkLotto();
        
        // 결과 출력
        lotteryResultView.display(lotteryResult);

    }

    private Lottos purchaseLottos() {
        // 구매 수량 입력
        PurchaseAmount purchaseAmount = purchaseInputView.display();
        // 구매 - 수동
        Lottos manualLottos = manualLottoInputView.display(purchaseAmount.getNumberOfManualLotto());
        // 구매결과 출력
        purchaseInputView.displayPurchaseAmount(purchaseAmount);
        // 구매 - 자동
        Lottos autoLottos = lottoDispenser.auto(purchaseAmount.getNumberOfAutoLotto());
        // 자동로또 출력
        lottoPurchaseResultView.display(autoLottos);
        // 병합
        return Lottos.merge(manualLottos, autoLottos);
    }

}
