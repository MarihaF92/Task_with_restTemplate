package ru.inex.study.application.study_app.service;


import ru.inex.parameters.repository.BaseParametersRepository;
import ru.inex.parameters.service.BaseParametersService;
import ru.inex.schedulerlib.service.IParameterService;

/**
 * Created by starostinaaa on 29.03.2021
 */
public class ParameterService extends BaseParametersService implements IParameterService {

    public ParameterService(BaseParametersRepository repository, String keyData) {
        super(repository, keyData);
    }

}
