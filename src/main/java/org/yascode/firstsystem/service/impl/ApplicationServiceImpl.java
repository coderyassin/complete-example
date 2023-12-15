package org.yascode.firstsystem.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yascode.firstsystem.entity.Application;
import org.yascode.firstsystem.repository.ApplicationRepository;
import org.yascode.firstsystem.service.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private final ApplicationRepository applicationRepository;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    /**
     * Save application
     *
     * @param application as {@link Application}
     * @return Application
     */
    @Override
    public Application saveApplication(Application application) {
        logger.info("Saving the application {}", application);
        return applicationRepository.save(application);
    }
}
