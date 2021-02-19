package com.tingbei.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * spring-sercurity-oauth授权返回的标准令牌实体
 * Created by JJH on 2017/5/9.
 * @author JJH
 */
@Data
public class AuthTokenInfo implements Serializable{
	private static final long serialVersionUID = -1100328079624438353L;
	private String access_token;
	private String token_type;
	private String refresh_token;
	private int expires_in;
	private String scope;


	@Override
	public String toString() {
		return "AuthTokenInfo [access_token=" + access_token + ", token_type=" + token_type + ", refresh_token="
				+ refresh_token + ", expires_in=" + expires_in + ", scope=" + scope + "]";
	}
	
	
}
