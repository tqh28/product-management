package com.product.management.common;

public class ReplaceVietnameseCharacter {

	public static final String replace(String s) {
		s = s.toLowerCase()
				.replaceAll("á|à|ạ|ả|ã|â|ấ|ầ|ậ|ẩ|ẫ|ă|ắ|ằ|ặ|ẳ|ẵ", "a")
				.replaceAll("é|è|ẹ|ẻ|ẽ|ê|ế|ề|ệ|ể|ễ", "e")
				.replaceAll("ó|ò|ọ|ỏ|õ|ô|ố|ồ|ộ|ổ|ỗ|ơ|ớ|ờ|ợ|ở|ỡ", "o")
				.replaceAll("ú|ù|ụ|ủ|ũ|ư|ứ|ừ|ự|ử|ữ", "u")
				.replaceAll("í|ì|ị|ỉ|ĩ", "i")
				.replaceAll("đ", "d")
				.replaceAll("ý|ỳ|ỵ|ỷ|ỹ", "y");
		
		return s;
	}
	
}
