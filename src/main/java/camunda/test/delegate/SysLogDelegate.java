package camunda.test.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component("SysLog")
public class SysLogDelegate implements JavaDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysLogDelegate.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        LOGGER.info("last step with status status={}", execution.getVariable("status"));
    }

}
