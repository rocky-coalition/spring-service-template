package com.coalition.sample.api;

import com.coalition.core.configuration.error.api.NotFoundException;
import com.coalition.core.configuration.error.logic.LogicException;
import com.coalition.sample.api.model.SampleModel;
import com.coalition.sample.logic.SampleService;
import io.micrometer.core.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class SampleController {

    private SampleService sampleService;

    public SampleController (@Autowired SampleService sampleService) {
        this.sampleService = sampleService;
    }

    @GetMapping("/sample/id/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Secured("ROLE_user")
    @Timed("get_sample")
    public SampleModel getSample(@PathVariable("id") String id) throws LogicException {
        if(StringUtils.equalsIgnoreCase(id, "id")) {
            return SampleModel.builder().id(UUID.randomUUID().toString()).data("data").build();
        } else{
            throw new NotFoundException();
        }
    }

    @PostMapping("/sample/id/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Secured("ROLE_user")
    @Timed("send_sample")
    public void sendSample(@PathVariable("id") String id) throws LogicException {

    }

}
