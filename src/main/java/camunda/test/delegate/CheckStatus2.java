package camunda.test.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component("checkStatus2")
public class CheckStatus2 implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String i = "1";
        execution.setVariable("status", "1");
        LOGGER.info(execution.getProcessInstanceId()  + " Hello second step status {} time {} ", i, LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME));
    }

}
