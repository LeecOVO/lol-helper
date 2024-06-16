package yalong.site.frame.panel.client;

import lombok.extern.slf4j.Slf4j;
import yalong.site.cache.AppCache;
import yalong.site.frame.panel.base.BaseButton;

import java.awt.event.ActionListener;
import java.io.IOException;

@Slf4j
public class ResetClientCheckBox extends BaseButton {

    public ResetClientCheckBox() {
        this.setText("热重启客户端");
        this.addActionListener(listener());
    }

    private ActionListener listener() {
        return e -> {
            try {
                System.out.println("resp = " + AppCache.api.resetUxClient());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        };
    }
}
