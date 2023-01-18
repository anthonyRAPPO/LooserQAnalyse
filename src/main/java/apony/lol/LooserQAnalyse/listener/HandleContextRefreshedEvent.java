package apony.lol.LooserQAnalyse.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import apony.lol.LooserQAnalyse.service.interfaces.IRefService;

@Component
public class HandleContextRefreshedEvent implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    IRefService refService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        refService.init();
    }

}
