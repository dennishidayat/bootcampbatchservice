package com.enigma.task.bootcampbatch;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enigma.task.bootcampbatch.dao.BootcampBatchDao;
import com.enigma.task.bootcampbatch.dto.BootcampBatchDto;
import com.enigma.task.bootcampbatch.dto.CommonResponse;
import com.enigma.task.execption.*;
import com.enigma.task.bootcampbatch.model.BootcampBatch;

@RestController
@RequestMapping("/batch")
@SuppressWarnings("rawtypes")
public class BootcampBatchController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BootcampBatchDao bootcampBatchDao;
	
	@GetMapping(value="/{batchId}")
	public CommonResponse<BootcampBatch> getById(@PathVariable("batchId") String batchId) throws CustomExecption {
		try {
			BootcampBatch bootcampBatch = bootcampBatchDao.getById(Integer.parseInt(batchId));
			
			return new CommonResponse<BootcampBatch>(modelMapper.map(bootcampBatch, BootcampBatch.class));
		} catch (CustomExecption e) {
			return new CommonResponse<BootcampBatch>("01", e.getMessage());
		} catch (NumberFormatException e) {
			return new CommonResponse<BootcampBatch>("06", "input must be a number");
		} catch (Exception e) {
			return new CommonResponse<BootcampBatch>("06", e.getMessage());
		}
	}
	
	@PostMapping(value="")
	public CommonResponse<BootcampBatch> insert(@RequestBody BootcampBatch bcBatch) throws CustomExecption {
		try {
			BootcampBatch bootcampBatch = bcBatch;
			bootcampBatch.setBatchId(0);
			bootcampBatch =  bootcampBatchDao.save(bootcampBatch);
			
			return new CommonResponse<BootcampBatch>(modelMapper.map(bootcampBatch, BootcampBatch.class));
		} catch (CustomExecption e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@PutMapping(value="")
	public CommonResponse update(@RequestBody BootcampBatchDto bootcampBatch) throws CustomExecption {
		try {
			BootcampBatch checkBootcampBatch = bootcampBatchDao.getById(bootcampBatch.getBatchId());
			if(checkBootcampBatch == null) {
				return new CommonResponse("14", "batch not found");
			}
			if (bootcampBatch.getSequence() !=  null) {
				checkBootcampBatch.setSequence(bootcampBatch.getSequence());
			}
			if (bootcampBatch.getDescription() != null) {
				checkBootcampBatch.setDescription(bootcampBatch.getDescription());
			}
			if (bootcampBatch.getStartDate() != null) {
				checkBootcampBatch.setStartDate(bootcampBatch.getStartDate());
			}
			if (bootcampBatch.getFinishDate() !=  null) {
				checkBootcampBatch.setFinishDate(bootcampBatch.getFinishDate());
			}
			
			checkBootcampBatch = bootcampBatchDao.save(checkBootcampBatch);
			
			return new CommonResponse<BootcampBatchDto>(modelMapper.map(checkBootcampBatch, BootcampBatchDto.class));
		} catch (CustomExecption e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@DeleteMapping(value="/{batch-id}")
	public CommonResponse delete(@PathVariable("batch-id") Integer batch_id) throws CustomExecption {
		try {
			BootcampBatch checkBootcampBatch = bootcampBatchDao.getById(batch_id);
			if(checkBootcampBatch == null) {
				return new CommonResponse("06", "customer tidak ditemukan");
			}
			bootcampBatchDao.delete(checkBootcampBatch);
			return new CommonResponse();
		} catch (CustomExecption e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@GetMapping(value="")
	public CommonResponse getList(@RequestParam(name="list", defaultValue="") String bcBatch) throws CustomExecption{
		
		try {
			List<BootcampBatch> bootcampBatch = bootcampBatchDao.getList();
		
			return new CommonResponse<List<BootcampBatch>>(bootcampBatch);
		} catch (CustomExecption e) {
			throw e;
		} catch(NumberFormatException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			return new CommonResponse("06", e.getMessage());
		}
	}

}
