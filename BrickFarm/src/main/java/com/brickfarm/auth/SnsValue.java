package com.brickfarm.auth;

import org.apache.commons.lang3.StringUtils;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.api.DefaultApi20;

import lombok.Data;

@Data
public class SnsValue implements SnsUrls {
	private String service;
	private String clientId;
	private String clientSecret;
	private String redirectUrl;
	private DefaultApi20 api20Instance;
	private String profileUrl;
	private String scope;
	private String response_type;

	private boolean isNaver;
	private boolean isGoogle;
	private boolean isKakao;

	public SnsValue(String service, String clientId, String clientSecret, String redirectUrl) {
		this.service = service;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUrl = redirectUrl;

		this.isNaver = StringUtils.equalsIgnoreCase("naver", this.service);
		this.isGoogle = StringUtils.equalsIgnoreCase("google", this.service);

		if (isNaver) {
			this.api20Instance = NaverAPI20.instance();
			this.profileUrl = NAVER_PROFILE_URL;
		} else if (isGoogle) {
			this.api20Instance = GoogleApi20.instance();
			this.profileUrl = GOOGLE_PROFILE_URL;
		}
	}

	public SnsValue(String service, String clientId, String redirectUrl, String scope, String response_type) {
		this.service = service;
		this.clientId = clientId;
		this.redirectUrl = redirectUrl;
		this.scope = scope;
		this.response_type = response_type;

		this.isKakao = StringUtils.equalsIgnoreCase("kakao", this.service);

		if (isKakao) {
			this.api20Instance = KakaoAPI20.instance();
			this.profileUrl = KAKAO_PROFILE_URL;
		}
	}

}
