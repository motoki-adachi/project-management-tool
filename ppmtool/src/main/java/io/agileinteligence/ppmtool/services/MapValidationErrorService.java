package io.agileinteligence.ppmtool.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationErrorService {

    public ResponseEntity<?> mapValidationService(BindingResult bindingResult) {
	// エラーハンドリング処理
	if (bindingResult.hasErrors()) {
	    // バリデーションメッセージを格納するMapを生成する
	    Map<String, String> errorMap = new HashMap<>();
	    // 生成したMapにパラメータで受け取ったメッセージを格納する
	    for (FieldError error : bindingResult.getFieldErrors()) {
		errorMap.put(error.getField(), error.getDefaultMessage());
	    }
	    // バリデーションメッセージをJSONレスポンスとして返却する
	    return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);
	}
	return null;
    }

}
