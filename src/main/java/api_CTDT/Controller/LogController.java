package api_CTDT.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import api_CTDT.Models.Log;
import api_CTDT.services.LogService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ghiLog")
public class LogController {
	@Autowired
	private LogService logService;

	@RequestMapping(method = RequestMethod.POST, path = "/create", produces = "application/json; charset=utf-8")
	@ResponseBody
	public boolean ghiLog(@RequestBody @Valid Log log) {
		logService.GhiLog(log);
		return true;
	}
}
