package camunda.test.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component("checkStatus")
public class CheckStatus implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        int i = new Random().nextInt(2);
        execution.setVariable("status", ""+ i);
        LOGGER.info(execution.getProcessInstanceId()  + " Hello second step status {} time {} ", i, LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
    }

}
