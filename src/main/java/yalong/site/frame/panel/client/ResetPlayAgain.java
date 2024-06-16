package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.AppCache;
import yalong.site.frame.panel.base.BaseButton;

import java.awt.event.ActionListener;
import java.io.IOException;

@Slf4j
public class ResetPlayAgain extends BaseButton {

    public ResetPlayAgain() {
        ResetPlayAgain resetPlayAgain = this;
        this.setText("跳过结算");
        this.addActionListener(listener());
    }

    private ActionListener listener() {
        return e -> {
                try {
                    System.out.println("resp = " + AppCache.api.setPlayAgain());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
        };

    }


}
