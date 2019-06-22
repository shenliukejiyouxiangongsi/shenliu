package com.youdai.daichao.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 落地页
 * @author Administrator
 *
 */

@Setter
@Getter
@ToString
@Data
public class LandingPage {
	
	private int id;
	private int pId;
	private String pName;
	private long firstViewNum;
	private long secondViewNum;
	private long firstUserNum;
	private long secondUserNum;
	private String createTime;
	private int type;
}
