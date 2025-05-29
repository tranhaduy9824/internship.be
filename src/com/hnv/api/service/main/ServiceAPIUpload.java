package com.hnv.api.service.main;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hnv.api.interf.IServiceCallback;
import com.hnv.api.main.API;
//import com.hnv.api.main.API;
import com.hnv.common.tool.ToolLogServer;
import com.hnv.def.DefAPIExt;

@Controller
public class ServiceAPIUpload implements IServiceCallback {
	private static final String PARAM_FILE_01 = "file_data";
	private static final String PARAM_FILE_02 = "file";
	
	private static final String PARAM_TYP01 = "typ01";
	private static final String PARAM_TYP02 = "typ01";
	private static final String PARAM_TYP03 = "typ03";
	private static final String PARAM_CODE 	= "code";

	@PostMapping(DefAPIExt.URL_API_UP)
	public void doService(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(PARAM_FILE_01) MultipartFile[] files_01, //---Normal fileInput
			@RequestParam(PARAM_FILE_02) MultipartFile[] files_02, //---DropZone
			@RequestParam Map<String, Object> params) {
		
		try {
			MultipartFile[] files = (files_01!=null&&files_01.length>0)?files_01:files_02;
			
			API.doServiceUpload(API.SV_TYPE_PRIVATE, request, response, files, params, DefAPIExt.API_PATH_UPLOAD, this);
		} catch (Exception e) {
			ToolLogServer.doLogErr(e);
//			message = "Could not upload the files. Error: " + e.getMessage();
//			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ServiceResponse(message));
		}
	}

	@Override
	public void doCallBack(HttpServletRequest request, HttpServletResponse response, Object... params)
			throws Exception {
		// ---do something here after sending the response to client
	}

	/*
	 * @GetMapping("/files") public ResponseEntity<List<FileInfo>> getListFiles() {
	 * List<FileInfo> fileInfos = storageService.loadAll().map(path -> { String
	 * filename = path.getFileName().toString(); String url =
	 * MvcUriComponentsBuilder .fromMethodName(FilesController.class, "getFile",
	 * path.getFileName().toString()).build().toString();
	 * 
	 * return new FileInfo(filename, url); }).collect(Collectors.toList());
	 * 
	 * return ResponseEntity.status(HttpStatus.OK).body(fileInfos); }
	 * 
	 * @GetMapping("/files/{filename:.+}")
	 * 
	 * @ResponseBody public ResponseEntity<Resource> getFile(@PathVariable String
	 * filename) { Resource file = storageService.load(filename); return
	 * ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION,
	 * "attachment; filename=\"" + file.getFilename() + "\"").body(file); }
	 */
}
