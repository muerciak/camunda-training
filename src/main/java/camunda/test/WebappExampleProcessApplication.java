package camunda.test;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.management.JobDefinition;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.GatewayDirection;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.Random;

@SpringBootApplication
@EnableScheduling
@EnableProcessApplication

public class WebappExampleProcessApplication {
    public static void main(String... args) {
        SpringApplication.run(WebappExampleProcessApplication.class, args);
    }

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    ManagementService managementService;

    @Scheduled(fixedDelay = 10_000)
    public void run() {
        runtimeService.startProcessInstanceByKey("test", Variables.putValue("testVar", "aaa"+ new Random().nextInt()));

    }

    @PostConstruct
    public void createProcess() {
        BpmnModelInstance modelInstance = Bpmn.createExecutableProcess("test")
                .startEvent()
                .serviceTask().camundaDelegateExpression("#{LogTimeDelegate}")
                .serviceTask().name("checkStatus").id("checkStatusServiceTask").camundaDelegateExpression("#{checkStatus}")
                .exclusiveGateway()
                    .name("What to do next?")
                .condition("Create a task", "#{status == '1'}")
                .serviceTask().camundaDelegateExpression("#{SysLog}")                .endEvent()
                .moveToLastGateway()
                .condition("Call an agent", "#{status == '0'}")
                    .intermediateCatchEvent().timerWithDuration("PT5S")
                    .connectTo("checkStatusServiceTask")//.camundaDelegateExpression("#{checkStatus2}")
                    .endEvent()
                .done();

        repositoryService.createDeployment().addModelInstance("test.bpmn", modelInstance).deploy();
        // find the job definition

        //

        System.out.println(Bpmn.convertToString(modelInstance));

    }

}