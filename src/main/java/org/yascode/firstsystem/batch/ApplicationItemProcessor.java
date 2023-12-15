package org.yascode.firstsystem.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.yascode.firstsystem.dto.ApplicationCsv;
import org.yascode.firstsystem.entity.Application;

@Component
public class ApplicationItemProcessor<A, B> implements ItemProcessor<A, B> {
    @Override
    public B process(A item) throws Exception {
        ApplicationCsv applicationCsv = (ApplicationCsv) item;
        Application application = Application.builder()
                .code(applicationCsv.getCode())
                .name(applicationCsv.getName())
                .build();
        return (B) application;
    }
    /*@Override
    public ApplicationCsv process(ApplicationCsv applicationCsv) throws Exception {
        return ApplicationCsv.builder()
                .code(applicationCsv.getCode())
                .name(applicationCsv.getName())
                .build();
    }*/
}
