
package com.bridgelabz.fundoo.labels.service;

import java.io.UnsupportedEncodingException;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.labels.dto.LabelsDto;
import com.bridgelabz.fundoo.response.Response;

public interface ILabelsService {
Response createlabel(LabelsDto labelsDts,String token) throws UserException, UnsupportedEncodingException;
Response updatelabel(LabelsDto labelsDto,String token,long id) throws UserException, UnsupportedEncodingException;
Response readlabel(LabelsDto labelsdDto);
Response deletelabel(LabelsDto labelsDto,String token,long id) throws UserException, UnsupportedEncodingException;
Response addLabelNote(long labelid,String token,long noteid);
Response removeLabelNote(long labelid,String token,long noteid);
}
