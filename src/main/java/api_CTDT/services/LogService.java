package api_CTDT.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api_CTDT.Models.Log;
import api_CTDT.repositories.LogRepository;

@Service
public class LogService {
	@Autowired
	LogRepository logRepository;
	private Set<Integer> logIds = new HashSet<>();

	public List<Log> getAll() {
		List<Log> listLHP = logRepository.findAll();
		return listLHP;
	}

	public Boolean GhiLog(Log log) {
		if (logIds.contains(log.getId()) == true) {
			return false;
		}
		Log logs = logRepository.save(log);
		if (logs != null) {
			return true;
		}

		return false;
	}

	public Log getID(Integer id) {
		Log log = logRepository.findById(id).orElseThrow();
		return log;
	}

}
