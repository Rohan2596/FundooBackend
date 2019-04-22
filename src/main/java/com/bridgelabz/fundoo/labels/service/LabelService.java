package com.bridgelabz.fundoo.labels.service;

import java.time.LocalDateTime;
import java.util.Optional;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.labels.dto.LabelsDto;
import com.bridgelabz.fundoo.labels.model.Labels;
import com.bridgelabz.fundoo.labels.respository.LabelRespository;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.util.ResponseStatus;
@PropertySource("classpath:message.properties")
@Service
public class LabelService implements ILabelsService {
@Autowired
LabelRespository labelRespository;
@Autowired
ModelMapper modelMapper;
@Autowired
Environment environment;

	@Override
	public Response createlabel(LabelsDto labelsDto) {
		Response response=null;
		Optional<Labels> labels1=labelRespository.findByLabelName(labelsDto.getLabelName());
		if(labels1.isPresent()) {
			System.out.println("labels are present");
			response = ResponseStatus.statusinfo(environment.getProperty("status.failure.labels.created"),
					Integer.parseInt(environment.getProperty("status.failure.labels.code")));
		}else {
			Labels labels=modelMapper.map(labelsDto, Labels.class);
			labels.setCreatedDateTime(LocalDateTime.now());
			labelRespository.save(labels);
			response = ResponseStatus.statusinfo(environment.getProperty("status.success.labels.created"),
					Integer.parseInt(environment.getProperty("status.success.labels.code")));
		}
		return response;
	}

	@Override
	public Response updatelabel(LabelsDto labelsDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response readlabel(LabelsDto labelsdDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response deletelabel(LabelsDto labelsDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
