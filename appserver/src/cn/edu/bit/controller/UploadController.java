package cn.edu.bit.controller;

import java.io.File;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/upload")
public class UploadController {

	@RequestMapping("uploadToInstall")
	public String uploadToInstall() {
		return "upload/uploadToInstall";
	}

	@RequestMapping(value = "uploadFile", produces = "plain/text;charset=UTF-8")
	public ModelAndView uploadFile(MultipartFile imgFile,
			HttpServletRequest request) {
		String result = "";
		String path = request.getSession().getServletContext()
				.getRealPath("/files");

		String fileName = imgFile.getOriginalFilename();

		// String extendFileName = fileName
		//		.substring(fileName.lastIndexOf(".") + 1);
		// if(!"xml".equals(extendFileName)&&!"apk".equals(extendFileName)){
		// result = "只能上传xml和apk文件";
		// }else{
		File targetFile = new File(path, fileName);

		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			imgFile.transferTo(targetFile);

			result = "上传成功.";
		} catch (Exception e) {
			result = e.getMessage();
		}
		// }

		ModelAndView view = new ModelAndView("upload/uploadResult");

		return view.addObject("uploadResult", result);

	}

}
