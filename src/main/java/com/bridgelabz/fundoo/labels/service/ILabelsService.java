package com.bridgelabz.fundoo.labels.service;

import com.bridgelabz.fundoo.labels.dto.LabelsDto;
import com.bridgelabz.fundoo.response.Response;

public interface ILabelsService {
Response createlabel(LabelsDto labelsDto);
Response updatelabel(LabelsDto labelsDto);
Response readlabel(LabelsDto labelsdDto);
Response deletelabel(LabelsDto labelsDto);
}
